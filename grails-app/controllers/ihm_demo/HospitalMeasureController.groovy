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
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)
			println params.id

			def hospitalProdcuts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProdcuts.collect{it.product}


			render(contentType: "text/json") {
				measrue = array {
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
												product : p
									}
								}
				}	

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
