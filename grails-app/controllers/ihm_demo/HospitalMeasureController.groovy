package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalMeasureController {

	private HospitalMeasure saveInstance (HospitalMeasure instance, def param) {
		instance.properties = param
		return instance.save(flush :true)
	}

	def save() {
		def hospitalMeasureInstance  = saveInstance(new HospitalMeasure(), params)
		render(contentType: "text/json") {
			resp = "ok"
			version = hospitalMeasureInstance.version
			message = "HospitalMeasure ${hospitalMeasureInstance.measure.name} successfully created"
		}
	}

	def show() {
		if(!params?.hm){
			//println "1"
			if (params.id && Hospital.exists(params.id)) {
				def  result = Hospital.get(params.id)
				def hospitalProducts = HospitalProduct.findAllByHospital(result)

				render(contentType: "text/json") {
					measures = array {
						for (hp in hospitalProducts) {
							for (hpm in hp.hospitalProductMeasures){
								measure id : hpm.hospitalMeasure.id,
								code : hpm.hospitalMeasure.measure.code,
								name : hpm.hospitalMeasure.measure.name,
								accepted : hpm.hospitalMeasure.accepted,
								completed : hpm.hospitalMeasure.completed,
								confirmed : hpm.hospitalMeasure.confirmed,
								included : hpm.included,
								verified : hpm.hospitalMeasure.verified
							}
						}
					}
				}
			} 
		} else {
			if (HospitalMeasure.exists(params.id)) {
				HospitalMeasure hospitalMeasure = HospitalMeasure.get(params.id)
				
				render(contentType: "text/json") {
					id = hospitalMeasure.id
					code = hospitalMeasure.measure.code
					name = hospitalMeasure.measure.name
					accepted = hospitalMeasure.accepted
					completed = hospitalMeasure.completed
					confirmed = hospitalMeasure.confirmed
					verified = hospitalMeasure.verified
					qa = hospitalMeasure.qa
				}
			}	
		}	
	}

	def update(Long id, Long version) {
		def hospitalMeasureInstance = HospitalMeasure.get(id)

		if  (!hospitalMeasureInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		if (params.version != null) {
			if (hospitalMeasureInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated hospitalMeasureInstance while you were editing"
				}
			}
		}

		hospitalMeasureInstance  = saveInstance(hospitalMeasureInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			version = hospitalMeasureInstance.version
			message = "The Hospital Measure is updated successfully"
		}
	}

	def delete(Long id) {
		println "Delete"
	}
}
