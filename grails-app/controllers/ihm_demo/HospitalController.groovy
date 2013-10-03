package ihm_demo

import java.util.Date;
import java.util.Map;

import grails.converters.JSON
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {
	
	def sendMailService
	def securityService
	
	
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
						message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${hospitalInstance.name}."
					}
				}
			}
			hospitalInstance = saveHospital(hospitalInstance, params)
			
			for (prod in params.products){
				def product = Product.get(prod.id)
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospitalInstance, product)
				if (hospitalProduct){
					for (msr in prod.measures){
						def hospitalMeasure = HospitalMeasure.get(msr.id)
						if (hospitalMeasure){

							boolean oldValueC = hospitalMeasure.completed
							boolean oldValueA = hospitalMeasure.accepted
							boolean oldValueV = hospitalMeasure.verified
							boolean oldValueCo = hospitalMeasure.confirmed
							
							
							hospitalMeasure.accepted = msr.accepted
							hospitalMeasure.completed = msr.completed
							hospitalMeasure.confirmed = msr.confirmed
							hospitalMeasure.verified = msr.verified
							hospitalMeasure.save(flush:true)
							
							if (oldValueC != hospitalMeasure.completed && hospitalMeasure.completed)
								sendMailService.markMeasureAsComplete(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date(), session?.user.login)
								
							if (oldValueA != hospitalMeasure.accepted && hospitalMeasure.completed && hospitalMeasure.accepted)	
								sendMailService.asseptMeasureThatCompleted(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date(), session?.user.login)
								
							if (oldValueV != hospitalMeasure.verified && hospitalMeasure.verified)
								sendMailService.verifieMeasure(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date(), session?.user.login)
								
							if (oldValueCo != hospitalMeasure.confirmed && hospitalMeasure.confirmed)
								sendMailService.omissionUserIdentifie(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name)
							
						}
						def hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, hospitalMeasure)
						boolean oldIncludedo = hospitalProductMeasure.included
						if (hospitalProductMeasure)
							hospitalProductMeasure.included	= msr.included
						hospitalProductMeasure.save(flush:true)
						if (oldIncludedo != hospitalProductMeasure.included && hospitalProductMeasure.included)
						sendMailService.includeMeasureIntoHospitalProduct(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date())
							
							
					}
				}
			}

			def result = hospitalInstance
			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			render(contentType: "text/json") {
				resp = "ok"
				message = "Hospital ${hospitalInstance.name} has been successfully updated"
			}
		}
		//else if (!params.clone){
			// TODO: clone action
		//}
		else if (params.ehr_id) {
			// update Hospital set EHR
			def hospital = Hospital.get(params.id)
			hospital = saveHospital(hospital, params)

			def old_ids = HospitalProduct.list().findAll{it?.hospital == hospital}.collect{it.product.id}

			for (prId in params.product_ids) {//update HospitalProduct
				def product = Product.get(prId)
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospital, product)
				if (!hospitalProduct) {
					hospitalProduct = new HospitalProduct(hospital:hospital, product:product).save(flush:true)
					sendMailService.assignProductToHospital(hospital?.email, hospital?.name, product?.name, new Date())
				}		

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
					if (!hospitalProductMeasure){
						def _included = measure.measureCategory.name == "CORE" 
						hospitalProductMeasure 	= new HospitalProductMeasure(hospitalProduct:hospitalProduct, hospitalMeasure:hospitalMeasure, included:_included).save(flush:true)
					}

					for (de in measure.dataElements) {
						def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, de)
						def isNew = false
						if (!hospitalElement){
							hospitalElement = new HospitalElement(hospital: hospital, dataElement: de, internalNotes : "", notes:"", location : "", source : "", sourceEHR : false, valueSet : "", valueSetFile : "", valueType : ValueType.NotApplicable)
							isNew = true
						}

						def defaultSetting = DataElementDefaults.findByDataElementAndEhr(de, hospital.ehr)
						if (defaultSetting) {
							if (isNew){
								hospitalElement.sourceEHR = true
								hospitalElement.source = hospital.ehr.code
								hospitalElement.location = defaultSetting.location
								hospitalElement.valueType = defaultSetting.valueType
								//hospitalElement.codeType = defaultSetting.codeType
							}
							else if (hospitalElement.sourceEHR){
								hospitalElement.sourceEHR = true
								hospitalElement.source = hospital.ehr.code
								//hospitalElement.location = defaultSetting.location
								//hospitalElement.valueType = defaultSetting.valueType
								//hospitalElement.codeType = defaultSetting.codeType
							}
						}
						else{
							// TODO need client verification
							hospitalElement.source = hospital.ehr.code
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
				sendMailService.deAssignProductFromHospital(hospital?.email, hospital?.name, p?.name, new Date())
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
				email = (result.email)?result.email:""
				notes= result.notes
				externalEHRs = (result.externalEHRs)?result.externalEHRs:""
				populationMethod = result.populationMethod
				id   = result.id
				ehr = result.ehr
				products = array {
					for (hp in hospitalProducts) {
						product id : hp.product.id,
						name : hp.product.name,
						code : hp.product.code,
						help : hp.product.help,
						notes : hp.product.notes,
						measures : array {
							for (hpm in hp.hospitalProductMeasures){
								measure id : hpm.hospitalMeasure.id,
								measureCategory :  hpm.hospitalMeasure?.measure?.measureCategory?.name,
								code : hpm.hospitalMeasure.measure.code,
								name : hpm.hospitalMeasure.measure.name,
								accepted : hpm.hospitalMeasure.accepted,
								completed : hpm.hospitalMeasure.completed,
								confirmed : hpm.hospitalMeasure.confirmed,
								included : hpm.included,
								verified : hpm.hospitalMeasure.verified,
								notes : hpm.hospitalMeasure.measure.notes,
								help : hpm.hospitalMeasure.measure.help
							}
						}
					}
				}
			}
		}
		else {
			
			// IMPORTANT
			// here we are expecting to get the whole list of Hospitals
			// and if we detect any new hospital we just add it to our list
			// The question here is: should we remove hospital on eDEG side if we don't get it from ihmSecurity call?
			Map<String, String> allHospitals = securityService.getHospitalNameMap(request.getRemoteUser())
			allHospitals.each { key, value  ->
				def h = Hospital.findByName(value)
				if (!h){
					h = new Hospital(name:value, ehr:Ehr.findByCode("MEDITECH 6.2"), notes:"", populationMethod: "ED-ALL", externalEHRs:"")
					h.save(flush: true)
				}
			}

			// from this point forward we should get 'sync'ed list of available hospitals			
			def results = Hospital.list([sort: 'name', order:'asc'])
			render(contentType: "text/json") {
				hospitals = array {
					for (p in results) {
						hospital name: p.name, notes: p.notes,email: p.email, id: p.id
					}
				}
			}
		}
	}

