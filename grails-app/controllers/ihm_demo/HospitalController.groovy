package ihm_demo

import java.util.Date;
import java.util.Map;

import grails.converters.JSON
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {
	
	def sendMailService
	def securityService
	def fileUploadService
	
	def update(Long id, Long version) {
		
		if (params.clone){
			// TODO: clone action
			def currHospital = Hospital.get(params.id)
			def srcHospital = Hospital.get(params.src_id)
			if (null!=currHospital && null!=srcHospital){
				if ( cloneHospitalSettings(currHospital, srcHospital) ){
					render(contentType: "text/json") {
						resp = "ok"
						message = "Cloned Successfully"
					}
				}
				else{
					render(contentType: "text/json") {
						resp = "error"
						message = "Clone Failed"
					}
				}
			}
			else{
				render(contentType: "text/json") {
					resp = "error"
					message = "Please define valid Hospitals"
				}
			}
		}
		
		
		else if (params.apply){
			// update Hospital: set primary EHR
			def hospital = Hospital.get(params.id)
			hospital = saveHospital(hospital, params)

			def old_ids = HospitalProduct.list().findAll{it?.hospital == hospital}.collect{it.product.id}

			// update HospitalProduct list
			for (prId in params.product_ids) {
				def product = Product.get(prId)
				def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospital, product)
				if (!hospitalProduct) {
					hospitalProduct = new HospitalProduct(hospital:hospital, product:product, qa:"", notifyAdmin : false, notifyUser : false).save(flush:true)
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
						hospitalMeasure 	= new HospitalMeasure(hospital: hospital, measure: measure, accepted: false, completed: false, confirmed: false, verified: false, qa:"", notifyAdmin : false, notifyUser : false).save(flush:true)
					def hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, hospitalMeasure)
					if (!hospitalProductMeasure){
						def _included = measure.measureCategory.name == "CORE"
						hospitalProductMeasure 	= new HospitalProductMeasure(hospitalProduct:hospitalProduct, hospitalMeasure:hospitalMeasure, included:_included).save(flush:true)
					}
					//TODO ValyesType for HospitalElement
					for (de in measure.dataElements) {
						def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, de)
						def isNew = false
						if (!hospitalElement){
							hospitalElement = new HospitalElement(	hospital: hospital, 
																	dataElement: de, 
																	internalNotes : "", 
																	notes:"", 
																	location : "", 
																	source : hospital.ehr.code, 
																	sourceEHR : true, 
																	valueSet : "", 
																	valueSetFile : "", 
																	//valueType : ValueType.NotApplicable, 
																	valuesType : ValuesType.findByName("NotApplicable")
																	).save(flush:true)
							isNew = true
						}

						def defaultSetting = DataElementDefaults.findByDataElementAndEhr(de, hospital.ehr)
						if (defaultSetting) {
							if (isNew){
								hospitalElement.sourceEHR = true
								hospitalElement.source = hospital.ehr.code
								hospitalElement.location = defaultSetting.location
								//hospitalElement.valueType = defaultSetting.valueType
								hospitalElement.valuesType = deriveValuesType(defaultSetting)
							}
							else if (hospitalElement.sourceEHR){
								hospitalElement.sourceEHR = true
								hospitalElement.source = hospital.ehr.code
								//hospitalElement.valuesType = deriveValuesType(defaultSetting)
							}
						}
						else if (hospitalElement.sourceEHR){
							// TODO need client verification
							hospitalElement.sourceEHR = true
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
		}
		
		else if (params.submit) { //if just save
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
							//check for changing
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
								
							if (oldValueA != hospitalMeasure.accepted && hospitalMeasure.accepted)
								sendMailService.asseptMeasureThatCompleted(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date(), session?.user.login)
								
							if (oldValueV != hospitalMeasure.verified && hospitalMeasure.verified)
								sendMailService.verifieMeasure(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name, new Date(), session?.user.login)
								
							if (oldValueCo != hospitalMeasure.confirmed && hospitalMeasure.confirmed)
								sendMailService.omissionUserIdentifie(hospitalInstance?.email, hospitalInstance?.name, product?.name, msr?.name)
							
						}
						def hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, hospitalMeasure)
						boolean oldIncluded = hospitalProductMeasure.included
						if (hospitalProductMeasure)
							hospitalProductMeasure.included	= msr.included
						hospitalProductMeasure.save(flush:true)
						if (oldIncluded != hospitalProductMeasure.included && hospitalProductMeasure.included)
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
		
		
		else {
			render(status: 420, text: "SomeError")
		}
		
	}

	
	private ValuesType deriveValuesType(DataElementDefaults defaultSetting){
		if (defaultSetting.ids){
			String[] _ids = ((String)defaultSetting.ids).split(';') 
			def valuesTypes = ValuesType.list()
			for (vt in valuesTypes){
				if (_ids.contains(vt.id.toString()))
					return vt
			}
		}
		else
			return ValuesType.findByName("NotApplicable")
	}
	
	
	def unlinkProductAndMeasure(Long hp_id, Long hm_id){
		def hp = HospitalProduct.findById(hp_id)
		def hm = HospitalMeasure.findById(hm_id)
		def hpm = HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hp, hm);
		def hsize = hm.hospitalProductMeasures.size()

		hm.removeFromHospitalProductMeasures(hpm).save(/*flush:true*/)
		hp.removeFromHospitalProductMeasures(hpm).save(/*flush:true*/)
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

		hm.removeFromHospitalMeasureElements(hme).save(/*flush:true*/)
		he.removeFromHospitalMeasureElements(hme).save(/*flush:true*/)
		hme.hospitalElement = null
		hme.hospitalMeasure = null
		hme.delete(flush:true)

		if (hsize==1){
			he = HospitalElement.findById(he_id)
			HospitalValueSet.executeUpdate("delete HospitalValueSet hvs where hvs.hospitalElement=?", [he])
			ElementExtraLocation.executeUpdate("delete ElementExtraLocation eel where eel.hospitalElement=?", [he])
			he.delete(flush:true)
		}
	}

	
	
	def show() {
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)

			def hospitalProducts = HospitalProduct.findAllByHospital(result)
			
			render(contentType: "text/json") {
				name = result.name
				email = isNULL(result.email, "")
				notes= isNULL(result.notes,"")
				externalEHRs = isNULL(result.externalEHRs,"")
				populationMethod = isNULL(result.populationMethod,"ED-ALL")
				id   = result.id
				ehr = result.ehr
				products = array {
					for (hp in hospitalProducts) {
						product id : hp.product.id,
						name : hp.product.name,
						code : hp.product.code,
						help : isNULL(hp.product.help,""),
						notes : isNULL(hp.product.notes,""),
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
								notes : isNULL(hpm.hospitalMeasure.measure.notes,""),
								help : isNULL(hpm.hospitalMeasure.measure.help,"")
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
			
			// auth.jar re-check
			// 
			
			def firstEHR = Ehr.list().first()
			Map<String, String> allHospitals = securityService.getHospitalNameMap(request.getRemoteUser())
			allHospitals.each { key, value  ->
				def h = Hospital.findByIhmid(key)
				if (!h){
					h = new Hospital(name:value, ehr:firstEHR, notes:"", populationMethod: "ED-ALL", externalEHRs:"", ihmid:key)
					h.save(flush: true)
				}
				else if (h.name != value ){
					h.name = value
					h.save(flush: true)
				}
			}

			// from this point forward we should get 'sync'ed list of available hospitals			
			def results = Hospital.list([sort: 'name', order:'asc'])
			render(contentType: "text/json") {
				hospitals = array {
					for (p in results) {
						if (allHospitals[p.ihmid]){
							hospital 	name: p.name, 
										notes: isNULL(p.notes,""),
										email: isNULL(p.email,""), 
										id: p.id
						}
					}
				}
			}
		}
	}
	
	
	
	
	private Boolean cloneHospitalSettings(Hospital curr, Hospital srch){
		
		// STEP 1 : clean UP current hospital
		def hProducts = HospitalProduct.findAllByHospital(curr)
		def hMeasures = HospitalMeasure.findAllByHospital(curr)
		def hElements = HospitalElement.findAllByHospital(curr)
		for (he in hElements){
			HospitalValueSet.executeUpdate("delete HospitalValueSet hvs where hvs.hospitalElement=?", [he])
			ElementExtraLocation.executeUpdate("delete ElementExtraLocation eel where eel.hospitalElement=?", [he])
			HospitalMeasureElement.executeUpdate("delete HospitalMeasureElement hme where hme.hospitalElement=?", [he])
		}
		for (hm in hMeasures){
			HospitalProductMeasure.executeUpdate("delete HospitalProductMeasure hpm where hpm.hospitalMeasure=?", [hm])
		}
		HospitalElement.executeUpdate("delete HospitalElement he where he.hospital=?", [curr])
		HospitalMeasure.executeUpdate("delete HospitalMeasure hm where hm.hospital=?", [curr])
		HospitalProduct.executeUpdate("delete HospitalProduct hp where hp.hospital=?", [curr])
		
		
		
		// STEP 2 : update
		curr.ehr = srch.ehr
		curr.populationMethod = srch.populationMethod
		curr.externalEHRs = srch.externalEHRs
		curr.save(flush:true)
		
		
		// STEP 3 : apply+clone
	
		// clone HospitalProducts
		def shProducts = HospitalProduct.findAllByHospital(srch)
		def shMeasures = HospitalMeasure.findAllByHospital(srch)
		def shElements = HospitalElement.findAllByHospital(srch)
		
		for (srcHP in shProducts) {
			def currHP = new HospitalProduct(hospital:curr, product:srcHP.product)
			currHP.qa = srcHP.qa
            currHP.notifyAdmin = srcHP.notifyAdmin
            currHP.notifyUser = srcHP.notifyUser
			currHP.save(flush:true)
		}
		// clone HospitalMeasures
		for (srcHM in shMeasures) {
			def currHM = new HospitalMeasure(hospital:curr, measure:srcHM.measure)
			currHM.accepted = srcHM.accepted
			currHM.completed = srcHM.completed
			currHM.confirmed = srcHM.confirmed
			currHM.verified = srcHM.verified
			currHM.qa = srcHM.qa
            currHM.notifyAdmin = srcHM.notifyAdmin
            currHM.notifyUser = srcHM.notifyUser
			currHM.save(flush:true)
		}
		// clone HospitalElements
		for (srcHE in shElements) {
			def currHE = new HospitalElement(hospital:curr, dataElement:srcHE.dataElement)
			currHE.internalNotes 	= srcHE.internalNotes
			currHE.location 		= srcHE.location
			currHE.notes 			= srcHE.notes
			currHE.source 			= srcHE.source
			currHE.sourceEHR 		= srcHE.sourceEHR
			currHE.valueSet 		= srcHE.valueSet
			//currHE.valueType 		= srcHE.valueType
			currHE.valuesType 		= srcHE.valuesType
			currHE.valueSetFile		= ""
			currHE.save(flush:true)
			if (srcHE.valueSetFile){
				currHE.valueSetFile 	= currHE.id + "_" + srcHE.valueSetFile.substring(srcHE.valueSetFile.indexOf("_")+1)
				fileUploadService.duplicateFile(srcHE.valueSetFile, currHE.valueSetFile, "uploadFiles")
				currHE.save(flush:true)
			}
			// clone ExtraLocations
			def xLocations = ElementExtraLocation.findAllByHospitalElement(srcHE)
			for (srcXL in xLocations) {
				def currXL = new ElementExtraLocation(hospitalElement:currHE)
				currXL.location 	= srcXL.location
				currXL.source		= srcXL.source
				currXL.sourceEHR	= srcXL.sourceEHR
				//currXL.valueType	= srcXL.valueType
				currXL.valuesType	= srcXL.valuesType
				currXL.save(flush:true)
			}
			// clone HospitalValueSet
			def eValueSets = HospitalValueSet.findAllByHospitalElement(srcHE)
			for (srcHVS in eValueSets) {
				def currHVS = new HospitalValueSet(hospitalElement:currHE)
				currHVS.code		= srcHVS.code
				currHVS.mnemonic	= srcHVS.mnemonic
				currHVS.save(flush:true)
			}
		}
		
		// clone HP~HM~HE relationships
		for (srcHM in shMeasures) {
			def xPM = HospitalProductMeasure.findAllByHospitalMeasure(srcHM)
			for (srcPM in xPM) {
				def currHP = HospitalProduct.findByHospitalAndProduct(curr, srcPM.hospitalProduct.product)
				def currHM = HospitalMeasure.findByHospitalAndMeasure(curr, srcPM.hospitalMeasure.measure)
				def currPM = new HospitalProductMeasure(hospitalMeasure:currHM, hospitalProduct:currHP)
				currPM.included = srcPM.included
				currPM.save(flush:true)
			}
			
			def xME = HospitalMeasureElement.findAllByHospitalMeasure(srcHM)
			for (srcME in xME) {
				def currHE = HospitalElement.findByHospitalAndDataElement(curr, srcME.hospitalElement.dataElement)
				def currHM = HospitalMeasure.findByHospitalAndMeasure(curr, srcME.hospitalMeasure.measure)
				def currME = new HospitalMeasureElement(hospitalMeasure:currHM, hospitalElement:currHE)
				currME.save(flush:true)
			}
		}
		
		sendMailService.hospitalOptionsCloned(curr?.email, curr.name, srch.name, new Date(), session?.user.login)
	
		render(contentType: "text/json") {
			resp = "ok"
			message = "Hospital options are stransferred from [ ${srch.name} ] to [ ${curr.name} ] "
		}
		
		
		return true
	}
	

	private Hospital saveHospital (Hospital hospitalInstance, GrailsParameterMap params) {
		def modificationDetected = false
		//save and checks for Notification
		if (isNULL(hospitalInstance.email,"") != params?.email)				
			modificationDetected = true 	
		hospitalInstance.email = params?.email
		
		if (isNULL(hospitalInstance.externalEHRs,"") != params?.externalEHRs)
			modificationDetected = true 	
		hospitalInstance.externalEHRs = params?.externalEHRs
		
		if (isNULL(hospitalInstance.populationMethod,"") !=  params?.populationMethod)
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
		
		if (!modificationDetected && isNULL(hospitalInstance.notes,"") != params?.notes){
			hospitalInstance.notes = params?.notes
			hospitalInstance.save(flush:true)
			sendMailService.updateHospitalConfig("", hospitalInstance.name, new Date())
		}
			
		return hospitalInstance
	}
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
	
}