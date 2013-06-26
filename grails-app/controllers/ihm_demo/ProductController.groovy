package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ProductController {

	def save() {
		println "save"
		def productInstance = new Product()

		productInstance.name = params.name
		productInstance.code = params.code
		productInstance.notes = params.notes

		for (hospital in params.hospitals) {
			productInstance.addToHospitals(Hospital.get(hospital.hid))
		}

		for (measure in params.measures) {
			productInstance.addToMeasures(Measure.get(measure.mid))
		}

		productInstance.save(flush :true)
		render(contentType: "text/json") {
					resp = "ok"
					message = "updated"
		}
	}

	def show() {
		def results = Product.list()

		render(contentType: "text/json") {
			products = array {
				for (p in results) {
					product version: p.version,
							code: p.code, 
							name: p.name, 
							notes: p.notes, id: 
							p.id,
							measures :  array {
								for (m in p?.measures) {
									measure  mname: m.name, mid: m.id
								}
							},
							hospitals : array {
								for (h in p?.hospitals) {
									hospital  hname: h.name, hid: h.id
								}
							}
				}
			}
		}
	}

	def update(Long id, Long version) {
		def productInstance = Product.get(id)

		if  (!productInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}
		println "~"+productInstance.version
		println "-"+params.version
		 if (params.version != null) {
			 println productInstance.version > params.version
            if (productInstance.version > params.version) {
				println 'inside'
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user has updated this Users while you were editing"
				}
			} 
		 }	
		
		productInstance.name = params.name
		productInstance.code = params.code
		productInstance.notes = params.notes

		productInstance.hospitals.clear()
		for (hospital in params.hospitals) {
			productInstance.addToHospitals(Hospital.get(hospital.hid))
		}
		
		
		productInstance.measures.clear()

		for (measure in params.measures) {
			productInstance.addToMeasures(Measure.get(measure.mid))
		}

		productInstance.save(flush :true)
		render(contentType: "text/json") {
					resp = "ok"
					message = "updated"
		}
	}
	


	def delete(Long id) {
		/*  def productInstance = Product.get(id)
		 try {
		 productInstance.delete(flush: true)           
		 }
		 catch (DataIntegrityViolationException e) {
		 }*/

		def product = Product.findById(params.id)

		println ("product.measures:" + product.measures)

		def measuresDep = product.measures ? true : false

		def hospitalsDep = product.hospitals ? true : false

		if (measuresDep || hospitalsDep) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Entity cannot be deleted because of existing dependencies"
				//This item cannot be deleted because a dependency (on smth.)  is present.
			}
		} else {
			product?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "successfully deleted"
			}
		}
	}
}
