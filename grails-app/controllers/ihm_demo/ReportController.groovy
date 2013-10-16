package ihm_demo

import grails.converters.JSON

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import org.springframework.dao.DataIntegrityViolationException
import groovy.sql.Sql

class ReportController {
	def dataSource

	
	private String prepSQL(GrailsParameterMap params){
		
		def res = "SELECT id, TO_CHAR(last_updated, 'mm/dd/yy HH24:MI') last_updated, actor, event_name, class_name, property_name, new_value, old_value, TO_CHAR(date_created, 'mm/dd/yy HH24:MI') date_created, persisted_object_id FROM Audit_Log "
		def _where = " Where 1=1"
		
		if (params.etype != "-"){
			def assembly = "ihm_demo"
			def oid = params.entity
			def hid = params.hospital
			def etype = params.etype
			
			switch(etype){
				case "P": 
					_where += " and CLASS_NAME = '${assembly}.Product'"
					if (oid != "0")
						_where += " and PERSISTED_OBJECT_ID = $oid"
					break;
					
				case "M": 
					_where += " and CLASS_NAME = '${assembly}.Measure'"
					if (oid != "0")
						_where += " and PERSISTED_OBJECT_ID = $oid"
					break;
				
				case "E": 
					_where += " and CLASS_NAME = '${assembly}.DataElement'"
					if (oid != "0")
						_where += " and PERSISTED_OBJECT_ID = $oid"
					break;
				
				case "H": 
					_where += " and CLASS_NAME = '${assembly}.Hospital'"
					if (hid != "0")
						_where += " and PERSISTED_OBJECT_ID = $hid"
					break;

										
				case "HP": 
					_where += " and CLASS_NAME = '${assembly}.HospitalProduct'"
					_where += derivePersistedObjectIDs(hid,oid,etype)
					break;
				case "HM": 
					_where += " and CLASS_NAME = '${assembly}.HospitalMeasure'"
					_where += derivePersistedObjectIDs(hid,oid,etype)
					break;
				case "HE": 
					_where += " and CLASS_NAME = '${assembly}.HospitalElement'"
					_where += derivePersistedObjectIDs(hid,oid,etype)
					break;
				case "XL":
					_where += " and CLASS_NAME = '${assembly}.ElementExtraLocation'"
					_where += deriveNestedObjectIDs(hid,oid,etype)
					break;
				case "VS":
					_where += " and CLASS_NAME = '${assembly}.HospitalValueSet'"
					_where += deriveNestedObjectIDs(hid,oid,etype)
					break;
			}

		}
		
		def dpFrom = (new Date()).format("yyyy-MM-dd") + " 00:00:00"
		def dpTill = (new Date()).format("yyyy-MM-dd") + " 23:59:59"
		
		if (params.dpFrom)
			dpFrom = (Date.parse("MM/dd/yy", params.dpFrom)).format("yyyy-MM-dd") + " 00:00:00"
		
		if (params.dpTill)
			dpTill = (Date.parse("MM/dd/yy", params.dpTill)).format("yyyy-MM-dd") + " 23:59:59"
			
		_where += " and  LAST_UPDATED between to_timestamp('$dpFrom', 'yyyy-mm-dd hh24:mi:ss') and to_timestamp('$dpTill', 'yyyy-mm-dd hh24:mi:ss') "
		
		return res + _where + " order by persisted_object_id asc, last_updated asc"
	}
	
	private String derivePersistedObjectIDs(hid,oid,etype){
		def hospital = Hospital.get(hid)
		def product  = Product.get(oid)
		def measure  = Measure.get(oid)
		def element  = DataElement.get(oid)
		List<Long> _ids = new ArrayList<Long>()
		_ids.add(0)
		def res = ""
		def poids = ""
		
		switch (etype){
			case "HP": 
				if (null != hospital && null != product){
					def hospitalProduct = HospitalProduct.findByHospitalAndProduct(hospital, product)
					if (hospitalProduct)
						_ids.add(hospitalProduct.id)
				}
				else if (null != hospital){
					def hospitalProducts = HospitalProduct.findAllByHospital(hospital)
					for (hp in hospitalProducts)
						_ids.add(hp.id)
				}
				else if (null != product){
					def hospitalProducts = HospitalProduct.findAllByProduct(product)
					for (hp in hospitalProducts)
						_ids.add(hp.id)
				}
				else{
					_ids.remove(0)
				}
				break;
			case "HM": 
				if (null != hospital && null != measure){
					def hospitalMeasure = HospitalMeasure.findByHospitalAndMeasure(hospital, measure)
					if (hospitalMeasure)
						_ids.add(hospitalMeasure.id)
				}
				else if (null != hospital){
					def hospitalMeasures = HospitalMeasure.findAllByHospital(hospital)
					for (hm in hospitalMeasures)
						_ids.add(hm.id)
				}
				else if (null != measure){
					def hospitalMeasures = HospitalMeasure.findAllByMeasure(measure)
					for (hm in hospitalMeasures)
						_ids.add(hm.id)
				}
				else{
					_ids.remove(0)
				}
				break;
			case "HE": 
				if (null != hospital && null != element){
					def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, element)
					if (hospitalElement)
						_ids.add(hospitalElement.id)
				}
				else if (null != hospital){
					def hospitalElements = HospitalElement.findAllByHospital(hospital)
					for (he in hospitalElements)
						_ids.add(he.id)
				}
				else if (null != element){
					def hospitalElements = HospitalElement.findAllByDataElement(element)
					for (he in hospitalElements)
						_ids.add(he.id)
				}
				else{
					_ids.remove(0)
				}
				break;
		}
		
