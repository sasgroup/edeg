package ihm_demo

import java.util.Date;
import java.util.Map;

import grails.converters.JSON
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.dao.DataIntegrityViolationException

class HospitalProductMeasureController {
	
	def securityService
		
	def show() {
		
		if (params.h_id && Hospital.exists(params.h_id)) {
			def  hospital = Hospital.get(params.h_id)
			def  hp = HospitalProduct.findByHospitalAndProduct(hospital, Product.get(params.p_id))
			def  hm = HospitalMeasure.get(params.m_id)
			def hpm = HospitalProductMeasure.findByHospitalProductAndHospitalMeasure(hp, hm)

			if (null==hp){
				render(contentType: "text/json") {
					resp = "error"
					message = "Hospital Product with product ID = ${params.p_id} was not found"
				}
			}
			else if (null==hm){
				render(contentType: "text/json") {
					resp = "error"
					message = "Hospital Measure with ID = ${params.m_id} was not found"
				}
			}
			else if (null==hpm){
				render(contentType: "text/json") {
					resp = "error"
					message = "Hospital Product~Measure with product ID = ${params.p_id} and hospital measure ID = ${params.m_id} was not found"
				}
			}
			
			
			render(contentType: "text/json") {
				id   = hospital.id
				name = hospital.name
				ehr = hospital.ehr
				externalEHRs = hospital.externalEHRs?hospital.externalEHRs:""
				products = array{
					product id : hp.product.id,
					name : hp.product.name,
					code : hp.product.code,
					help : isNULL(hp.product.help,""),
					notes : isNULL(hp.product.notes,""),
					measures : array{
						measure id : hm.id,
						measureCategory :  hm.measure.measureCategory?.name,
						code : hm.measure.code,
						name : hm.measure.name,
						accepted : hm.accepted,
						completed : hm.completed,
						confirmed : hm.confirmed,
						included : hpm.included,
						verified : hm.verified,
						notes : isNULL(hm.measure.notes,""),
						help : isNULL(hm.measure.help,"")
					}
				}
			}
		}
		else {
			render(contentType: "text/json") {
				resp = "error"
				message = "Hospital with ID = ${params.h_id} was not found"
			}
		}
	}
	
	
	
	
	
	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
	
}