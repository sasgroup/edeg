package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class EhrController {
	private Ehr saveInstance (Ehr instance, def param) {
		instance.properties = param
		return instance.save(flush :true)
	}
	
    def save() {
		println "save"
		def ehr = saveInstance (new Ehr(), params)

		render(contentType: "text/json") {
			resp = "ok"
			message = "Product ${ehr.name} successfully created"
		}
	}
   
	def show() {
		println "show"
		if (params.id && Ehr.exists(params.id)) {
			def  result = Ehr.get(params.id)

			def hospitalList = Hospital.list().findAll { it.ehr.id == result.id } 
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.ehrs.id.findAll{it == result.id}.size() >= 1}
			
			render(contentType: "text/json") {
				version = result.version
				code =result.code
				name =result.name
				notes=result.notes
				id   = result.id	
				hospitals = array {
					for (h in hospitalList) {
						hospital  hname: h.name, hid: h.id
					}
				}
				dataElementDefaults = array {
					for (d in dataElementDefaultsList) {
						dataElementDefault  location : d.location,
											source : d.source,
											sourceEHR : d.sourceEHR,
											valueType : d.valueType,
											codeType : d.codeType
					}
				}
				
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
		println "Update"
		def ehr = Ehr.get(id)

		if  (!ehr) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
			 println ehr.version > params.version
            if (ehr.version > params.version) {
				println 'inside'
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated product(${ehr.name}) while you were editing"
				}
			} 
		 }	
		
		ehr  = saveInstance(ehr, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Product ${ehr.name} successfully updated"
		}
	}
	

	def delete(Long id) {
		println "Delete"

		def ehr = Ehr.findById(id)
		String name = ehr.name

		def hasHospitals = Hospital.list().findAll { it.ehr.id == ehr.id } ? true : false
		def hasDataElementDefaultsList = DataElementDefaults.list().findAll{it.ehrs.id.findAll{it == ehr.id}.size() >= 1} ? true : false
		
		if (hasHospitals || hasDataElementDefaultsList) {
			render(status: 420, text: "Ehr ${name} cannot be deleted because of existing dependencie")
		} else {
			ehr?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Ehr ${name} successfully deleted"
			}
		}
	}
}