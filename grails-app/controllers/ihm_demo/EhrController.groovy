package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class EhrController {

    def save() {
		println "save"
		def ehr = new Ehr(request.JSON)
		render( ehr.save() as JSON )
	}
   
	def show() {
		println "show"
		if (params.id && Ehr.exists(params.id)) {
			def  result = Ehr.get(params.id)
									
			render(contentType: "text/json") {			
				code =result.code
				name =result.name
				notes=result.notes
				id   =result.id			
				
			}
		}
		else {
			def results = Ehr.list()
			
			render(contentType: "text/json") {
				ehrs = array {
					for (p in results) {
						ehr code: p.code, name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

	
	def update(Long id, Long version) {
		println "update"
		
		def ehrInstance = Ehr.get(id)
				
		ehrInstance.properties = params

		ehrInstance.save(flush: true)
	}
	

	def delete(Long id) {
		def ehrInstance = Ehr.get(id)
	   
		try {
			ehrInstance.delete(flush: true)
		}
		catch (DataIntegrityViolationException e) {
		   
		}
	}
}
