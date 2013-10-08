package ihm_demo

import ihm_demo.HospitalMeasure;

class HospitalProductController {

	private HospitalProduct saveInstance (HospitalProduct instance, def param) {
		instance.qa = param.qa
		return instance.save(flush :true)
	}

	def save() {
		def hospitalProductInstance  = saveInstance(new HospitalProduct(), params)
		render(contentType: "text/json") {
			resp = "ok"
			version = hospitalProductInstance.version
			message = "HospitalMeasure ${hospitalMeasureInstance.measure.name} successfully created"
		}
	}

	def show() {
	  if(Hospital.exists(params?.h_id) && Product.exists(params?.p_id)) {
	  
	   def  hospitalInstance = Hospital.get(params?.h_id)
	   def  product = Product.get(params?.p_id)
	   
	   if (HospitalProduct.findByHospitalAndProduct(hospitalInstance, product)) {
	    HospitalProduct hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospitalInstance, product)
	 
	    render(contentType: "text/json") {
	     id = hospitalProduct.id
	     hospital = hospitalProduct.hospital.name
	     product = hospitalProduct.product.name
	     qa = hospitalProduct.qa
	    }
	   }
	  }  
	 }

	def update(Long id, Long version) {

		def hospitalProductInstance = HospitalProduct.get(id)

		if  (!hospitalProductInstance) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		if (params.version != null) {
			if (hospitalProductInstance.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another User has updated hospitalProductInstance while you were editing"
				}
			}
		}

		hospitalProductInstance  = saveInstance(hospitalProductInstance, params)
		render(contentType: "text/json") {
			resp = "ok"
			version = hospitalProductInstance.version
			message = "The Hospital Measure is updated successfully"
		}
	}

	def delete(Long id) {
		println "Delete"
	}
}
