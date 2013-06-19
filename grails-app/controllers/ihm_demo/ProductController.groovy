package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ProductController {
   	
	def save() {
		println "save"
		def product = new Product(request.JSON)
		render( product.save() as JSON )
	}
   
	def show() {
		println "show"
		if (params.id && Product.exists(params.id)) {
			def  result = Product.get(params.id)
			def product_measures = result?.measures
						
			render(contentType: "text/json") {			
				code =result.code
				name =result.name
				notes=result.notes
				id   =result.id			
				
				measures = array {
					for (m in product_measures) {
						measure  mname: m.name, mid: m.id
					}
				}
			}
		}
		else {
			def results = Product.list()
			
			render(contentType: "text/json") {
				products = array {
					for (p in results) {
						product code: p.code, name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

    
    def update(Long id, Long version) {
		println "update"
		
        def productInstance = Product.get(id)
		        
        productInstance.properties = params

		productInstance.save(flush: true)                             
    }
	

    def delete(Long id) {
        def productInstance = Product.get(id)
       
        try {
            productInstance.delete(flush: true)           
        }
        catch (DataIntegrityViolationException e) {
           
        }
    }
}
