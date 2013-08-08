package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {

	def save() {
		println "save me"
	}
	
	def update(Long id, Long version) {
		println "Update"
	}

	def delete(Long id) {
		println "Delete"
	}
	def show() {
		if (params.id && HospitalMeasure.exists(params.id)) {
			def  result = HospitalMeasure.get(params.id)

			def hospitalElements =  result.hospitalMeasureElements //HospitalElement.list().findAll{it?.hospitalMeasure.findAll{it.id == result.id}.size() >= 1}

			render(contentType: "text/json") {
				hospitalElements = array {
					for (hme in hospitalElements) {
						hospitalElement id : hme.hospitalElement.id,
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
	
}

