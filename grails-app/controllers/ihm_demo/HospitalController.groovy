package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {
	//apply need JSON: Hospital(id)  with Prodcuts(checked id) and Ehr (selected id)
	
	
	def save() {
		if (params.id && params.ehr_id) {// update Hospital set EHR
			def hospital = Hospital.get(params.id)
			println hospital
			hospital.ehr = Ehr.get(params.ehr_id)
			hospital.save()
			for (prId in params.product_ids) {//update HospitalProduct
				def product = Product.get(prId)
				def hospitalproduct = HospitalProduct.findByHospitalAndProduct(hospital,product)

				if (!HospitalProduct.exists(hospitalproduct?.id)) //create HospitalProduct if new product added to this hospital   
					hospitalproduct = new HospitalProduct(hospital:hospital,product:product).save()
			
				println "-------------$hospitalproduct--------"
				
				for (measure in product?.measures) {
					def hospitalMeasureList = HospitalMeasure.findAllByHospitalProductsAndMeasure(hospitalproduct,measure)
					if (!hospitalMeasureList) {	//if null so need add to hospitalMeasure
						def hospitalMeasure =new HospitalMeasure(accepted:false,
																 completed:false,
																 confirmed:false,
																 included:false,
																 verified:false,
																 measure : measure)
						    hospitalMeasure.addToHospitalProducts(hospitalproduct)
							hospitalMeasure.save()
					}
				}
			}
			render(contentType: "text/json") {
				resp = "ok"
				message = "Hospital ${hospital.name} "
			}
		} else {
			render(status: 420, text: "SomeError")
		}
		
	}
   
	def show() {
		if (params.id && Hospital.exists(params.id)) {
			def  result = Hospital.get(params.id)
			println params.id

			def hospitalProdcuts = HospitalProduct.findAllByHospital(result)
			def productList	=  hospitalProdcuts.collect{it.product}


			render(contentType: "text/json") {
				name = result.name
				notes= result.notes
				id   = result.id
				ehr = result.ehr
				products = array {
					for (p in productList) {
						product id : p.id,
								name : p.name,
								code : p.code,
								measures : array {
									for (h in HospitalMeasure.list().findAll{it?.hospitalProducts.findAll{it.product == p}.size() >= 1}){
										measrue id : h.id,
												code : h.measure.code,
												name : h.measure.name,
												accepted : h.accepted,
												completed : h.completed,
												confirmed : h.confirmed,
												included : h.included,
												verified : h.verified
									}
								}
					}
				}

			}
		}
		else {
			def results = Hospital.list()

			render(contentType: "text/json") {
				hospitals = array {
					for (p in results) {
						hospital name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}
	
}