private Hospital saveHospital (Hospital hospitalInstance, GrailsParameterMap params) {
		def modificationDetected = false
		
		if (!hospitalInstance.email && params?.email != "" &&   hospitalInstance.email != params?.email)				
			modificationDetected = true 	
		hospitalInstance.email = params?.email
		
		if (!hospitalInstance.externalEHRs && params?.externalEHRs != "" && hospitalInstance.externalEHRs != params?.externalEHRs)
			modificationDetected = true 	
		hospitalInstance.externalEHRs = params?.externalEHRs
		
		if (!hospitalInstance.populationMethod && params?.populationMethod != "" && hospitalInstance.populationMethod != params?.populationMethod)
			modificationDetected = true 	
		hospitalInstance.populationMethod 	= params?.populationMethod
		
		if (params.apply){
			def ehrID = (params?.ehr_id) ? params?.ehr_id : params.ehr.id 
			if (!hospitalInstance.ehr && ehrID != "" && hospitalInstance.ehr 	!= Ehr.get(ehrID))	
				modificationDetected = true 	
			hospitalInstance.ehr = Ehr.get(ehrID)
		}
		
		
		if (modificationDetected) { 
			hospitalInstance.notes = params?.notes
			hospitalInstance.save(flush:true)
			sendMailService.updateHospitalConfig(hospitalInstance?.email, hospitalInstance.name, new Date())
		} 
		
		if ((!modificationDetected && !hospitalInstance.notes && params?.notes != "" && hospitalInstance.notes != params?.notes) || (hospitalInstance.notes && !params?.notes)){
			hospitalInstance.notes = params?.notes
			hospitalInstance.save(flush:true)
			sendMailService.updateHospitalConfig("", hospitalInstance.name, new Date())
		}
			
		return hospitalInstance
	}
	
	
}