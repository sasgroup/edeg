package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {

	private HospitalElement saveInstance (HospitalElement instance, def param) {
		//instance.properties = param
		instance.location = param.location
		instance.sourceEHR = param.sourceEHR
		instance.source = param.source
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
					hm.save()
				}
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
						hospitalValueSet : HospitalValueSet.findByHospitalElement(hme.hospitalElement),
						elementExtraLocation : ElementExtraLocation.findByHospitalElement(hme.hospitalElement)
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

