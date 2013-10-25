package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {
	def sendMailService
	
	private HospitalElement saveInstance (HospitalElement instance, def param) {
		
		def ehrCode = instance.hospital.ehr.code
		
		// TODO: check here for possible changes to be reported via Email Notification
		def modificationDetected = false
		
		if ((instance.location && param.location!='') && instance.location != param.location) 
			modificationDetected = true 
		instance.location = param.location
		
		if (instance.sourceEHR && param.source!='' && instance.sourceEHR != (ehrCode == param.source))
			modificationDetected = true
		instance.sourceEHR = (ehrCode == param.source)
		
		if (instance.source && param.source!='' && instance.source != param.source)
			modificationDetected = true
		//instance.source = param.sourceEHR ? instance.hospital.ehr.code : param.source		
		instance.source = param.source
		
		if (instance.valueSet && param.valueSet!='' && instance.valueSet != param.valueSet)
			modificationDetected = true
		instance.valueSet = param.valueSet

		if (instance.valueSetFile && param.valueSetFile!='' && instance.valueSetFile != param.valueSetFile)
			modificationDetected = true
		instance.valueSetFile = param.valueSetFile
		// TODO append notes info
		
		if (instance.internalNotes && param.internalNotes!='' && instance.internalNotes != param.internalNotes)
			modificationDetected = true
		instance.internalNotes = param.internalNotes

		if (instance.notes && param.notes!='' && instance.notes != param.notes)
			modificationDetected = true
		instance.notes = param.notes

		//TODO correct ValuesType
		if (instance.valuesTypeId && param.valuesTypeId!='' && instance.valuesType != ValuesType.get(param?.valuesTypeId))
			modificationDetected = true
		instance.valuesType = ValuesType.get(param?.valuesTypeId)
		
				
		/*if (param.markAsComplete){
			def mid = param.m_id as Long
			for(def hme in instance.hospitalMeasureElements){
				def hm = hme.hospitalMeasure
				if (hm.id == mid){
					boolean oldValueC = hm.completed
					hm.completed = true
					hm.save(flush :true)
					println param
					if (oldValueC != hm.completed && hm.completed)
						sendMailService.markMeasureAsComplete(instance?.hospital.email, instance?.hospital.name, Product.get(param?.p_id)?.name, HospitalMeasure.get(param?.m_id)?.measure.name, new Date(), session?.user.login)
				}
			}	
		}
		*/
		
		instance.save(flush :true)
		if (modificationDetected)
			sendMailService.updateDataElement(instance?.hospital.email, instance?.hospital.name, instance?.dataElement.name, HospitalMeasure.get(param?.m_id)?.measure.name, new Date(), session?.user.login)
		instance.hospitalMeasureElements
		
		// TODO remove all hospital value sets
		HospitalValueSet.executeUpdate("delete HospitalValueSet hvs where hvs.hospitalElement=?", [instance])
		ElementExtraLocation.executeUpdate("delete ElementExtraLocation eel where eel.hospitalElement=?", [instance])
		
		if (param.hospitalValueSet)																
			for (hvs in param.hospitalValueSet){
				new HospitalValueSet(code:hvs.code, mnemonic:hvs.mnemonic, hospitalElement:instance).save(flush:true)
			}
			
		
		if (param.elementExtraLocation)
			for (e in param.elementExtraLocation){
				//TODO correct ValuesType
				if (e.valuesTypeId){
					ElementExtraLocation xl = new ElementExtraLocation(	location:e.location, 
																		source:e.source, 
																		sourceEHR:(ehrCode==e.source), 
																		//valueType: ValueType.valueOf(e.valueType.name), 
																		hospitalElement:instance, 
																		valuesType : ValuesType.get(e.valuesTypeId))
					xl.save(flush:true)
				}
			}
		return instance.save(flush :true)	
	}

	def save() {
		def hospitalElementInstance  = saveInstance(new HospitalElement(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "HospitalElement ${hospitalElementInstance.dataElement.name} successfully created"
		}
	}
	
	def show() {
		if (params.id && HospitalMeasure.exists(params.id)) {
			
			if (params.defaults){
				if (params.he_id){
					println "reset to default settings only required HospitalElement"
					def hElement = HospitalElement.get(params.he_id)
					if (hElement && hElement.sourceEHR){
						def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
						if (defSettings && hElement.sourceEHR) {
							hElement.location = defSettings.location
							// TODO: correct for values Type
							//hElement.valueType = defSettings.valueType							
						}
						hElement.save(flush:true)
					}
				}
				else{
					println "reset all hospital element to default settings"
					def hMeasure = HospitalMeasure.get(params.id)
					if (hMeasure){
						def hElementIds =  hMeasure.hospitalMeasureElements.collect{it.hospitalElement.id}
						for (hElementId in hElementIds){
							def hElement = HospitalElement.get(hElementId)
							if (hElement && hElement.sourceEHR){
								def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
								if (defSettings) {
									hElement.location = defSettings.location
									// TODO: correct for ValuesType
									//hElement.valueType = defSettings.valueType									
								}
								hElement.save(flush:true)
							}
						}
					}
				}
			}

			def  result = HospitalMeasure.get(params.id)

			HospitalProduct hospitalProduct = HospitalProduct.findByHospitalAndProduct(Hospital.get(params.h_id), Product.get(params.p_id))
			HospitalProductMeasure hospitalProductMeasure 	= HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hospitalProduct, result)
			def hospitalElements = new ArrayList()
			//TODO verify permission to view HospitalMeasure
			if ((session.user.role.equals("user") && hospitalProductMeasure.included)||session.user.role.equals("admin")) 			
				hospitalElements =  result.hospitalMeasureElements //HospitalElement.list().findAll{it?.hospitalMeasure.findAll{it.id == result.id}.size() >= 1}
			render(contentType: "text/json") {
				hospitalElements = array {
					for (hme in hospitalElements) {
						hospitalElement id : hme.hospitalElement.id,
						version : hme.hospitalElement.version,
						internalNotes : isNULL(hme.hospitalElement.internalNotes,""),
						location : isNULL(hme.hospitalElement.location,""),
						notes : isNULL(hme.hospitalElement.notes,""),
						source : isNULL(hme.hospitalElement.source,""),
						sourceEHR : hme.hospitalElement.sourceEHR,
						valueSet : isNULL(hme.hospitalElement.valueSet,""),
						valueSetFile : isNULL(hme.hospitalElement.valueSetFile,""),
						//valueType : hme.hospitalElement.valueType,
						valuesTypeId : hme.hospitalElement.valuesType.id,
						dataElement : hme.hospitalElement.dataElement.code,
						element_notes:hme.hospitalElement.dataElement.notes,
						help:isNULL(hme.hospitalElement.dataElement.help,""),
						ids : deriveIds(hme.hospitalElement),
						
						hospitalValueSet : array {
							for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
								hvset code : hvs.code,
								mnemonic : hvs.mnemonic								
							}
						},
					
						elementExtraLocation : array {
							for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
								elem location  : e.location,
									 source    : e.source,
									 sourceEHR : e.sourceEHR,									
									 //valueType : e.valueType,
									 valuesTypeId : e.valuesType.id
							}
						}
					}
				}
			}
		}
	}

	def update(Long id, Long version) {
		def hospitalElementInstance = HospitalElement.get(id)

		if  (!hospitalElementInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		if (params.version != null) {
			if (hospitalElementInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record [${hospitalElementInstance.dataElement.name}]."
				}
			}
		}

		hospitalElementInstance  = saveInstance(hospitalElementInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "The Hospital Element is updated successfully"
		}
	}

	def delete(Long id) {
		println "Delete"
	}
	
	
	private String deriveIds(HospitalElement hElement){
		def defSettings = DataElementDefaults.findByDataElementAndEhr(hElement.dataElement, hElement.hospital.ehr)
		if (defSettings) {
			return defSettings.ids
		}
		return ""
	}
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
}







