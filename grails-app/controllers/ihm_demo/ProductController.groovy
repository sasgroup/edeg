package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ProductController {
	
	private Product saveInstance (Product instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.notes = isNULL(param?.notes,"")
		instance.help = isNULL(param?.help,"")
				
		if (instance.id) {
			instance.measures.clear()
		}
		
		for (measure in param.measures) {
			instance.addToMeasures(Measure.get(measure.mid))
		}
		instance.save(flush :true)
		
		return instance
	}
	
	def save() {
		def productInstance  = saveInstance(new Product(), params)
		if (productInstance)
			render(contentType: "text/json") {
						resp = "ok"
						message = "Product ${productInstance.code} has been successfully created"
			}
		else{
			render(contentType: "text/json") {
				resp = "error"
				message = "Validation Error ID filed should be Unique..."
			}
		}
	}

	def show() {
		if (params.id && Product.exists(params.id)) {
			def  pr = Product.get(params.id)

			render(contentType: "text/json") {
				version = pr.version
				id   = pr.id
				code = pr.code 
				name = pr.name 
				notes = isNULL(pr.notes,"")
				help = isNULL(pr.help,"")
				measures =  array {
					for (m in pr?.measures) {
						measure  	mname: m.name, 
									mid: m.id, 
									mcode: m.code 
					}
				}
				hospitals = array {
					for (h in HospitalProduct.findAllByProduct(pr)) {
						hospital  	hname: h.hospital.name, 
									hid: h.hospital.id
					}
				}
			}
		} 
		else {
			def results = Product.list()
	
			render(contentType: "text/json") {
				products = array {
					for (p in results) {
						product version: p.version,
								code: p.code, 
								name: p.name, 
								notes: isNULL(p.notes,""), 
								id: p.id
					}
				}
			}
		}	
	}

	def update(Long id, Long version) {
		def productInstance = Product.get(id)

		if  (!productInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
            if (productInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${productInstance.code}."					
				}
			} 
		 }	
		
		productInstance  = saveInstance(productInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Product ${productInstance.code} has been successfully updated"
		}
	}
	

	def delete(Long id) {
		def product = Product.findById(params.id)
		
		def measuresDep = product.measures ? true : false
		def hospitalsDep = HospitalProduct.findByProduct(product) ? true : false
		
		String name = product.name
		String code = product.code
		String code2 = product.code

		if (measuresDep || hospitalsDep) {
			render(status: 420, text: "Product ${code} cannot be deleted because of existing dependencies")
		} 
		else {
			product?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Product ${code2} has been successfully deleted"
			}
		}
	}

	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
}