package ihm_demo

import ihm_demo.HospitalMeasure;

class HospitalProductController {

	def sendMailService

	private HospitalProduct saveInstance (HospitalProduct instance, def param) {
		def oldQA = instance.qa 
		instance.qa = param.qa
		// TODO: set the flags
		instance.notifyAdmin = param.notifyAdmin
		instance.notifyUser = param.notifyUser

		instance.save(flush :true)
		if (param.qa!="" && oldQA != param.qa)
			sendMailService.hospitalProductNotesUpdated(instance.hospital?.email, instance.hospital.name, instance.product.name, new Date(), session?.user.login)
		return instance
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
					qa = isNULL(hospitalProduct.qa,"")
					notifyAdmin = hospitalProduct?.notifyAdmin
					notifyUser  = hospitalProduct?.notifyUser
				}
			}
		}
		else if(Hospital.exists(params?.h_id)) {
			def hospital = Hospital.get(params?.h_id)
			def hospitalProducts = HospitalProduct.findAllByHospital(hospital)
			render(contentType: "text/json") {
				products = array {
					for (hp in hospitalProducts) {
						product 	id : hp.product.id,
									name : hp.product.name,
									code : hp.product.code
					}
				}
			}
		}
		else
			render(contentType: "text/json") {
				products = array {
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
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
}