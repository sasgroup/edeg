package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException
import groovy.sql.Sql

class DataElementController {

	def dataSource
	
	private LogDeleteEvent(DataElement instance){
		def sql = new Sql(dataSource)
		def results = sql.rows("Insert into AUDIT_LOG (ID,ACTOR,CLASS_NAME,DATE_CREATED,EVENT_NAME,LAST_UPDATED,NEW_VALUE,OLD_VALUE,PERSISTED_OBJECT_ID,PERSISTED_OBJECT_VERSION,PROPERTY_NAME,URI)"
								+
								"values (HIBERNATE_SEQUENCE.nextVal,'"+request.getRemoteUser()+"','"+instance.getClass().getSimpleName()+"',SYSTIMESTAMP,'DELETE',SYSTIMESTAMP,null,'"+instance.toString()+"','"+instance.id+"',null,'dataElementDefaults',null)")
	}

	//Save DataElement
	private DataElement saveInstance (DataElement instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.notes = isNULL(param?.notes,"")
		instance.help = isNULL(param?.help,"")
		
		//Remove DataElement from all Measures and add only to Measures from request 
		Measure.list().each { it.removeFromDataElements(instance) }
		for (measure in param.measures) {
			instance.addToMeasures(Measure.get(measure.mid))
		}
		instance.save(flush :true)
		
		//LogDeleteEvent(instance)
		DataElementDefaults.executeUpdate("delete DataElementDefaults ded where ded.dataElement=?", [instance])
		

		//create new DataElementDefaults
		//TODO ids for ValuesType
		def dataElementsDefaults = param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			if (dataElementsDefault.location){
				DataElementDefaults ded = new DataElementDefaults(
												location:dataElementsDefault.location, 
												dataElement : instance, 
												ehr : Ehr.get(dataElementsDefault.linkId), 
												ids : defaultIds(dataElementsDefault.ids))
				ded.save(flush:true)
			}
		}

		return instance
	}
	
	private String defaultIds(String ids){
		if (ids && ids.length()>0)
			return ids
		else{
			def _vt = ValuesType.findByName("NotApplicable")
			def _ids = _vt.id.toString()
			return _ids
		}
	}
	
	def save() {
		def dataElementInstance  = saveInstance(new DataElement(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Data Element ${dataElementInstance.code} has been successfully created"
		}
	}

	def show() {
		if (params.id && DataElement.exists(params.id)) {//display only 1 DataElement by ID
			def  de = DataElement.get(params.id)
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.dataElement.id.findAll{it == de.id}.size() >= 1}
			render(contentType: "text/json") {
				version = de.version
				id   = de.id
				code = de.code
				name = de.name
				notes = isNULL(de.notes,"")
				help = isNULL(de.help,"")
				measures =  array {
					for (m in de?.measures) {
						measure  mname: m.name, mid: m.id
					}
				}
				dataElementDefaults = array {
					for (d in dataElementDefaultsList) {
						dataElementDefault	id : d.id,
						location : isNULL(d.location,""),
						linkId : d.ehr.id,
						ids : isNULL(d.ids,"")
					}
				}
			}
		}
		else {//display all DataElement
			def results = DataElement.list()

			render(contentType: "text/json") {
				elements = array {
					for (p in results) {
						dataElement 	code: p.code, 
										name: p.name, 
										notes: isNULL(p.notes,""), 
										id: p.id
					}
				}
			}
		}
	}


	def update(Long id, Long version) {
		def dataElementInstance = DataElement.get(id)

		if  (!dataElementInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		if (params.version != null) {
			if (dataElementInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${dataElementInstance.code}."					
				}
			}
		}

		dataElementInstance  = saveInstance(dataElementInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Data Element ${dataElementInstance.code} has been successfully updated"
		}
	}


	def delete(Long id) {
		def de = DataElement.findById(params.id)
		//delete all depend links
		def measuresDep = de.measures ? true : false
		def hasDataElementDefaultsList = DataElementDefaults.list().findAll{it.dataElement.id.findAll{it == de.id}.size() >= 1} ? true : false
		def hospitalElementDep = HospitalElement.findByDataElement(de) ? true : false
		
		String code = de.code
		String code2 = de.code
		String name = de.name
		
		if (measuresDep || hasDataElementDefaultsList || hospitalElementDep) {
			render(status: 420, text: "Data Element ${code} cannot be deleted because of existing dependencies")
		} 
		else {
			de?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Data Element ${code2} has been successfully deleted"
			}
		}
	}
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}

}