		if (_ids.size()>0){
			poids = _ids.join(",")
			res = " and PERSISTED_OBJECT_ID in (${poids})"
		}
		
		return res
	}
	
	private String deriveNestedObjectIDs(hid,oid,etype){
		def hospital = Hospital.get(hid)
		def product  = Product.get(oid)
		def measure  = Measure.get(oid)
		def element  = DataElement.get(oid)
		List<Long> _ids = new ArrayList<Long>()
		_ids.add(0)
		def res = ""
		def poids = ""
		
		switch (etype){
			case "XL":
				if (null != hospital && null != element){
					def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, element)
					if (hospitalElement){
						def extraLocations = ElementExtraLocation.findAllByHospitalElement(hospitalElement)
						for (xl in extraLocations)
							_ids.add(xl.id)
					}
				}
				else if (null != hospital){
					def hospitalElements = HospitalElement.findAllByHospital(hospital)
					for (he in hospitalElements){
						def extraLocations = ElementExtraLocation.findAllByHospitalElement(he)
						for (xl in extraLocations)
							_ids.add(xl.id)
					}
				}
				else if (null != element){
					def hospitalElements = HospitalElement.findAllByDataElement(element)
					for (he in hospitalElements){
						def extraLocations = ElementExtraLocation.findAllByHospitalElement(he)
						for (xl in extraLocations)
							_ids.add(xl.id)
					}
				}
				else{
					_ids.remove(0)
				}
				break;
			case "VS":
				if (null != hospital && null != element){
					def hospitalElement = HospitalElement.findByHospitalAndDataElement(hospital, element)
					if (hospitalElement){
						def valueSets = HospitalValueSet.findAllByHospitalElement(hospitalElement)
						for (vs in valueSets)
							_ids.add(vs.id)
					}
				}
				else if (null != hospital){
					def hospitalElements = HospitalElement.findAllByHospital(hospital)
					for (he in hospitalElements){
						def valueSets = HospitalValueSet.findAllByHospitalElement(he)
						for (vs in valueSets)
							_ids.add(vs.id)
					}
				}
				else if (null != element){
					def hospitalElements = HospitalElement.findAllByDataElement(element)
					for (he in hospitalElements){
						def valueSets = HospitalValueSet.findAllByHospitalElement(he)
						for (vs in valueSets)
							_ids.add(vs.id)
					}
				}
				else{
					_ids.remove(0)
				}
				break;
		}
		
		if (_ids.size()>0){
			poids = _ids.join(",")
			res = " and PERSISTED_OBJECT_ID in (${poids})"
		}
		
		return res
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
				def assembly = "ihm_demo"
				
				render(contentType: "text/json") {
					report = "Audit Log"
					rid = 7
					data = array {
						for (r in results){
							row id			: r.get('ID'), 
								updated		: r.get('LAST_UPDATED'), //((Date)r.get('LAST_UPDATED')).format('dd/MM/yy HH:mm'),
								actor		: r.get('ACTOR'),
								event		: r.get('EVENT_NAME'),
								entity		: ((String)r.get('CLASS_NAME')).substring(assembly.length()),
								property	: r.get('PROPERTY_NAME'),
								nvalue		: r.get('NEW_VALUE'),
								ovalue		: r.get('OLD_VALUE'),
								
								created		: r.get('DATE_CREATED'), //((Date)r.get('DATE_CREATED')).format('dd/MM/yy HH:mm'),
								poid		: r.get('PERSISTED_OBJECT_ID')
								
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