package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class DataElementController {

    def save() {
		println "save"
		def dataElement = new DataElement(request.JSON)
		render( dataElement.save() as JSON )
	}
   
	def show() {
		println "show"
		if (params.id && DataElement.exists(params.id)) {
			def  result = DataElement.get(params.id)
			//def dataElement_products = result?.products
						
			render(contentType: "text/json") {			
				code =result.code
				name =result.name
				notes=result.notes
				id   =result.id			
				
				/*products = array {
					for (p in dataElement_products) {
						product  pname: p.name, pid: p.id
					}
				}*/
			}
		}
		else {
			def results = DataElement.list()
			
			render(contentType: "text/json") {
				elements = array {
					for (p in results) {
						dataElement code: p.code, name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

    
    def update(Long id, Long version) {
		println "update"
		
        def dataElementInstance = DataElement.get(id)
		        
        dataElementInstance.properties = params

		dataElementInstance.save(flush: true)                             
    }
	

    def delete(Long id) {
        def dataElementInstance = DataElement.get(id)
       
        try {
            dataElementInstance.delete(flush: true)           
        }
        catch (DataIntegrityViolationException e) {
           
        }
    }
}
