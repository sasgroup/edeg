package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalMeasureController {

	private HospitalMeasure saveInstance (HospitalMeasure instance, def param) {
		instance.properties = param
		return instance.save(flush :true)
	}
	
	def save() {
		println "save me"
		def hospitalMeasureInstance  = saveInstance(new HospitalMeasure(), params)
		render(contentType: "text/json") {
					resp = "ok"
					message = "HospitalMeasure ${hospitalMeasureInstance.name} successfully created"
		}
		
	}
   
	def show() {
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)
			println params.id

			def hospitalProdcuts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProdcuts.collect{it.product}


			render(contentType: "text/json") {
				measures = array {
					for (p in productList) {
						for (h in HospitalMeasure.list().findAll{it?.hospitalProducts.findAll{it.product == p}.size() >= 1}){
										measrue id : h.id,
												code : h.measure.code,
												name : h.measure.name,
												accepted : h.accepted,
												completed : h.completed,
												confirmed : h.confirmed,
												included : h.included,
												verified : h.verified,
												productId: p.id
									}
								}
				}	

			}
		}	
	}
	
	def update(Long id, Long version) {
		println "Update"
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
					message = "hospitalMeasureInstance successfully updated"
				}
	}

	def delete(Long id) {
		println "Delete"
	}
}
