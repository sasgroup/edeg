package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class DataElementController {

	private DataElement saveInstance (DataElement instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.notes = param.notes
		instance.help = param.help

		Measure.list().each { it.removeFromDataElements(instance) }
		for (measure in param.measures) {
			instance.addToMeasures(Measure.get(measure.mid))
		}
		instance.save(flush :true)
		
		DataElementDefaults.executeUpdate("delete DataElementDefaults ded where ded.dataElement=?", [instance])

		//create new
		//TODO ids for ValuesType
		def dataElementsDefaults = param?.dataElementDefaults
		for (dataElementsDefault in dataElementsDefaults) {
			if (dataElementsDefault.location)
				new DataElementDefaults(location:dataElementsDefault.location, valueType:dataElementsDefault.valueType.name,dataElement : instance, ehr : Ehr.get(dataElementsDefault.linkId), ids : dataElementsDefault.ids).save(flush:true)
		}

		return instance
	}
	
	def save() {
		def dataElementInstance  = saveInstance(new DataElement(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Data Element ${dataElementInstance.code} has been successfully created"
		}
	}

	def show() {
		if (params.id && DataElement.exists(params.id)) {
			def  de = DataElement.get(params.id)
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.dataElement.id.findAll{it == de.id}.size() >= 1}
			render(contentType: "text/json") {
				version = de.version
				id   = de.id
				code = de.code
				name = de.name
				notes = de.notes
				help = de.help
				measures =  array {
					for (m in de?.measures) {
						measure  mname: m.name, mid: m.id
					}
				}
				dataElementDefaults = array {
					for (d in dataElementDefaultsList) {
						dataElementDefault	id : d.id,
						location : d.location,
						valueType : d.valueType,
						linkId : d.ehr.id,
						ids : d.ids
					}
				}
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
}
