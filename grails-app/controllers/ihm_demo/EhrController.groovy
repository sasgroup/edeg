package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class EhrController {
	
	private Ehr saveInstance (Ehr instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.notes = param.notes
		
		instance.save(flush :true)
	
		DataElementDefaults.executeUpdate("delete DataElementDefaults ded where ded.ehr=?", [instance])
		
		//create new
		def dataElementsDefaults = param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			if (dataElementsDefault.location)
			 new DataElementDefaults(location:dataElementsDefault.location, valueType:dataElementsDefault.valueType.name, dataElement : DataElement.get(dataElementsDefault.linkId), ehr : instance, ids : dataElementsDefault.ids).save(flush:true)
		}
		
		return instance
	}
	
	def save() {
		def ehrInstance = saveInstance (new Ehr(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "EHR ${ehrInstance.code} has been successfully created"
		}
	}
   
	def show() {
		if (params.id && Ehr.exists(params.id)) {
			def result = Ehr.get(params.id)
			def hospitalList = Hospital.list().findAll { it.ehr.id == result.id } 
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.ehr.id.findAll{it == result.id}.size() >= 1}
			//println dataElementDefaultsList
			dataElementDefaultsList = dataElementDefaultsList.sort{it.dataElement.name}
			//println dataElementDefaultsList
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
											valueType : d.valueType,
											linkId : d.dataElement.id,
											ids : d.ids
											
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
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${ehr.code}."
				}
			} 
		 }	
		
		ehr  = saveInstance(ehr, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "EHR ${ehr.code} has been successfully updated"
		}
	}
	

	def delete(Long id) {
		def ehr = Ehr.findById(id)
		
		def hasHospitals = Hospital.list().findAll { it.ehr.id == ehr.id } ? true : false
		def hasDataElementDefaultsList = DataElementDefaults.list().findAll{it.ehr.id.findAll{it == ehr.id}.size() >= 1} ? true : false
		
		String name = ehr.name
		String code = ehr.code
		String code2 = ehr.code
		
		if (hasHospitals || hasDataElementDefaultsList) {
			render(status: 420, text: "EHR ${code} cannot be deleted because of existing dependencies")
		} 
		else {
			ehr?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "EHR ${code2} has been successfully deleted"
			}
		}
	}
}