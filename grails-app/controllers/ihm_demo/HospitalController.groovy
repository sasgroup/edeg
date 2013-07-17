package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class HospitalController {
	
	def update (Long id, Long version){
		def hospitalInstance = Hospital.get(id)
		
				if  (!hospitalInstance) {
					render(contentType: "text/json") {
						resp = "error"
						message = "Id exceptions"
					}
				}
		
				 if (params.version != null) {
					if (hospitalInstance.version > params.version) {
						return render(contentType: "text/json") {
							resp = "error"
							message = "Another User has updated hospital(${hospitalInstance.name}) while you were editing"
						}
					}
				 }
				
				hospitalInstance.name = params?.name
				hospitalInstance.notes = params?.notes
				hospitalInstance.ehr = Ehr.get(params?.ehr_id)
				hospitalInstance.save(flush:true)
				render(contentType: "text/json") {
					resp = "ok"
					message = "Hospital ${hospitalInstance.name} successfully updated"
				}
	}
	
	def save() {
		if (params.id && params.ehr_id) {// update Hospital set EHR
			def hospital = Hospital.get(params.id)
			println hospital
			hospital.ehr = Ehr.get(params.ehr_id)
			hospital.save(flush : true)
			for (prId in params.product_ids) {//update HospitalProduct
				def product = Product.get(prId)
				def hospitalproduct = HospitalProduct.findByHospitalAndProduct(hospital,product)

				if (!HospitalProduct.exists(hospitalproduct?.id)) //create HospitalProduct if new product added to this hospital   
					hospitalproduct = new HospitalProduct(hospital:hospital,product:product).save()
				
				for (measure in product?.measures) {
					def hospitalMeasure = HospitalMeasure.findAllByHospitalProductsAndMeasure(hospitalproduct,measure)
					if (!hospitalMeasure) {	//if null so need add to hospitalMeasure
						hospitalMeasure =new HospitalMeasure(accepted:false,
																 completed:false,
																 confirmed:false,
																 included:false,
																 verified:false,
																 measure : measure)
					    hospitalMeasure.addToHospitalProducts(hospitalproduct)
						hospitalMeasure.save()
					}
					
					for (de in measure?.dataElements) {//need find dataLement for this Measure and add to Hospital element
						def hasElement
						for (he in hospitalMeasure?.hospitalElement) {
							if (he.dataElement.id	== de.id)
								hasElement = true
						}
						if (!hasElement) {
							def defaultSetting = DataElementDefaults.findByDataElementAndEhr(de, hospital.ehr)
							if (defaultSetting) {//if not null so need add to HospitalElement
								println "p-->$product.name  m-->$measure.name  d-->$de.name"
								def hospitalElement = new HospitalElement(internalNotes : "",
																		  location : defaultSetting.location,
																		  notes : "",
																		  source : "",
																		  sourceEHR : true,
																		  valueSet : "",
																		  valueSetFile : "",
																		  valueType : defaultSetting.valueType,
																		  codeType : defaultSetting.codeType,
																		  dataElement : de
																		  )
								hospitalElement.addToHospitalMeasure(hospitalMeasure)
								hospitalElement.save()
							}
						}	
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