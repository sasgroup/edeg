package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {
	//apply need JSON: Hospital(id)  with Prodcuts(checked id) and Ehr (selected id)
	def apply() {
		println "save"
		def hospital = new Hospital(request.JSON)
		render( hospital as JSON )
	}
   
	def show() {
		println "show"
		println params.id
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)
			println params.id

			def hospitalProdcuts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProdcuts.collect{it.product}
			
								
			render(contentType: "text/json") {			
				name =result.name
				notes=result.notes
				id   =result.id	
				products = array {
					for (p in productList) {
						product id : p.id, 
								name : p.name,
								code : p.code, 
								measures : array {
									for (h in HospitalMeasure.list().findAll{it?.hospitalProducts.findAll{it.product == p}.size() >= 1}){
										measrue id : h.id, 
												code : h.measure.code,
												name : h.measure.name,
												accepted : h.accepted,
												completed : h.completed,
												confirmed : h.confirmed,
												included : h.included,
												verified : h.verified
									}
								}
					}
				}		
				
			}
		}
		else {
			def results = Hospital.list()
			
			render(contentType: "text/json") {
				hospitals = array {
					for (p in results) {
						hospital name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

	//update current Hospital need JSON: Hospital(id) with Prodcuts(checked id) and Ehr (selected id)
	def update(Long id, Long version) {
		println "update"
		
		def hospitalInstance = Hospital.get(id)
				
		hospitalInstance.properties = params

		hospitalInstance.save(flush: true)
	}
	

	def delete(Long id) {
		def hospitalInstance = Hospital.get(id)
	   
		try {
			hospitalInstance.delete(flush: true)
		}
		catch (DataIntegrityViolationException e) {
		   
		}
	}
}
