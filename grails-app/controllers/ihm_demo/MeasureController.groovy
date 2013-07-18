package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class MeasureController {
	
	private Measure saveInstance (Measure instance, def param) {
		println "1"
		instance.name = param.name
		instance.code = param.code
		println "2"
		if (param.notes)
			instance.notes = param.notes
			
		if (param.measureCategory)
			instance.measureCategory = MeasureCategory.get (param.measureCategory.id)
			
		if (param.cqmDomain)
			instance.cqmDomain = CqmDomain.get (param.cqmDomain.id)
			println "3"
		Product.list().each {
			it.removeFromMeasures(instance)
		}	
		
		DataElement.list().each {
			it.removeFromMeasures(instance)
		}
		
		for (dataElement in param.dataElements) {
			instance.addToDataElements(DataElement.get(dataElement.did))
		}
		
		for (product in param.products) {
			instance.addToProducts(Product.get(product.pid))
		}
		println "4"
		return instance.save(flush :true)
	}
	
    def save() {
		println "!"
		def measureInstance  = saveInstance(new Measure(), params)
		println "!!"
		render(contentType: "text/json") {
					resp = "ok"
					message = "Measure ${measureInstance?.name} successfully created"
		}
		println "!!!"
	}
   
	def show() {
		if (params.id && Measure.exists(params.id)) {
			def  result = Measure.get(params.id)
					
			render(contentType: "text/json") {	
				version = result.version
				code = result.code
				name = result.name
				notes= result.notes
				id   = result.id
				measureCategory = result?.measureCategory
				cqmDomain = result?.cqmDomain
				products =  array {
					for (p in result?.products) {
						product  pname: p.name, pid: p.id, pcode: p.code
					}
				}
				dataElements =  array {
					for (d in result?.dataElements) {
						dataElement  dname: d.name, did: d.id, dcode: d.code
					}
				}
			}
		}
		else {
			def results = Measure.list()
			
			render(contentType: "text/json") {
				measures = array {
					for (p in results) {
						measure code: p.code, name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}

    
    def update(Long id, Long version) {
		def measrueInstance = Measure.get(id)

		if  (!measrueInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
            if (measrueInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated meausure(${measrueInstance.name}) while you were editing"
				}
			} 
		 }	
	
		measrueInstance  = saveInstance(measrueInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Measure ${measrueInstance.name} successfully updated"
		}                          
    }
	

    def delete(Long id) {
		def measure = Measure.findById(params.id)
		String name = measure.name

		def dataElementsDep = measure.dataElements ? true : false

		def productsDep = measure.products ? true : false
		
		def hospuitalmeasureDep = HospitalMeasure.findByMeasure(measure) ? true : false

		if (dataElementsDep || productsDep || hospuitalmeasureDep) {
			render(status: 420, text: "Measrue ${name} cannot be deleted because of existing dependencie")
		} else {
			measure?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Measure ${name} successfully deleted"
			}
		}
    }
}
