package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class MeasureController {
	
	private Measure saveInstance (Measure instance, def param) {
		instance.name = param.name
		instance.code = param.code
		instance.help = param.help
		
		if (param.notes)
			instance.notes = param.notes
			
		if (param.measureCategory)
			instance.measureCategory = MeasureCategory.get (param.measureCategory.id)
			
		if (param.cqmDomain)
			instance.cqmDomain = CqmDomain.get (param.cqmDomain.id)
			
		Product.list().each { it.removeFromMeasures(instance) }	
		DataElement.list().each { it.removeFromMeasures(instance) }
		
		for (dataElement in param.dataElements) {
			instance.addToDataElements(DataElement.get(dataElement.did))
		}
		
		for (product in param.products) {
			instance.addToProducts(Product.get(product.pid))
		}
		
		return instance.save(flush :true)
	}
	
    def save() {
		def measureInstance  = saveInstance(new Measure(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Measure ${measureInstance?.code} has been successfully created"
		}
	}
   
	def show() {
		if (params.id && Measure.exists(params.id)) {
			def  result = Measure.get(params.id)
					
			render(contentType: "text/json") {	
				version = result.version
				code = result.code
				name = result.name
				notes= result.notes
				help = result.help
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
						measure code: p.code, name: p.name, notes: p.notes, id: p.id, category: p?.measureCategory?.name, cqm:p?.cqmDomain?.name
					}
				}
			}
		}
	}

    
    def update(Long id, Long version) {
		def measureInstance = Measure.get(id)

		if  (!measureInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
            if (measureInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${measureInstance.code}."					
				}
			} 
		 }	
	
		measureInstance  = saveInstance(measureInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "Measure ${measureInstance.code} has been successfully updated"
		}                          
    }
	

    def delete(Long id) {
		def measure = Measure.findById(params.id)
		
		def dataElementsDep = measure.dataElements ? true : false
		def productsDep = measure.products ? true : false
		def hospitalMeasureDep = HospitalMeasure.findByMeasure(measure) ? true : false
		
		String name = measure.name
		String code = measure.code
		String code2 = measure.code

		if (dataElementsDep || productsDep || hospitalMeasureDep) {
			render(status: 420, text: "Measure ${code} cannot be deleted because of existing dependencies")
		} else {
			measure?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "Measure ${code2} has been successfully deleted"
			}
		}
    }
}
