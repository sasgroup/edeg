package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {
	def sendMailService
	
	private HospitalElement saveInstance (HospitalElement instance, def param) {
		
		def ehrCode = instance.hospital.ehr.code
		
		// TODO: check here for possible changes to be reported via Email Notification
		def modificationDetected = false
		
		if (instance.location != param.location) 
			modificationDetected = true 
		instance.location = param.location
		
		if (instance.sourceEHR != (ehrCode == param.source))
			modificationDetected = true
		instance.sourceEHR = (ehrCode == param.source)
		
		if (instance.source != param.source)
			modificationDetected = true
		//instance.source = param.sourceEHR ? instance.hospital.ehr.code : param.source		
		instance.source = param.source
		
		def vType = param.valueType.name
		
		if (instance.valueType != ValueType.valueOf(vType))
			modificationDetected = true
		instance.valueType = ValueType.valueOf(vType)
		
		if (instance.valueSet != param.valueSet)
			modificationDetected = true
		instance.valueSet = param.valueSet

		if (instance.valueSetFile != param.valueSetFile)
			modificationDetected = true
		instance.valueSetFile = param.valueSetFile
		// TODO append notes info
		
		if (instance.internalNotes != param.internalNotes)
			modificationDetected = true
		instance.internalNotes = param.internalNotes

		if (instance.notes != param.notes)
			modificationDetected = true
		instance.notes = param.notes

		//TODO correct ValuesType
		//if (instance.valuesType != ValuesType.get(param?.valuesTypeId))
		//	modificationDetected = true
		instance.valuesType = ValuesType.list().get(0)
				
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
				new HospitalValueSet(code:hvs.code, mnemonic:hvs.mnemonic, hospitalElement:instance).save()
			}
			
		
		if (param.elementExtraLocation)
			for (e in param.elementExtraLocation){
				//TODO correct ValuesType
				if (e.valueType.name)
					new ElementExtraLocation(location:e.location, source:e.source, sourceEHR:(ehrCode==e.source), valueType: ValueType.valueOf(e.valueType.name), hospitalElement:instance, valuesType : ValuesType.get(1)).save()
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
							hElement.valueType = defSettings.valueType							
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
									hElement.valueType = defSettings.valueType									
								}
								hElement.save(flush:true)
							}
						}
					}
				}
			}

			def  result = HospitalMeasure.get(params.id)

			def hospitalElements =  result.hospitalMeasureElements //HospitalElement.list().findAll{it?.hospitalMeasure.findAll{it.id == result.id}.size() >= 1}

			render(contentType: "text/json") {
				hospitalElements = array {
					for (hme in hospitalElements) {
						hospitalElement id : hme.hospitalElement.id,
						version : hme.hospitalElement.version,
						internalNotes : hme.hospitalElement.internalNotes,
						location : hme.hospitalElement.location,
						notes : hme.hospitalElement.notes,
						source : hme.hospitalElement.source,
						sourceEHR : hme.hospitalElement.sourceEHR,
						valueSet : hme.hospitalElement.valueSet,
						valueSetFile : hme.hospitalElement.valueSetFile,
						valueType : hme.hospitalElement.valueType,						
						dataElement : hme.hospitalElement.dataElement.code,
						element_notes:hme.hospitalElement.dataElement.notes,
						help:hme.hospitalElement.dataElement.help,
						
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
									 valueType : e.valueType
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
}

