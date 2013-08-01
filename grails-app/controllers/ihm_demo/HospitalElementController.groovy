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

			def hospitalElements =  result.hospitalElements //HospitalElement.list().findAll{it?.hospitalMeasure.findAll{it.id == result.id}.size() >= 1}

			render(contentType: "text/json") {
				hospitalElements = array {
					for (he in hospitalElements) {
						hospitalElement id : he.id,
										internalNotes : he.internalNotes,
										location : he.location,
										notes : he.notes,
										source : he.source,
										sourceEHR : he.sourceEHR,
										valueSet : he.valueSet,
										valueSetFile : he.valueSetFile,
										valueType : he.valueType,
										codeType : he.codeType,
										dataElement : he.dataElement.code,
										hospitalValueSet : HospitalValueSet.findByHospitalElement(he),
										elementExtraLocation : ElementExtraLocation.findByHospitalElement(he)
					}
				}
			}
		}	
	}
	
}

