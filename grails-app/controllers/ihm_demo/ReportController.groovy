package ihm_demo

import grails.converters.JSON

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import org.springframework.dao.DataIntegrityViolationException
import groovy.sql.Sql

class ReportController {
	def dataSource

	
	private String prepSQL(GrailsParameterMap params){
		
		def res = "SELECT * from Audit_Log"
		def _where = " Where 1=1"
		
		if (params.etype != "-"){
			def etype = "ihm_demo."
			switch(params.etype){
				case "P": etype += "Product"; break;
				case "M": etype += "Measure"; break;
				case "E": etype += "DataElement"; break;
				case "H": etype += "Hospital"; break;
				case "HP": etype += "HospitalProduct"; break;
				case "HM": etype += "HospitalMeasure"; break;
				case "HE": etype += "HospitalElement"; break;
			}
			_where += " and CLASS_NAME = '$etype'"
		
			
			def oid = params.entity
			if (params.entity != "-")
				_where += " and PERSISTED_OBJECT_ID = $oid"
			
		}
		
		def dpFrom = (new Date()).format("dd/MM/yy")
		def dpTill = (new Date()).format("dd/MM/yy")
		
		if (params.dpFrom)
			dpFrom = params.dpFrom
		
		if (params.dpTill)
			dpTill = params.dpTill
			
		//_where += " and LAST_UPDATED between '$dpFrom' and '$dpTill'"
		
		return res + _where
	}
				
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
							if (params.product == "0" || params.product == hp.product.id.toString())
								for (hpm in hp.hospitalProductMeasures){
									if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString())
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
							if (params.product == "0" || params.product == hp.product.id.toString())
								for (hpm in hp.hospitalProductMeasures){
									if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString())
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
						for (hp in hProducts)
							if (params.product == "0" || params.product == hp.product.id.toString()){
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
							if (params.product == "0" || params.product == hp.product.id.toString())
								for (hpm in hp.hospitalProductMeasures){
									if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString())
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
						for (he in hElements)
							if (params.element == "0" || params.element == he.dataElement.id.toString()){
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
							}
					}
				}
			}
			
			
			
			
			else if (params.id == "6"){ // data element report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				
				render(contentType: "text/json") {
					report = "Hospital ~ Filtered Data Elements"
					rid = 6
					data = array {
						for (hp in hProducts){
							if (params.product == "0" || params.product == hp.product.id.toString()){
								for (hpm in hp.hospitalProductMeasures){
									if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString()){
										if (hpm.included){
											for (hme in hpm.hospitalMeasure.hospitalMeasureElements){
												if (params.element == "0" || params.element == hme.hospitalElement.dataElement.id.toString()){
													element pid : hp.product.id,
													pcode 		: hp.product.code,
													pname 		: hp.product.name,
													mid 		: hpm.hospitalMeasure.id,
													mcode 		: hpm.hospitalMeasure.measure.code,
													mname 		: hpm.hospitalMeasure.measure.name,
													eid			: hme.hospitalElement.id,
													location 	: hme.hospitalElement.location,
													source 		: hme.hospitalElement.source,
													valueType	: hme.hospitalElement.valueType.toString(),
													ecode		: hme.hospitalElement.dataElement.code,
													ename		: hme.hospitalElement.dataElement.name,
													hname		: hme.hospitalElement.hospital.name
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			
			
			
			else if (params.id == "7"){ // data element report
				def sql = new Sql(dataSource)
				def results = sql.rows(prepSQL(params))
				
				
				render(contentType: "text/json") {
					report = "Audit Log"
					rid = 7
					data = array {
						for (r in results){
							row id			: r.get('ID'), 
								updated		: ((Date)r.get('LAST_UPDATED')).format('dd/MM/yy'),
								actor		: r.get('ACTOR'),
								event		: r.get('EVENT_NAME'),
								entity		: ((String)r.get('CLASS_NAME')).substring(9),
								property	: r.get('PROPERTY_NAME'),
								nvalue		: r.get('NEW_VALUE'),
								ovalue		: r.get('OLD_VALUE'),
								
								created		: ((Date)r.get('DATE_CREATED')).format('dd/MM/yy'),
								poid		: r.get('PERSISTED_OBJECT_ID'),
								povers		: r.get('PERSISTED_OBJECT_VERSION'),
								uri			: r.get('URI')
								
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