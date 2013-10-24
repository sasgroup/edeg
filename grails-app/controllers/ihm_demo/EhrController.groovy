package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import groovy.sql.Sql

class EhrController {
	
	def dataSource
	
	private LogDeleteEvent(DataElement instance){
		def sql = new Sql(dataSource)
		def results = sql.rows("Insert into AUDIT_LOG (ID,ACTOR,CLASS_NAME,DATE_CREATED,EVENT_NAME,LAST_UPDATED,NEW_VALUE,OLD_VALUE,PERSISTED_OBJECT_ID,PERSISTED_OBJECT_VERSION,PROPERTY_NAME,URI)"
								+
								"values (HIBERNATE_SEQUENCE.nextVal,'"+request.getRemoteUser()+"','"+instance.getClass().getSimpleName()+"',SYSTIMESTAMP,'DELETE',SYSTIMESTAMP,null,'"+instance.toString()+"','"+instance.id+"',null,'dataElementDefaults',null)")
	}
	
	private Ehr saveInstance (Ehr instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.notes = isNULL(param.notes,"")
		
		instance.save(flush :true)
	
		//LogDeleteEvent(instance)
		DataElementDefaults.executeUpdate("delete DataElementDefaults ded where ded.ehr=?", [instance])
		
		//create new
		def dataElementsDefaults = param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			if (dataElementsDefault.location){
			 DataElementDefaults ded = new DataElementDefaults(	
				 						location:dataElementsDefault.location, 
				 						//valueType:dataElementsDefault.valueType.name, 
										dataElement : DataElement.get(dataElementsDefault.linkId), 
										ehr : instance, 
										ids : dataElementsDefault.ids)
			 ded.save(flush:true)
			}
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
				notes=isNULL(result.notes,"")
				id   = result.id	
				hospitals = array {
					for (h in hospitalList) {
						hospital  hname: h.name, hid: h.id
					}
				}
				dataElementDefaults = array {
					for (d in dataElementDefaultsList) {
						dataElementDefault  id : d.id,
											location : isNULL(d.location,""),
											//valueType : d.valueType,
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
						ehr 	code: p.code, 
								name: p.name, 
								notes: isNULL(p.notes,""), 
								id: p.id
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
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
}