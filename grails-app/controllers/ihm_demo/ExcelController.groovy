package ihm_demo

import grails.converters.JSON
import pl.touk.excel.export.WebXlsxExporter
import pl.touk.excel.export.XlsxExporter
import pl.touk.excel.export.getters.LongToDatePropertyGetter
import pl.touk.excel.export.getters.MessageFromPropertyGetter

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;
import org.springframework.dao.DataIntegrityViolationException
import groovy.sql.Sql
import org.codehaus.groovy.grails.web.context.*

class ExcelController {
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
		
		def servletContext = ServletContextHolder.servletContext
		def rightNow = new Date().format('MM/dd/yyyy HH:mm')
		def rowNum = 2
		
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
				
				def storagePath = servletContext.getRealPath("excelTemplates/HospitalMeasureReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "MeasureGapReport_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["MEASURE GAP REPORT ("+ rightNow +")","","","","","","",""], 0)
				
				for (hp in hProducts){
					if (params.product == "0" || params.product == hp.product.id.toString()){
						for (hpm in hp.hospitalProductMeasures){
							if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString()){
								if (hpm.included && !hpm.hospitalMeasure.completed){
									exporter.fillRow([
										hpm.hospitalMeasure.hospital.name,
										hp.product.code,
										hpm.hospitalMeasure.measure.code,
										hpm.hospitalMeasure.measure.name,
										hpm.hospitalMeasure.completed?"*":"",
										hpm.hospitalMeasure.accepted?"*":"",
										hpm.hospitalMeasure.confirmed?"*":"",
										hpm.hospitalMeasure.verified?"*":""
										], rowNum++)
								}
							}
						}
					}
				}
				exporter.save(response.outputStream)
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
				
				def storagePath = servletContext.getRealPath("excelTemplates/HospitalMeasureReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "MeasureCompletenessReport_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["MEASURE COMPLETENESS REPORT ("+ rightNow +")","","","","","","",""], 0)
				
				for (hp in hProducts){
					if (params.product == "0" || params.product == hp.product.id.toString()){
						for (hpm in hp.hospitalProductMeasures){
							if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString()){
								if (hpm.included && hpm.hospitalMeasure.completed){
									exporter.fillRow([
										hpm.hospitalMeasure.hospital.name,
										hp.product.code,
										hpm.hospitalMeasure.measure.code,
										hpm.hospitalMeasure.measure.name,
										hpm.hospitalMeasure.completed?"*":"",
										hpm.hospitalMeasure.accepted?"*":"",
										hpm.hospitalMeasure.confirmed?"*":"",
										hpm.hospitalMeasure.verified?"*":""
										], rowNum++)
								}
							}
						}
					}
				}
				exporter.save(response.outputStream)
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
				
				def storagePath = servletContext.getRealPath("excelTemplates/HospitalProductReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "Hospital_ProductReport_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["HOSPITAL PRODUCT REPORT ("+ rightNow +")","",""], 0)
				
				for (hp in hProducts){
					if (params.product == "0" || params.product == hp.product.id.toString()){
						exporter.fillRow([
							hp.hospital.name,
							hp.product.code,
							hp.product.name
							], rowNum++)
					}
				}
				exporter.save(response.outputStream)
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
				
				def storagePath = servletContext.getRealPath("excelTemplates/HospitalMeasureReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "Hospital_MeasureReport_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["HOSPITAL MEASURE REPORT ("+ rightNow +")","","","","","","",""], 0)
				
				
				for (hp in hProducts){
					if (params.product == "0" || params.product == hp.product.id.toString()){
						for (hpm in hp.hospitalProductMeasures){
							if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString()){
								if (hpm.included){
									exporter.fillRow([
										hpm.hospitalMeasure.hospital.name,
										hp.product.code,
										hpm.hospitalMeasure.measure.code,
										hpm.hospitalMeasure.measure.name,
										hpm.hospitalMeasure.completed?"*":"",
										hpm.hospitalMeasure.accepted?"*":"",
										hpm.hospitalMeasure.confirmed?"*":"",
										hpm.hospitalMeasure.verified?"*":""
										], rowNum++)
								}
							}
						}
					}
				}
				exporter.save(response.outputStream)
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
				
				def storagePath = servletContext.getRealPath("excelTemplates/HospitalElementReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "Hospital_DataElements_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["HOSPITAL ELEMENTS REPORT ("+ rightNow +")","","","","",""], 0)
				
				for (he in hElements){
					if (params.element == "0" || params.element == he.dataElement.id.toString()){
						exporter.fillRow([
							he.hospital.name,
							he.dataElement.code,
							he.dataElement.name,
							he.location,
							he.source,
							he.valueType.toString()
							], rowNum++)
					}
				}
				exporter.save(response.outputStream)
			}
			
			
			
			
			else if (params.id == "6"){ // filtered data element report
				def hProducts = null
				if (params.hospital != "0"){
					def hospital = Hospital.get(params.hospital)
					hProducts = HospitalProduct.findAllByHospital(hospital)
				}
				else{
					hProducts = HospitalProduct.list()
				}
				
				def storagePath = servletContext.getRealPath("excelTemplates/FilteredElementReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "Hospital_FilteredDataElements_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["HOSPITAL FILTERED ELEMENTS REPORT ("+ rightNow +")","","","","","","",""], 0)
				
				for (hp in hProducts){
					if (params.product == "0" || params.product == hp.product.id.toString()){
						for (hpm in hp.hospitalProductMeasures){
							if (params.measure == "0" || params.measure == hpm.hospitalMeasure.measure.id.toString()){
								if (hpm.included){
									for (hme in hpm.hospitalMeasure.hospitalMeasureElements){
										if (params.element == "0" || params.element == hme.hospitalElement.dataElement.id.toString()){
											exporter.fillRow([
												hme.hospitalElement.hospital.name,
												hp.product.code,
												hpm.hospitalMeasure.measure.code,
												hme.hospitalElement.dataElement.code,
												hme.hospitalElement.dataElement.name,
												hme.hospitalElement.location,
												hme.hospitalElement.source,
												hme.hospitalElement.valueType.toString()
												], rowNum++)
										}
									}
								}
							}
						}
					}
				}
				exporter.save(response.outputStream)
			}
			
			
			
			
			else if (params.id == "7"){ 
				def sql = new Sql(dataSource)
				def results = sql.rows(prepSQL(params))
				def assembly = "ihm_demo"
				
				def storagePath = servletContext.getRealPath("excelTemplates/AuditLogReport.xlsx")
				
				WebXlsxExporter exporter = new WebXlsxExporter(storagePath)
				exporter.setResponseHeaders(response, "AuditLog_(" + new Date().format('MM.dd.yy_HH.mm')+").xlsx")
				exporter.fillRow(["AUDIT LOG REPORT ("+ rightNow +")","","","","","","",""], 0)
				
				for (r in results){
					exporter.fillRow([
						r.get('PERSISTED_OBJECT_ID'),
						r.get('LAST_UPDATED'), 
						r.get('ACTOR'),
						r.get('EVENT_NAME'),
						((String)r.get('CLASS_NAME')).substring(assembly.length()),
						r.get('PROPERTY_NAME'),
						r.get('OLD_VALUE'),
						r.get('NEW_VALUE')], rowNum++)
				}
				exporter.save(response.outputStream)
			}
			
			
			
			
			else{
				render "Wrong Request data for Report"
			}
		}

	}
		
}
		
