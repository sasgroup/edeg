package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {

	def save() {
		println "save"
		def hospital = new Hospital(request.JSON)
		render( hospital.save() as JSON )
	}
   
	def show() {
		println "show"
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)
									
			render(contentType: "text/json") {			
				name =result.name
				notes=result.notes
				id   =result.id			
				
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
