import ihm_demo.*

class BootStrap {

	def init = { servletContext ->
		//-----------EHRs-----------
		def ehr
		ehr = new Ehr(code:"mv5",
					  name:"Meditech Version 5",
					  notes:"")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		ehr = new Ehr(code:"mv6",
					  name:"Meditech Version 6",
					  notes:"")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		ehr = new Ehr(code:"ecw",
					  name:"eClinicalWorks",
					  notes:"")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		ehr = new Ehr(code:"cerner",
					  name:"Cerner",
					  notes:"")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------PRODUCTs-----------
		def product
		product = new Product(code:"MU1",
							  name:"Meaningful Use Solution 2014 Stage 1",
							  notes:"")
		if (!product.save()){
			product.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		product = new Product(code:"MU2",
							  name:"Meaningful Use Solution 2014 Stage 2",
							  notes:"")
		if (!product.save()){
			product.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		product = new Product(code:"IA",
							  name:"Infection Alert",
							  notes:"")
		if (!product.save()){
			product.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		product = new Product(code:"QA",
							  name:"Quality Alert ",
							  notes:"")
		if (!product.save()){
			product.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		//-----------HOSPITALs-----------
		def hospital
		hospital = new Hospital(name:"Massachusetts General Hospital",
								 notes:"",
								 ehr:Ehr.findByCode("mv5"),
								 products:Product.findByCode("MU2"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Lindemann Mental Health Center",
								notes:"",
								ehr:Ehr.findByCode("mv6"),
								products:Product.findByCode("MU1"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Hebrew Rehabilitation Center for the Aged",
								notes:"",
								ehr:Ehr.findByCode("mv6"),
								products:Product.findByCode("MU1"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Faulkner Hospital",
								notes:"",
								ehr:Ehr.findByCode("mv5"),
								products:Product.findByCode("MU2"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Carney Hospital",
								notes:"",
								ehr:Ehr.findByCode("mv6"),
								products:Product.findByCode("MU2"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Franciscan Children's Hospital and Rehabilitation Center",
								notes:"",
								ehr:Ehr.findByCode("mv5"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Tufts Medical Center",
								notes:"",
								ehr:Ehr.findByCode("mv5"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Lowell General Hospital",
								notes:"",
								ehr:Ehr.findByCode("mv5"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		hospital.addToProducts(product)
		hospital.save()
		
		
		//-----------USERs-----------
		def user1 = new Users(username:"sysUser",
							  password:"password1",
							  role:"SystemUser",
							  hospital:hospital)
		if (!user1.save()){
			user1.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		def user2 = new Users(username:"hosUser",
							  password:"password2",
							  role:"HospitalUser",
							  hospital:hospital)
		if (!user2.save()){
			user2.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------MEASURE_CATEGORYs-----------
		def measureCategory =new MeasureCategory(name:"core",
												  description:"core",
												 categoryType:"Core")
		if (!measureCategory.save()){
			measureCategory.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------CQM_DOMAINs-----------
		def cqmDomain =new CqmDomain(name:"domain",
										notes:"!")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------MEASUREs-----------
		def measure
		measure = new Measure(code:"CPOE - rad",
							  name:"CPOE - rad",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"CPOE - meds",
							  name:"CPOE - meds",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"CPOE - labs",
							  name:"CPOE - labs",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"Family Hx",
							  name:"Family Hx",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		measure.addToProducts(Product.findByCode("MU2"))
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"VTE 4",
							  name:"VTE 4",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		measure.addToProducts(Product.findByCode("MU1"))
		measure.addToProducts(Product.findByCode("MU2"))
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"CAC 3",
							  name:"CAC 3",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		measure.addToProducts(Product.findByCode("MU2"))
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"STK 10",
							  name:"STK 10",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		measure.addToProducts(Product.findByCode("MU2"))
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"PN 6",
							  name:"PN 6",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		measure.addToProducts(Product.findByCode("MU2"))
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		measure = new Measure(code:"CPOE - rad",
							  name:"CPOE - rad",
							  notes:"",
							  measureCategory:measureCategory,
							  cqmDomain:cqmDomain)
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------DATA_ELEMENTs-----------
		def dataElement
		dataElement = new DataElement(code:"ad",
										  name:"Admission Date",
									   notes:"")
		dataElement.addToMeasures(Measure.findByCode("Family Hx"))
		dataElement.addToMeasures(Measure.findByCode("VTE 4"))
		dataElement.addToMeasures(Measure.findByCode("CAC 3"))
		dataElement.addToMeasures(Measure.findByCode("STK 10"))
		dataElement.addToMeasures(Measure.findByCode("PN 6"))
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		dataElement = new DataElement(code:"pmrb",
									   name:"Patient Medical Record Number",
									   notes:"")
		dataElement.addToMeasures(Measure.findByCode("Family Hx"))
		dataElement.addToMeasures(Measure.findByCode("VTE 4"))
		dataElement.addToMeasures(Measure.findByCode("PN 6"))
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		dataElement = new DataElement(code:"vte",
									   name:"VTE Diagnostic Test",
									   notes:"")
		dataElement.addToMeasures(Measure.findByCode("VTE 4"))
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		dataElement = new DataElement(code:"e",
									   name:"Emphysema",
									   notes:"")
		dataElement.addToMeasures(Measure.findByCode("PN 6"))
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------DATA_ELEMENT_DEFAULTSs-----------
		def dataElementDefaults
		dataElementDefaults = new DataElementDefaults(isIMO:false,
														 location:"ADM.PAT.admit.date",
													  queryMnemonic:"",
													  valueSet:"",
													  valueSetRequired:false,
													  locationtype:"Internal",
													  dataElement : DataElement.findByCode("ad"),
													  ehr : Ehr.findByCode("mv5"))
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		dataElementDefaults = new DataElementDefaults(isIMO:false,
													  location:"AD.PT.admDate",
													  queryMnemonic:"",
													  valueSet:"",
													  valueSetRequired:false,
													  locationtype:"Internal",
													  dataElement : DataElement.findByCode("ad"),
													  ehr : Ehr.findByCode("mv6"))
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		dataElementDefaults = new DataElementDefaults(isIMO:false,
													  location:"",
													  queryMnemonic:"",
													  valueSet:"2.16.840.1.113883.3.117.1.7.1.276",
													  valueSetRequired:false,
													  locationtype:"Internal",
													  dataElement : DataElement.findByCode("vte"),
													  ehr : Ehr.findByCode("mv6"))
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		//-----------HOSPITAL_MEASUREs-----------
		def hospitalMeasure =new HospitalMeasure(approved:true,
												 completed:false,
												 confirmed:false,
												 included:false,
												 hospital : hospital,
												 measure : measure)
		if (!hospitalMeasure.save()){
			hospitalMeasure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------HOSPITAL_ELEMENTs-----------
		def hospitalElement =new HospitalElement(answer:"answer",
												   question:"question",
												   isIMO : true,
												   location:"location",
												   queryMnemonic:"queryMnemonic",
												   valueSet:"valueSet",
												   valueSetRequired:true,
												   locationtype:"Internal",
												   dataElementDefault : dataElementDefaults,
												   dataElement : dataElement)
		hospitalElement.addToHospitalMeasure(hospitalMeasure)
		if (!hospitalElement.save()){
			hospitalElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
	//end !
	}

	def destroy = {
	}
}