package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ReportController {
		
	def show() {
		
		if (params.id){
			
			if (params.id == "1"){ // gap report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				render(contentType: "text/json") {
					report = "Measure Gap Report"
					rid = 1
					data = array {
						for (hp in hProducts){
							for (hpm in hp.hospitalProductMeasures){
								if (hpm.included && !hpm.hospitalMeasure.completed){
									measure pid : hp.product.id,
									pcode 		: hp.product.code,
									pname 		: hp.product.name,
									mid 		: hpm.hospitalMeasure.id,
									mcode 		: hpm.hospitalMeasure.measure.code,
									mname 		: hpm.hospitalMeasure.measure.name,
									included 	: hpm.included,
									accepted 	: hpm.hospitalMeasure.accepted,
									completed 	: hpm.hospitalMeasure.completed,
									confirmed 	: hpm.hospitalMeasure.confirmed,
									verified 	: hpm.hospitalMeasure.verified,
									hid			: hpm.hospitalMeasure.hospital.id,
									hname		: hpm.hospitalMeasure.hospital.name
								}
							}
						}
					}
				}
			}
			else if (params.id == "2"){ // completeness report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				render(contentType: "text/json") {
					report = "Measure Completeness Report"
					rid = 2
					data = array {
						for (hp in hProducts){
							for (hpm in hp.hospitalProductMeasures){
								if (hpm.included && hpm.hospitalMeasure.completed){
									measure pid : hp.product.id,
									pcode 		: hp.product.code,
									pname 		: hp.product.name,
									mid 		: hpm.hospitalMeasure.id,
									mcode 		: hpm.hospitalMeasure.measure.code,
									mname 		: hpm.hospitalMeasure.measure.name,
									included 	: hpm.included,
									accepted 	: hpm.hospitalMeasure.accepted,
									completed 	: hpm.hospitalMeasure.completed,
									confirmed 	: hpm.hospitalMeasure.confirmed,
									verified 	: hpm.hospitalMeasure.verified,
									hid			: hpm.hospitalMeasure.hospital.id,
									hname		: hpm.hospitalMeasure.hospital.name
								}
							}
						}
					}
				}
			}
			else if (params.id == "3"){ // products report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				render(contentType: "text/json") {
					report = "Hospital ~ Product Report"
					rid=3
					data = array {
						for (hp in hProducts){
							product pid : hp.product.id,
							pcode 		: hp.product.code,
							pname 		: hp.product.name,
							hid			: hp.hospital.id,
							hname		: hp.hospital.name
						}
					}
				}
			}
			else if (params.id == "4"){ // measures report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				render(contentType: "text/json") {
					report = "Hospital ~ Measures Report"
					rid = 4
					data = array {
						for (hp in hProducts){
							for (hpm in hp.hospitalProductMeasures){
								if (hpm.included){
									measure pid : hp.product.id,
									pcode 		: hp.product.code,
									pname 		: hp.product.name,
									mid 		: hpm.hospitalMeasure.id,
									mcode 		: hpm.hospitalMeasure.measure.code,
									mname 		: hpm.hospitalMeasure.measure.name,
									included 	: hpm.included,
									accepted 	: hpm.hospitalMeasure.accepted,
									completed 	: hpm.hospitalMeasure.completed,
									confirmed 	: hpm.hospitalMeasure.confirmed,
									verified 	: hpm.hospitalMeasure.verified,
									hid			: hpm.hospitalMeasure.hospital.id,
									hname		: hpm.hospitalMeasure.hospital.name
								}
							}
						}
					}
				}
			}
			else if (params.id == "5"){ // data element report
				def hElements = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hElements = HospitalElement.findAllByHospital(hospital)
				}
				else{
					hElements = HospitalElement.list()
				}
				
				render(contentType: "text/json") {
					report = "Hospital ~ Data Elements Report"
					rid = 5
					data = array {
						for (he in hElements){
							element	hid		: he.hospital.id,
							hname			: he.hospital.name, 
							eid 			: he.dataElement.id,
							ecode 			: he.dataElement.code,
							ename 			: he.dataElement.name,
							enotes			: he.dataElement.notes,
							internalNotes 	: he.internalNotes,
							location 		: he.location,
							notes 			: he.notes,
							source 			: he.source,
							sourceEHR 		: he.sourceEHR,
							valueSet 		: he.valueSet,
							valueSetFile 	: he.valueSetFile,
							valueType 		: he.valueType.toString()
							//codeType 		: he.codeType.toString()//,
							//help			: he.dataElement.help//,
/*
							hospitalValueSet : array {
								for (hvs in HospitalValueSet.findAllByHospitalElement(hme.hospitalElement)){
									hvset code : hvs.code,
									mnemonic : hvs.mnemonic,
									codeType : hvs.codeType
								}
							},
						
							elementExtraLocation : array {
								for (e in ElementExtraLocation.findAllByHospitalElement(hme.hospitalElement)){
									elem location  : e.location,
										 source    : e.source
										 sourceEHR : e.sourceEHR 
										 codeType  : e.codeType
										 valueType : e.valueType								
								}
							}
*/							
						}
					}
				}
			}
			else{
				render(contentType: "text/json") {
					rtype = "No Report"
					data = null
				}
			}
		}
	}
}