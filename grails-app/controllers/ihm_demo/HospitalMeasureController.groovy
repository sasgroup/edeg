package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalMeasureController {

	private HospitalMeasure saveInstance (HospitalMeasure instance, def param) {
		instance.properties = param
		return instance.save(flush :true)
	}
	
	def save() {
		println "save"
		
	}
   
	def show() {
		if (params.id && HospitalMeasure.exists(params.id)) {//show individual
			println "show I"
			def  result = HospitalMeasure.get(params.id)
			render(contentType: "text/json") {
				version = result.version
				id = result.id
				code = result.measure.code
				name = result.measure.name
				accepted = result.accepted
				completed = result.completed
				confirmed = result.confirmed
				included = result.included
				verified = result.verified
			}
		}
	}
	
	def update(Long id, Long version) {
		println "Update"
	}

	def delete(Long id) {
		println "Delete"
	}
}
