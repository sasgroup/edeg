import ihm_demo.*

/*class BootStrap {
	
		def init = { servletContext ->
		}
		def destroy = {
		}
}*/
	
class BootStrap {

    def init = { servletContext ->
		
		def admin = new User(
			login:"admin",
			password: "admin",
			role:"admin")
			
		admin.save()

		def hospital_user = new User(
			login:"user",
			password: "user",
			role:"user")
	
		hospital_user.save()
		
		//-----------MEASURE_CATEGORYs-----------
		def measureCategory =new MeasureCategory(name:"CORE",
												 description:"Core Measure Category")
		if (!measureCategory.save()){
			measureCategory.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		measureCategory =new MeasureCategory(name:"MENU",
											 description:"Menu Measure Category")
		if (!measureCategory.save()){
			measureCategory.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measureCategory =new MeasureCategory(name:"CQM",
											 description:"Clinical Quality Measure y")
		if (!measureCategory.save()){
			measureCategory.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------CQM_DOMAINs-----------
		def cqmDomain =new CqmDomain(name:"Patient and Family Engagement",
									 notes:"Patient and Family Engagement")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		cqmDomain =new CqmDomain(name:"Care Coordination",
								 notes:"Care Coordination")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		cqmDomain =new CqmDomain(name:"Clinical Process / Effectiveness",
								 notes:"Clinical Process / Effectiveness")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		cqmDomain =new CqmDomain(name:"Patient Safety",
								 notes:"Patient Safety")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		cqmDomain =new CqmDomain(name:"Efficient Use of Healthcare Resources",
								 notes:"Efficient Use of Healthcare Resources")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------EHRs-----------
		def ehr
		ehr = new Ehr(code:"MEDITECH 6",
					  name:"MEDITECH Version 6.0",
					  notes:"MEDITECH Version 6.0")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		ehr = new Ehr(code:"MEDITECH 6.1",
					  name:"MEDITECH Version 6.1",
					  notes:"MEDITECH Version 6.1")
		if (!ehr.save()){
			ehr.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------HOSPITALs-----------
		def hospital
		hospital = new Hospital(name:"Massachusetts General Hospital",
								 notes:"",
								 ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		hospital = new Hospital(name:"Lindemann Mental Health Center",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Hebrew Rehabilitation Center for the Aged",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Faulkner Hospital",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Carney Hospital",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Franciscan Children's Hospital and Rehabilitation Center",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Tufts Medical Center",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}

		hospital = new Hospital(name:"Lowell General Hospital",
								notes:"",
								ehr:Ehr.findByCode("MEDITECH 6"))
		if (!hospital.save()){
			hospital.errors.allErrors.each{error ->
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
							  notes:""//"<script>alert('FY');</script>"//notes:""
							  )
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
		//-----------MEASUREs-----------
		def measure
		measure = new Measure(code:"CPOE",
							  name:"CPOE",
							  notes:"More than 60 percent of medication orders created by the EP or authorized providers of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period are recorded using CPOE",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		
		measure = new Measure(code:"Demograph",
							  name:"Demographics",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save()

		measure = new Measure(code:"Prob List",
							  name:"Problem List",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"Med List",
							  name:"Medication List",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		
		measure = new Measure(code:"MedAllerg",
							  name:"Medication Allergy",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"Vitals",
							  name:"Vitals",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"Smoking",
							  name:"Smoking",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"Adv Dir",
							  name:"Advance Directive",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"Lab Tests",
							  name:"Laboratory Test Results",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"EDU Res",
							  name:"Education Resources",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"Med Rec",
							  name:"Medication Reconciliation",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"Trans Sum",
							  name:"Transition Summary",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"CPOE-Med",
							  name:"CPOE - Medication Orders",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"CPOE-Lab",
							  name:"CPOE - Laboratory Orders",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"CPOE-Rad",
							  name:"CPOE - Radiology Orders",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"SumOfCare",
							  name:"Summary of Care",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"eSumOfCare",
							  name:"Summary of Care: Electronic Transmission",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"eMAR",
							  name:"eMAR",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CORE"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"eNote",
							  name:"Electronic Note",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"ImageResults",
							  name:"Imaging Results",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"Fam Hx",
							  name:"Family Health History",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save()
		
		measure = new Measure(code:"eRx",
							  name:"Electronic Prescriptions",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"Amb Labs",
							  name:"Lab Test Results to Ambulatory Providers",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save()
		
		measure = new Measure(code:"Amb Labs Alt",
							  name:"Lab Test Results to Ambulatory Providers (Alternative)",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("MENU"),
							  cqmDomain:null)
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.save() 
		
		measure = new Measure(code:"ED-1",
							  name:"ED-1: ED Arrival to Departure-Admitted Patients",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient and Family Engagement"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"ED-2",
							  name:"ED-2: Admit Decision to ED Departure-Admitted Patients",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient and Family Engagement"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"ED-3",
							  name:"ED-3: ED Arrival to ED Departure-Discharged Patients",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Care Coordination"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-2",
							  name:"STK-2: Discharged on Antithrombotic Therapy",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-3",
							  name:"STK-3: Anticoagulation for Atrial Fibrillation/Flutter",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-4",
							  name:"STK-4: Thrombolytic Therapy",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-5",
							  name:"STK-5: Antithrombotic By End of Hospital Day 2",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-6",
							  name:"STK-6: Discharged on Statin Medication",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-8",
							  name:"STK-8: Stroke Education",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient and Family Engagement"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"STK-10",
							  name:"STK-10: Assessed for Rehabilitation",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Care Coordination"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-1",
							  name:"VTE-1: VTE Prophylaxis",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-2",
							  name:"VTE-2:  ICU VTE Prophylaxis",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-3",
							  name:"VTE-3: VTE Patients with Anticoagulation Overlap",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-4",
							  name:"VTE-4: VTE Patients Monitored by Protocol/Nomogram",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-5",
							  name:"VTE-5:  VTE Discharge Instructions",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient and Family Engagement"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"VTE-6",
							  name:"VTE-6: Potentially-Preventable VTE",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"AMI-2",
							  name:"AMI-2: Aspirin at Discharge",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"AMI-7a",
							  name:"AMI-7a:  Fibrinolytic Within 30 Minutes of Arrival",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"AMI-8a",
							  name:"AMI-8a:  Primary PCI Within 90 Minutes of Arrival",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"SCIP-1",
							  name:"SCIP-INF-1: Abx Within 1 Hour Prior to Incision",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"SCIP-2",
							  name:"SCIP-INF-2: Abx Selection for Surg Patientsn",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Efficient Use of Healthcare Resources"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		
		measure = new Measure(code:"SCIP-9",
							  name:"SCIP-INF-9: Urinary catheter removed on POD 1 or 2",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"PC-01",
							  name:"PC-01: Elective Delivery",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"PC-05",
							  name:"PC-05: Exclusive Breast Milk Feeding",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		
		measure = new Measure(code:"HTN-0716",
							  name:"HTN-0716: Healthy Term Newborn",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient Safety"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() \
		
		measure = new Measure(code:"EHDI-1a",
							  name:"EHDI-1a: Hearing Screening Prior To Discharge",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Clinical Process / Effectiveness"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"CAC-3",
							  name:"CAC-3: Home Management Plan of Care Given to Patient",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Patient and Family Engagement"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save() 
		
		measure = new Measure(code:"PN-6a",
							  name:"PN-6a:  Initial Antibiotic Selection for CAP-ICU",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Efficient Use of Healthcare Resources"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		
		measure = new Measure(code:"PN-6b",
							  name:"PN-6b:  Initial Antibiotic Selection for CAP-Non ICU",
							  notes:"",
							  measureCategory : MeasureCategory.findByName("CQM"),
							  cqmDomain:CqmDomain.findByName("Efficient Use of Healthcare Resources"))
		
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		measure.addToProducts(Product.findByCode("MU2"))
		measure.addToProducts(Product.findByCode("MU1"))
		measure.save()
		//-----------DATA_ELEMENTs-----------
		def dataElement
		def dataElementDefaults
		//---
		dataElement = new DataElement(code:"AdmsDate",
									  name:"Admission Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admit.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admitDate",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"AdmsODate",
									  name:"Admission Order Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.save()
		
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"AdmsOTime",
									  name:"Admission Order Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.save()
		
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"AdmsSrc",
									  name:"Admission Source",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Med Rec"))
		dataElement.addToMeasures(Measure.findByCode("PN-6a"))
		dataElement.save()
		
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admit.source",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admit.source",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"AdmsTime",
									  name:"Admission Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admit.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.admit.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"AdvDirSts",
									  name:"Advance Directive Status",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Adv Dir"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"GEN.AD",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"GEN.AD",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"ArrDate",
									  name:"Arrival Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.arrival.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.arrival.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"ArrTime",
									  name:"Arrival Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.arrival.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.arrival.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"BirthDate",
									  name:"Birth Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.birthdate",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.birthdate",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"BloodPress",
									  name:"Blood Pressure Value - Ratio",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Vitals"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"VS.BP",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"VS.BP",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"DthCause",
									  name:"Death Cause",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.ccdqr.response",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.ccdqr.response",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DthDate",
									  name:"Death Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DthTime",
									  name:"Death Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DepDate",
									  name:"Departure Date from ED",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.er.depart.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.er.depart.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DepTime",
									  name:"Departure Time from ED",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.er.depart.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.er.depart.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DiagCode",
									  name:"Diagnosis Code",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.dx",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.dx",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DxDate",
									  name:"Discharge Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"DxTime",
									  name:"Discharge Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.discharge.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"EdDecision",
									  name:"ED Decision to Admit to Inpatient Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("ED-1"))
		dataElement.addToMeasures(Measure.findByCode("ED-2"))
		dataElement.addToMeasures(Measure.findByCode("ED-3"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.decision.to.admit.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.decision.to.admit.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"Gndr",
									  name:"Gender",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.sex",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.sex",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"HUnits",
									  name:"Height Units",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Vitals"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"HValue",
									  name:"Height Value",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Vitals"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PAT.ht.in.cm",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PAT.ht.in.cm",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"LabAddr",
									  name:"Lab Address",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.C.SITE.address.1",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.C.SITE.address.1",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabSpec",
									  name:"Lab Specimen Unacceptable",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"LabTestOrdCode",
									  name:"Lab Test Order Code",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.procedure.mnemonic",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.procedure.mnemonic",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestCodeDesc",
									  name:"Lab Test code description",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PROC.name",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PROC.name",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		//---
		dataElement = new DataElement(code:"LabTestCollDate",
									  name:"Lab Test code description",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.collection.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.collection.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		//---
		dataElement = new DataElement(code:"LabTestOrdID",
									  name:"Lab Test order ID",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.num",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.num",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestOrdBy",
									  name:"Lab Test ordered by",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.subm.doctor",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.subm.doctor",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestOrdTime",
									  name:"Lab Test Ordered Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.ORD.order.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestResCode",
									  name:"Lab Test Result Code",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.TEST.mnemonic",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.TEST.mnemonic",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestResVal",
									  name:"Lab Test Result Value",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.result",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.result",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestResRepDate",
									  name:"Lab Test Result Report Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.test.result.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.test.result.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestResRepTime",
									  name:"Lab Test Result Report Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.test.result.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.test.result.time",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestSpecSrc",
									  name:"Lab Test Specimen Source",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.spec.source",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.spec.source",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"LabTestUnits",
									  name:"Lab Test Units",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.units",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"LAB.L.SPEC.units",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"MedAdmin",
									  name:"Medication Administration",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("eMAR"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"OR-AED",
									  name:"OR - Anesthesia end time date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("VTE-1"))
		dataElement.addToMeasures(Measure.findByCode("VTE-2"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.2.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.2.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"OR-AET",
									  name:"OR - Anesthesia end time ",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("VTE-1"))
		dataElement.addToMeasures(Measure.findByCode("VTE-2"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.2",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.2",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"OR-AST",
									  name:"OR - Anesthesia start time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("VTE-1"))
		dataElement.addToMeasures(Measure.findByCode("VTE-2"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.1",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.1",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"OR-ASD",
									  name:"OR - Anesthesia start time date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("VTE-1"))
		dataElement.addToMeasures(Measure.findByCode("VTE-2"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.1.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"SCH.PAT.time.anes.1.date",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"PMRNum",
									  name:"Patient Medical Record number",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.unit.number",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.unit.number",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"PENum",
									  name:"Patient Encounter Number",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		for (m in Measure.list())
			dataElement.addToMeasures(m)
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.account.number",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ABS.PAT.account.number",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}  
		
		//---
		dataElement = new DataElement(code:"PrefLang",
									  name:"Preferred Language",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.language",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"ADM.PAT.language",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"RaceEthn",
									  name:"Race/Ethnicity",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"WUnits",
									  name:"Weight Units",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"WValue",
									  name:"Weight Value",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PAT.wt.in.oz",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"OE.PAT.wt.in.oz",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"MedOrdCPOEflg",
									  name:"Medication Ordered in CPOE Flag",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"MedOrdIndCodes",
									  name:"Code (s) which identifies the qualified individual who wrote the order",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"LabOrdCPOEflg",
									  name:"Laboratory Ordered in CPOE Flag",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"LabOrdIndCodes",
									  name:"Code (s) which identifies the qualified individual who wrote the order",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		/*dataElement = new DataElement(code:"RadOrdCPOEflg",
									  name:"Radiology Ordered in CPOE Flag",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"RadOrdIndCodes",
									  name:"Code (s) which identifies the qualified individual who wrote the order",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("CPOE"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"LangCodes",
									  name:"Language (632-9 Codes)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Demograph"))
		dataElement.save()
		 dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:ValueType.HospitalSpecific,
													  codeType:CodeType.CVX,
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		 if (!dataElementDefaults.save()){
			 dataElementDefaults.errors.allErrors.each{error ->
				 println "An error occured with event1: ${error}"
			 }
		 }
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"EDQuery",
									  name:"Education Query (multiple)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} */
		
		//---
		/*dataElement = new DataElement(code:"LabReqRec",
									  name:"Flag or other identifier: Lab request received from Ambulatory Provider (Hospital specific flag/identifier)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"LabResSentE",
									  name:"Flag or other identifier: Lab results sent electronically to Ambulatory Providers (Hospital specific flag/identifier)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"LabResRecE",
									  name:"Flag or other identifier: Lab request received electronically from Ambulatory Provider (Hospital specific flag/identifier)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"PatStatus4Lab",
									  name:"Patient Status for Lab (Hospital specific flag/identifier)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"eProgressNote",
									  name:"Electronic Progress note (Progress note format))",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"DxPrescription",
									  name:"Flag that a discharge prescription was electronically provided to another provider",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("eRx"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"FamHX",
									  name:"Family History (structured data recording first degree relative health history)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Fam Hx"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"ImgRecNum",
									  name:"Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Fam Hx"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"ImgRecFlg",
									  name:"Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Fam Hx"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"MedRecCompl",
									  name:"Medication Reconciliation Completed",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Med Rec"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"MedRecComplDate",
									  name:"Medication Reconciliation Completed Date",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Med Rec"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"MedRecComplTime",
									  name:"Medication Reconciliation Completed Time",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Med Rec"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"LabTestDesc",
									  name:"Lab Test Description (multiple)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElement.addToMeasures(Measure.findByCode("Lab Tests"))
		dataElement.save()
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"IPOS",
									  name:"Inpatient (POS Code)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"21",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"21",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		//---
		dataElement = new DataElement(code:"OPOS",
									  name:"Outpatient (POS Code)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"22",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"22",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"ERPOS",
									  name:"Emergency Room (POS Code)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"23",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"23",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"SmokSts",
									  name:"Smoking Status (references standard codes)",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"GEN.SMKST",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"GEN.SMKST",
													  source:"",
													  sourceEHR:"",
													  valueType:"StandartCode",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"SmrCareDocComplFlg",
									  name:"Flag that the Summary of care document was completed",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//---
		dataElement = new DataElement(code:"SmrCareDocSentE",
									  name:"Flag that the Summary of care document was sent electronically to another provider",
									  notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(1))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		} 
		
		dataElementDefaults = new DataElementDefaults(location:"",
													  source:"",
													  sourceEHR:"",
													  valueType:"HospitalSpecific",
													  codeType:"CPT",
													  dataElement :dataElement,
													  ehr : Ehr.get(2))
		
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}*/
    }
    def destroy = {
    }
}
