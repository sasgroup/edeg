package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class DataElementController {
	private DataElement saveInstance (DataElement instance, def param) {
		
		instance.name = param.name
		instance.code = param.code
		instance.notes = param.notes
		
		if (instance.id) {
			instance.measures.clear()
		}
		
		for (measure in param.measures) {
			instance.addToMeasures(Measure.get(measure.mid))
		}
	
		return instance.save(flush :true)
	}
    def save() {
		println "save"
		def dataElementtInstance  = saveInstance(new DataElement(), params)
		render(contentType: "text/json") {
					resp = "ok"
					message = "Product ${dataElementtInstance.name} successfully created"
		}
	}
   
	def show() {
		println "show"
		if (params.id && DataElement.exists(params.id)) {
			println "One"
			def  de = DataElement.get(params.id)
			def dataElementDefaultsList = DataElementDefaults.list().findAll{it.dataElements.id.findAll{it == de.id}.size() >= 1}
			println dataElementDefaultsList
			
			render(contentType: "text/json") {
						version = de.version
						id   = de.id
						code = de.code
						name = de.name
						notes = de.notes
						measures =  array {
							for (m in de?.measures) {
								measure  mname: m.name, mid: m.id
							}
						}
						dataElementDefaults = array {
							for (d in dataElementDefaultsList) {
								dataElementDefault  isIMO: d.isIMO,
													location : d.location,
													queryMnemonic : d.queryMnemonic,
													valueSet : d.valueSet,
													valueSetRequired : d.valueSetRequired,
													locationtype : d.locationtype
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
		println "Update"
		def dataElementInstance = DataElement.get(id)

		if  (!dataElementInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
			 println dataElementInstance.version > params.version
            if (dataElementInstance.version > params.version) {
				println 'inside'
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated product(${dataElementInstance.name}) while you were editing"
				}
			} 
		 }	
		
		dataElementInstance  = saveInstance(dataElementInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Product ${dataElementInstance.name} successfully updated"
		}                           
    }
	

    def delete(Long id) {
        println "Delete"

		def de = DataElement.findById(params.id)
		String name = de.name
		println ("product.measures:" + de.measures)

		def measuresDep = de.measures ? true : false

		if (measuresDep ) {
			render(status: 420, text: "DataElement ${name} cannot be deleted because of existing dependencie")
		} else {
			de?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "DataElement ${name} successfully deleted"
			}
		}
    }
}
