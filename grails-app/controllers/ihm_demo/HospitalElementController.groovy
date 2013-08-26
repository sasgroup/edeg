package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {

	private HospitalElement saveInstance (HospitalElement instance, def param) {
		//instance.properties = param
		instance.location = param.location
		instance.sourceEHR = param.sourceEHR
		instance.source = param.sourceEHR ? instance.hospital.ehr.code : param.source
		instance.codeType =  CodeType.valueOf(param.codeType.name)
		instance.valueType = ValueType.valueOf(param.valueType.name)
		instance.valueSet = param.valueSet
		instance.valueSetFile = param.valueSetFile
		// TODO append notes info
		instance.internalNotes = param.internalNotes
		instance.notes = param.notes
				
		if (param.markAsComplete){
			def mid = param.m_id as Long
			for(def hme in instance.hospitalMeasureElements){
				def hm = hme.hospitalMeasure
				if (hm.id == mid){
					hm.completed = true
					hm.save(flush :true)
				}
			}	
		}
		
		instance.save(flush :true)
		
		// TODO remove all hospital value sets
//		def hvSets = HospitalValueSet.findByHospitalElement(instance)
		
		if (param.hospitalValueSet)																
			for (hvs in param.hospitalValueSet){
				//new HospitalValueSet(code:hvs.code, mnemonic:hvs.mnemonic, codeType:hvs.codeType, hospitalElement:instance).save(flush:true)				
				new HospitalValueSet(code:hvs.code, mnemonic:hvs.mnemonic, codeType:CodeType.valueOf(hvs.codeType.name), hospitalElement:instance).save(flush:true)
			}
			
		
		/*if (param.elementExtraLocation)
			for (e in param.elementExtraLocation){
				println e
				new ElementExtraLocation(location:e.location, source:e.source, sourceEHR:e.sourceEHR, codeType:CodeType.valueOf(e.codeType), valueType: ValueType.valueOf(e.valueType), hospitalElement:instance).save(flush:true)
			}			*/
				
				
		return instance		
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
							hElement.codeType = defSettings.codeType
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
									hElement.codeType = defSettings.codeType
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
						codeType : hme.hospitalElement.codeType,
						dataElement : hme.hospitalElement.dataElement.code,
						element_notes:hme.hospitalElement.dataElement.notes,
						help:hme.hospitalElement.dataElement.help,
						hospitalValueSet : array {
							for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
								hvset code : hvs.code,
								mnemonic : hvs.mnemonic,
								codeType : hvs.codeType
							}
						},
					
						elementExtraLocation : array {
							for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
								elem location  : e.location,
									 source    : e.source
									 sourceEHR : e.sourceEHR 
									 codeType  : e.codeType
									 valueType : e.valueType								
							}
						}
						 
						
						//elementExtraLocation : ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)
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
					message = "Another User has updated hospitalElementInstance while you were editing"
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

