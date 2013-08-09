package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {

	def update(Long id, Long version) {
		if (!params.apply) {
			def hospitalInstance = Hospital.get(id)
			if  (!hospitalInstance) {
				render(contentType: "text/json") {
					resp = "error"
					message = "Id exceptions" 
				}
			}

			if (params.version != null) {
				if (hospitalInstance.version > params.version) {
					return render(contentType: "text/json") {
						resp = "error"
						message = "Another User has updated hospital(${hospitalInstance.name}) while you were editing"
					}
				}
			}
			hospitalInstance.notes = params?.notes
			hospitalInstance.save(flush:true)

			for (prod in params.products){
				def product = Product.get(prod.id)
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospitalInstance, product)
				if (hospitalProduct){
					for (msr in prod.measures){
						def hospitalMeasure = HospitalMeasure.get(msr.id)
						if (hospitalMeasure){
							hospitalMeasure.accepted = msr.accepted
							hospitalMeasure.completed = msr.completed
							hospitalMeasure.confirmed = msr.confirmed
							hospitalMeasure.verified = msr.verified
							hospitalMeasure.save(flush:true)
						}
						def hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, hospitalMeasure)
						if (hospitalProductMeasure)
							hospitalProductMeasure.included	= msr.included
							hospitalProductMeasure.save(flush:true)
					}
				}
			}

			def result = hospitalInstance
			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			render(contentType: "text/json") {
				name = result.name
				notes= result.notes
				id   = result.id
				ehr = result.ehr
				products = array {
					for (hp in hospitalProducts) {
						product id : hp.product.id,
						name : hp.product.name,
						code : hp.product.code,
						measures : array {
							for (hpm in hp.hospitalProductMeasures){
								measure id : hpm.hospitalMeasure.id,
								code : hpm.hospitalMeasure.measure.code,
								name : hpm.hospitalMeasure.measure.name,
								accepted : hpm.hospitalMeasure.accepted,
								completed : hpm.hospitalMeasure.completed,
								confirmed : hpm.hospitalMeasure.confirmed,
								included : hpm.included,
								verified : hpm.hospitalMeasure.verified
							}
						}
					}
				}
			}
		}
		else if (params.ehr_id) {
			// update Hospital set EHR
			def hospital = Hospital.get(params.id)
			hospital.ehr = Ehr.get(params.ehr_id)
			hospital.save(flush : true)

			def old_ids = HospitalProduct.list().findAll{it?.hospital == hospital}.collect{it.product.id}

			for (prId in params.product_ids) {//update HospitalProduct
				def product = Product.get(prId)
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospital, product)
				if (!hospitalProduct)
					hospitalProduct = new HospitalProduct(hospital:hospital, product:product).save(flush:true)

				def idx = -1
				def old_idx = -1
				for (old_id in old_ids){
					idx++
					if (old_id == Integer.parseInt(prId))
						old_idx = idx
				}
				if (old_idx>=0)
					old_ids.remove(old_idx)

				for (measure in product?.measures) {
					def hospitalMeasure 	= HospitalMeasure.findByHospitalAndMeasure(hospital, measure)
					if (!hospitalMeasure)
						hospitalMeasure 	= new HospitalMeasure(hospital: hospital, measure: measure, accepted: false, completed: false, confirmed: false, verified: false).save(flush:true)
					def hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, hospitalMeasure)
					if (!hospitalProductMeasure)
						hospitalProductMeasure 	= new HospitalProductMeasure(hospitalProduct:hospitalProduct, hospitalMeasure:hospitalMeasure, included:false).save(flush:true)

					for (de in measure.dataElements) {
						def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, de)
						if (!hospitalElement)
							hospitalElement = new HospitalElement(hospital: hospital, dataElement: de, internalNotes : "", notes:"", location : "", source : "", sourceEHR : true, valueSet : "", valueSetFile : "", valueType : ValueType.NotApplicable, codeType : CodeType.NotApplicable)

						def defaultSetting = DataElementDefaults.findByDataElementAndEhr(de, hospital.ehr)
						if (defaultSetting) {
							hospitalElement.location = defaultSetting.location
							hospitalElement.valueType = defaultSetting.valueType
							hospitalElement.codeType = defaultSetting.codeType
						}
						hospitalElement.save(flush:true)

						def hospitalMeasureElement 	= HospitalMeasureElement.findByHospitalMeasureAndHospitalElement(hospitalMeasure, hospitalElement)
						if (!hospitalMeasureElement)
							hospitalMeasureElement 	= new HospitalMeasureElement(hospitalMeasure:hospitalMeasure, hospitalElement:hospitalElement).save(flush:true)
					}
				}
			}


			for (oldId in old_ids){
				def p  = Product.get(oldId)
				def hp = HospitalProduct.findByHospitalAndProduct(hospital, p)

				def hp_id = hp?.id
				def hm_ids = hp.hospitalProductMeasures.collect{it.hospitalMeasure.id}

				for (hm_id in hm_ids){
					unlinkProductAndMeasure(hp_id, hm_id)
				}
				hp = HospitalProduct.findById(hp_id)
				hp.delete(flush:true)
			}

			render(contentType: "text/json") {
				resp = "ok"
				message = "Hospital ${hospital.name} "
			}
		} else {
			render(status: 420, text: "SomeError")
		}

	}

	def unlinkProductAndMeasure(Long hp_id, Long hm_id){
		def hp = HospitalProduct.findById(hp_id)
		def hm = HospitalMeasure.findById(hm_id)
		def hpm = HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hp, hm);
		def hsize = hm.hospitalProductMeasures.size()

		hm.removeFromHospitalProductMeasures(hpm).save()
		hp.removeFromHospitalProductMeasures(hpm).save()
		hpm.hospitalProduct = null
		hpm.hospitalMeasure = null
		hpm.delete(flush:true)

		if (hsize==1){
			def he_ids = hm.hospitalMeasureElements.collect{it.hospitalElement.id}
			for (he_id in he_ids){
				unlinkMeasureAndElement(hm_id, he_id)
			}
			hm = HospitalMeasure.findById(hm_id)
			hm.delete(flush:true)
		}
	}

	def unlinkMeasureAndElement(Long hm_id, Long he_id){
		def hm = HospitalMeasure.findById(hm_id)
		def he = HospitalElement.findById(he_id)
		def hme = HospitalMeasureElement.findByHospitalMeasureAndHospitalElement(hm, he);
		def hsize = he.hospitalMeasureElements.size()

		hm.removeFromHospitalMeasureElements(hme).save()
		he.removeFromHospitalMeasureElements(hme).save()
		hme.hospitalElement = null
		hme.hospitalMeasure = null
		hme.delete(flush:true)

		if (hsize==1){
			he = HospitalElement.findById(he_id)
			he.delete(flush:true)
		}
	}

	def show() {
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)

			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			//def productList	=  hospitalProducts.collect{it.product}
			render(contentType: "text/json") {
				name = result.name
				notes= result.notes
				id   = result.id
				ehr = result.ehr
				products = array {
					for (hp in hospitalProducts) {
						product id : hp.product.id,
						name : hp.product.name,
						code : hp.product.code,
						measures : array {
							for (hpm in hp.hospitalProductMeasures){
								measure id : hpm.hospitalMeasure.id,
								code : hpm.hospitalMeasure.measure.code,
								name : hpm.hospitalMeasure.measure.name,
								accepted : hpm.hospitalMeasure.accepted,
								completed : hpm.hospitalMeasure.completed,
								confirmed : hpm.hospitalMeasure.confirmed,
								included : hpm.included,
								verified : hpm.hospitalMeasure.verified
							}
						}
					}
				}
			}
		}
		else {
			def results = Hospital.list()

			render(contentType: "text/json") {
				hospitals = array {
					for (p in results) {
						hospital name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

}