package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class EhrController {
	private Ehr saveInstance (Ehr instance, def param) {
		println param?.dataElementDefaults
		instance.properties = param
		instance.save(flush :true)
	
		//clear
		def dataElementsDefaults = DataElementDefaults.findAllByEhr(instance)//param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			DataElementDefaults.get(dataElementsDefault.id).delete(flush: true)
		}
		//create new
		dataElementsDefaults = param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			 new DataElementDefaults(location:dataElementsDefault.location,
				//source:"",
				//sourceEHR:"",
				//valueSetRequired:false,
				valueType:dataElementsDefault.valueType.name,
				//codeType:dataElementsDefault.codeType.name,
				dataElement : DataElement.get(dataElementsDefault.linkId),
				ehr : instance).save(flush:true)
		}
		return instance
	}
	def save() {
		def ehr = saveInstance (new Ehr(), params)

		render(contentType: "text/json") {
			resp = "ok"
			message = "Ehr ${ehr.code} has been successfully created"
		}
	}
   
	def show() {
		if (params.id && Ehr.exists(params.id)) {
			def  result = Ehr.get(params.id)

			def hospitalList = Hospital.list().findAll { it.ehr.id == result.id } 
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.ehr.id.findAll{it == result.id}.size() >= 1}
			
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
						dataElementDefault  id : d.id,
											location : d.location,
											//source : d.source,
											//sourceEHR : d.sourceEHR,
											valueType : d.valueType,
											//codeType : d.codeType,
											linkId : d.dataElement.id
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
		def ehr = Ehr.get(id)

		if  (!ehr) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
            if (ehr.version > params.version) {
				println 'inside'
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${ehr.code}."
				}
			} 
		 }	
		
		ehr  = saveInstance(ehr, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Ehr ${ehr.code} has been successfully updated"
		}
	}
	

	def delete(Long id) {
		println "Delete"

		def ehr = Ehr.findById(id)
		String name = ehr.name
		String code = ehr.code

		def hasHospitals = Hospital.list().findAll { it.ehr.id == ehr.id } ? true : false
		def hasDataElementDefaultsList = DataElementDefaults.list().findAll{it.ehr.id.findAll{it == ehr.id}.size() >= 1} ? true : false
		
		if (hasHospitals || hasDataElementDefaultsList) {
			render(status: 420, text: "Ehr ${code} cannot be deleted because of existing dependencies")
		} else {
			ehr?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Ehr ${code} has been successfully deleted"
			}
		}
	}
}