package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class MeasureController {

    def save() {
		println "save"
		def measure = new Measure(request.JSON)
		render( measure.save() as JSON )
	}
   
	def show() {
		println "show"
		if (params.id && Measure.exists(params.id)) {
			def  result = Measure.get(params.id)
			def measure_products = result?.products
						
			render(contentType: "text/json") {			
				code =result.code
				name =result.name
				notes=result.notes
				id   =result.id			
				
				products = array {
					for (p in measure_products) {
						product  pname: p.name, pid: p.id
					}
				}
			}
		}
		else {
			def results = Measure.list()
			
			render(contentType: "text/json") {
				measures = array {
					for (p in results) {
						measure code: p.code, name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

    
    def update(Long id, Long version) {
		println "update"
		
        def measureInstance = Measure.get(id)
		        
        measureInstance.properties = params

		measureInstance.save(flush: true)                             
    }
	

    def delete(Long id) {
        def measureInstance = Measure.get(id)
       
        try {
            measureInstance.delete(flush: true)           
        }
        catch (DataIntegrityViolationException e) {
           
        }
    }
}
