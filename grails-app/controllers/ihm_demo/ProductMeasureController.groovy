package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ProductMeasureController {	
	
	def show() {
		println "Show"
		println params.id
		if (params.id && Product.exists(params.id)) {
			println "One"
			def  pr = Product.get(params.id)			

			render(contentType: "text/json") {			
				array {
					for (m in pr?.measures) {
						measure  name: m.name, id: m.id, code: m.code, accepted:false, completed: false, confirmed: false, included: false, verified: false
					}
				}				
			}
		} else {
			println "NExt"
			def results = Product.list()
	
			render(contentType: "text/json") {
				products = array {
					for (p in results) {
						product version: p.version,
								code: p.code, 
								name: p.name, 
								notes: p.notes, 
								id: p.id,
								measures :  array {
									for (m in p?.measures) {
										measure  mname: m.name, mid: m.id, mcode: m.code
									}
								},
								hospitals : array {
									for (h in p?.hospitals) {
										hospital  hname: h.name, hid: h.id
									}
								}
					}
				}
			}
		}	
	}

	def update(Long id, Long version) {
		println "Update"
		def productInstance = Product.get(id)

		if  (!productInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
			 println productInstance.version > params.version
            if (productInstance.version > params.version) {
				println 'inside'
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated product(${productInstance.name}) while you were editing"
				}
			} 
		 }	
		
		productInstance  = saveInstance(productInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Product ${productInstance.name} successfully updated"
		}
	}
	


	def delete(Long id) {
		println "Delete"

		def product = Product.findById(params.id)
		String name = product.name
		println ("product.measures:" + product.measures)

		def measuresDep = product.measures ? true : false

		def hospitalsDep = product.hospitals ? true : false

		if (measuresDep || hospitalsDep) {
			render(status: 420, text: "Product ${name} cannot be deleted because of existing dependencie")
		} else {
			product?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Product ${name} successfully deleted"
			}
		}
	}
	
	private Product saveInstance (Product instance, def param) {
		
		instance.name = param.name
		instance.code = param.code
		instance.notes = param.notes
		
		if (instance.id) {
			instance.hospitals.clear()
			instance.measures.clear()
		}	
		
		for (hospital in param.hospitals) {
			instance.addToHospitals(Hospital.get(hospital.hid))
		}
		
		for (measure in param.measures) {
			instance.addToMeasures(Measure.get(measure.mid))
		}
	
		return instance.save(flush :true)
	}
}