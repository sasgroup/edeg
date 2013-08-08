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
			
			def result = hospitalInstance
			
			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProducts.collect{it.product}
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
							for (hm in hp.hospitalMeasures){
								measure id : hm.id,
								code : hm.measure.code,
								name : hm.measure.name,
								accepted : hm.accepted,
								completed : hm.completed,
								confirmed : hm.confirmed,
								included : hm.included,
								verified : hm.verified
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
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospital,product)

				if (!hospitalProduct) //create HospitalProduct if new product added to this hospital
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
					def hospitalMeasure = HospitalMeasure.findByHospitalAndMeasure(hospital, measure)
					if (!hospitalMeasure){
						hospitalMeasure = new HospitalMeasure(hospital: hospital, measure: measure, accepted: false, completed: false, confirmed: false, included: false, verified: false)
						hospitalMeasure.addToHospitalProducts(hospitalProduct).save(flush:true)
					}
					else{
						def hp_found = false
						for (hp in hospitalMeasure.hospitalProducts){
							if (hp.product.id == product.id)
								hp_found = true
						}
						if (!hp_found){
							hospitalMeasure.addToHospitalProducts(hospitalProduct).save(flush:true)
						}
					}
					
					for (de in measure.dataElements) { // need find dataLement for this Measure and add to Hospital element
						def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, de)
						if (!hospitalElement){
							hospitalElement = new HospitalElement(hospital: hospital, dataElement: de, internalNotes : "", notes:"", location : "", source : "", sourceEHR : true, valueSet : "", valueSetFile : "", valueType : ValueType.NotApplicable, codeType : CodeType.NotApplicable)
							def defaultSetting = DataElementDefaults.findByDataElementAndEhr(de, hospital.ehr)
							if (defaultSetting) {//if not null so need add to HospitalElement
								hospitalElement.location = defaultSetting.location
								hospitalElement.valueType = defaultSetting.valueType
								hospitalElement.codeType = defaultSetting.codeType
							}
							hospitalElement.addToHospitalMeasures(hospitalMeasure).save(flush:true)
						}
						else{
							def hm_found = false
							for (hm in hospitalElement.hospitalMeasures){
								if (hm.measure.id == measure.id)
									hm_found = true
							}
							if (!hm_found){
								hospitalElement.addToHospitalMeasures(hospitalMeasure).save(flush:true)
							}
						}
					}
				}
			}
			
			
			for (oldId in old_ids){
				def p  = Product.get(oldId)
				def hp = HospitalProduct.findByHospitalAndProduct(hospital,p)
				
				def hp_id = hp?.id
				def hm_ids = hp.hospitalMeasures.collect{it.id}
				
				for (hm_id in hm_ids){
					def hm = HospitalMeasure.findById(hm_id)
					if (hm.hospitalProducts.size()>1){
						hp.removeFromHospitalMeasures(hm).save(flush:true)
						hm.removeFromHospitalProducts(hp).save(flush:true)
					}
					else{
						def he_ids = hm.hospitalElements.collect{it.id}
						for (he_id in he_ids){
							def he = HospitalElement.findById(he_id)
							if (he.hospitalMeasures.size()>1){
								he.removeFromHospitalMeasures(hm).save(flush:true)
								//hm = HospitalMeasure.findById(hm_id)
								hm.removeFromHospitalElements(he).save(flush:true)
							}
							else{
								he.removeFromHospitalMeasures(hm).save(flush:true)
								//hm = HospitalMeasure.findById(hm_id)
								hm.removeFromHospitalElements(he).save(flush:true)
								//he.delete()
							}
							hm = HospitalMeasure.findById(hm_id)
						}
						hp.removeFromHospitalMeasures(hm).save(flush:true)
						hm.removeFromHospitalProducts(hp).save(flush:true)
						//hm.delete()
					}
					hp = HospitalProduct.findById(hp_id)
				}
				//hp.discard()
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

	def show() {
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)

			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProducts.collect{it.product}

			render(contentType: "text/json") {
				name = result.name
				notes= result.notes
				id   = result.id
				ehr = result.ehr
				products = array {
					for (p in productList) {
						product id : p.id,
<<<<<<< HEAD
						name : p.name,
						code : p.code,
						measures : array {
							for (h in HospitalMeasure.list().findAll{it?.hospitalProducts.findAll{it.product == p}.size() >= 1}){
								measrue id : h.id,
								code : h.measure.code,
								name : h.measure.name,
								accepted : h.accepted,
								completed : h.completed,
								confirmed : h.confirmed,
								included : h.included,
								verified : h.verified
							}
						}
=======
								name : p.name,
								code : p.code,
								measures : array {
									for (h in HospitalMeasure.list().findAll{it?.hospitalProducts.findAll{it.product == p}.size() >= 1}){
										measrue id : h.id,
												code : h.measure.code,
												name : h.measure.name,
												notes : h.measure.notes,
												accepted : h.accepted,
												completed : h.completed,
												confirmed : h.confirmed,
												included : h.included,
												verified : h.verified
									}
								}
>>>>>>> loginPageHelp
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