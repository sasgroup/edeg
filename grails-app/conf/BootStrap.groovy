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
		def _measureCategories = [
			["CORE", 	"Core Measure Category"],
			["MENU", 	"Menu Measure Category"],
			["CQM", 	"Clinical Quality Measure Category"]
			]
		for(_c in _measureCategories){
			def _measureCategory = new MeasureCategory(name:_c[0], description:_c[1])
			if (!_measureCategory.save()){
				_measureCategory.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
		}
		
		//-----------CQM_DOMAINs-----------
		def _cqmDomains = [
			["Patient and Family Engagement", 			"Patient and Family Engagement"],
			["Care Coordination", 						"Care Coordination"],
			["Clinical Process / Effectiveness", 		"Clinical Process / Effectiveness"],
			["Patient Safety", 							"Patient Safety"],
			["Efficient Use of Healthcare Resources", 	"Efficient Use of Healthcare Resources"]
			]
		for(_c in _cqmDomains){
			def _cqmDomain = new CqmDomain(name:_c[0], notes:_c[1])
			if (!_cqmDomain.save()){
				_cqmDomain.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
		}
		
		//-----------EHRs-----------
		def _ehrs = [
			["MEDITECH 6.0", "MEDITECH Version 6.0", "MEDITECH Version 6.0"],
			["MEDITECH 6.1", "MEDITECH Version 6.1", "MEDITECH Version 6.1"],
			["MEDITECH 6.2", "MEDITECH Version 6.2", "MEDITECH Version 6.2"],
			["MEDITECH 6.3", "MEDITECH Version 6.3", "MEDITECH Version 6.3"]
			]
		for(_e in _ehrs){
			def _ehr = new Ehr(code:_e[0], name:_e[1], notes:_e[2])
			if (!_ehr.save()){
				_ehr.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
		}
		
		//-----------HOSPITALs-----------
		def _hospitals = [
			["Massachusetts General Hospital",							"MEDITECH 6.0",""],
			["Lindemann Mental Health Center",							"MEDITECH 6.0",""],
			["Hebrew Rehabilitation Center for the Aged",				"MEDITECH 6.0",""],
			["Faulkner Hospital",										"MEDITECH 6.0",""],
			["Carney Hospital",											"MEDITECH 6.0",""],
			["Franciscan Children's Hospital and Rehabilitation Center","MEDITECH 6.0",""],
			["Tufts Medical Center",									"MEDITECH 6.0",""],
			["Lowell General Hospital",									"MEDITECH 6.0",""]
			]
		for(_h in _hospitals){
			def _hospital = new Hospital(name:_h[0], ehr:Ehr.findByCode(_h[1]), notes:_h[2])
			if (!_hospital.save()){
				_hospital.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
		}

		//-----------PRODUCTs-----------

		def _products = [
			["MU1",	"Meaningful Use Solution 2014 Stage 1",	""],
			["MU2",	"Meaningful Use Solution 2014 Stage 2",	""],
			["IA",	"Infection Alert",						""],
			["QA",	"Quality Alert",						""],
			// TEST
			["NP1",	"New Product 1",							"NP Test 1"],
			["NP2",	"New Product 2",							"NP Test 2"]
			]
		for(_p in _products){
			def _product = new Product(code:_p[0], name:_p[1], notes:_p[2])
			if (!_product.save()){
				_product.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}

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
>>>>>>> loginPageHelp
			}
		}
		
		//-----------MEASUREs-----------
		def _measures = [
			["CPOE", "CPOE", "", "CORE", "", ["MU1"]],
			["Demograph", "Demographics", "", "CORE", "", ["MU1","MU2"]],
			["Prob List", "Problem List", "", "CORE", "", ["MU1"]],
			["Med List", "Medication List", "", "CORE", "", ["MU1"]],
			["MedAllerg", "Medication Allergy", "", "CORE", "", ["MU1"]],
			["Vitals", "Vitals", "", "CORE", "", ["MU1","MU2"]],
			["Smoking", "Smoking", "", "CORE", "", ["MU1","MU2"]],
			["AdvDir", "Advance Directive", "", "MENU", "", ["MU1","MU2"]],
			["LabTests (Menu)", "Laboratory Test Results", "", "MENU", "", ["MU1"]],
			["EDURes (Menu)", "Education Resources", "", "MENU", "", ["MU1"]],
			["MedRec (Menu)", "Medication Reconciliation", "", "MENU", "", ["MU1"]],
			["Trans Sum", "Transition Summary", "", "MENU", "", ["MU1"]],
			["CPOE-Med", "CPOE - Medication Orders", "", "CORE", "", ["MU2"]],
			["CPOE-Lab", "CPOE - Laboratory Orders", "", "CORE", "", ["MU2"]],
			["CPOE-Rad", "CPOE - Radiology Orders", "", "CORE", "", ["MU2"]],
			["LabTests (Core)", "Laboratory Test Results", "", "CORE", "", ["MU2"]],
			["EDURes (Core)", "Education Resources", "", "CORE", "", ["MU2"]],
			["MedRec (Core)", "Medication Reconciliation", "", "CORE", "", ["MU2"]],
			["SumOfCare", "Summary of Care", "", "CORE", "", ["MU2"]],
			["eSumOfCare", "Summary of Care: Electronic Transmission", "", "CORE", "", ["MU2"]],
			["eMAR", "eMAR", "", "CORE", "", ["MU2"]],
			["eNote", "Electronic Note", "", "MENU", "", ["MU2"]],
			["ImageResults", "Imaging Results", "", "MENU", "", ["MU2"]],
			["Fam Hx", "Family Health History", "", "MENU", "", ["MU2"]],
			["eRx", "Electronic Prescriptions", "", "MENU", "", ["MU2"]],
			["Amb Labs", "Lab Test Results to Ambulatory Providers", "", "MENU", "", ["MU2"]],
			["Amb Labs Alt", "Lab Test Results to Ambulatory Providers (Alternative)", "", "MENU", "", ["MU2"]],
			["ED-1", "ED-1: ED Arrival to Departure-Admitted Patients", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-2", "ED-2: Admit Decision to ED Departure-Admitted Patients", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-3", "ED-3: ED Arrival to ED Departure-Discharged Patients", "", "CQM", "Care Coordination", ["MU1","MU2"]],
			["STK-2", "STK-2: Discharged on Antithrombotic Therapy", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-3", "STK-3: Anticoagulation for Atrial Fibrillation/Flutter", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-4", "STK-4: Thrombolytic Therapy", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-5", "STK-5: Antithrombotic By End of Hospital Day 2", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-6", "STK-6: Discharged on Statin Medication", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-8", "STK-8: Stroke Education", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["STK-10", "STK-10: Assessed for Rehabilitation", "", "CQM", "Care Coordination", ["MU1","MU2"]],
			["VTE-1", "VTE-1: VTE Prophylaxis", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-2", "VTE-2:  ICU VTE Prophylaxis", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-3", "VTE-3: VTE Patients with Anticoagulation Overlap ", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-4", "VTE-4: VTE Patients Monitored by Protocol/Nomogram", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-5", "VTE-5:  VTE Discharge Instructions", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["VTE-6", "VTE-6: Potentially-Preventable VTE", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["AMI-2", "AMI-2: Aspirin at Discharge", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-7a", "AMI-7a:  Fibrinolytic Within 30 Minutes of Arrival", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-8a", "AMI-8a:  Primary PCI Within 90 Minutes of Arrival", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-10", "AMI-10: Statin Prescribed at Discharge", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["SCIP-1", "SCIP-INF-1: Abx Within 1 Hour Prior to Incision", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["SCIP-2", "SCIP-INF-2: Abx Selection for Surg Patients", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["SCIP-9", "SCIP-INF-9: Urinary catheter removed on POD 1 or 2", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["PC-01", "PC-01: Elective Delivery", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["PC-05", "PC-05: Exclusive Breast Milk Feeding ", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["HTN-0716", "HTN-0716: Healthy Term Newborn", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["EHDI-1a", "EHDI-1a: Hearing Screening Prior To Discharge", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["CAC-3", "CAC-3: Home Management Plan of Care Given to Patient", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["PN-6a", "PN-6a:  Initial Antibiotic Selection for CAP-ICU", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["PN-6b", "PN-6b:  Initial Antibiotic Selection for CAP-Non ICU", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			// TEST
			["NM1", "New Measure 1", "", "CORE", "", ["NP1"]],
			["NM2", "New Measure 2", "", "MENU", "", ["NP1","NP2"]],
			["NM3", "New Measure 3", "", "CORE", "", ["NP1","NP2"]],
			["NM4", "New Measure 4", "", "MENU", "", ["NP2"]]
			
			]
		for(_m in _measures){
			def _measure = new Measure(code:_m[0], name:_m[1], notes:_m[2], measureCategory:MeasureCategory.findByName(_m[3]), cqmDomain:CqmDomain.findByName(_m[4]))
			
			for(_p in _m[5])
				_measure.addToProducts(Product.findByCode(_p))
			
			if (!_measure.save()){
				_measure.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
			else{
				_measure.save(flush:true)
			}
		}
		
		//-----------DATA_ELEMENTs-----------
		
		def _elementsAllMeasures = [	
			["AdmsDate", 	"Admission Date", 					"ADM.PAT.admit.date", 		"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsTime", 	"Admission Time", 					"ADM.PAT.admit.time", 		"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DxDate", 		"Discharge Date", 					"ADM.PAT.discharge.date", 	"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DxTime", 		"Discharge Time",					"ADM.PAT.discharge.time", 	"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PMRNum", 		"Patient Medical Record number", 	"ABS.PAT.unit.number", 		"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PENum", 		"Patient Encounter Number", 		"ABS.PAT.account.number", 	"StandartCode", ["MEDITECH 6.0", "MEDITECH 6.1"]]
	    ]
		for(_el in _elementsAllMeasures){
			def _element = new DataElement(code:_el[0], name:_el[1], notes:"")
			
			for (m in Measure.list())
				_element.addToMeasures(m)
			
			if (!_element.save()){
				_element.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
			else{
				_element.save(flush:true)
			}
			
			for(_ehr in _el[4]){
				def dataElementDefaults = new DataElementDefaults(location:_el[2], valueType:_el[3], codeType:"NotApplicable", dataElement:_element, ehr:Ehr.findByCode(_ehr))
				if (!dataElementDefaults.save()){
					dataElementDefaults.errors.allErrors.each{error ->
						println "An error occured with event1: ${error}"
					}
				}
				else{
					dataElementDefaults.save(flush:true)
				}
			}
		}
		
		
		def _elements = [
			["AdmsODate", "Admission Order Date", "OE.ORD.order.date", "StandartCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsOTime", "Admission Order Time", "OE.ORD.order.time", "StandartCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsSrc", "Admission Source", "ADM.PAT.admit.source", "StandartCode", ["MedRec (Menu)","MedRec (Core)", "PN-6a", "PN-6b"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdvDirSts", "Advance Directive Status", "GEN.AD", "StandartCode", ["AdvDir","AdvDir"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ArrDate", "Arrival Date", "ABS.PAT.arrival.date", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ArrTime", "Arrival Time", "ABS.PAT.arrival.time", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["BirthDate", "Birth Date", "ABS.PAT.birthdate", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["BloodPress", "Blood Pressure Value - Ratio", "VS.BP", "StandartCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthCause", "Death Cause", "ADM.PAT.ccdqr.response", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthDate", "Death Date", "ADM.PAT.discharge.date", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthTime", "Death Time", "ADM.PAT.discharge.time", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DepDate", "Departure Date from ED", "ABS.PAT.er.depart.date", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DepTime", "Departure Time from ED", "ABS.PAT.er.depart.time", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DiagCode", "Diagnosis Code", "ABS.PAT.dx", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["EdDecision", "ED Decision to Admit to Inpatient Time", "ABS.PAT.decision.to.admit.time", "StandartCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["Gndr", "Gender", "ABS.PAT.sex", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["HUnits", "Height Units", "", "HospitalSpecific", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["HValue", "Height Value", "OE.PAT.ht.in.cm", "StandartCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabAddr", "Lab Address", "LAB.C.SITE.address.1", "StandartCode", ["CPOE", "LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabSpec", "Lab Specimen Unacceptable", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdCode", "Lab Test Order Code", "OE.ORD.procedure.mnemonic", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestCodeDesc", "Lab Test code description", "OE.PROC.name", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestCollDate", "Lab Test collected date", "LAB.L.SPEC.collection.date", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdID", "Lab Test order ID", "OE.ORD.order.num", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdBy", "Lab Test ordered by", "LAB.L.SPEC.subm.doctor", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdTime", "Lab Test Ordered Time", "OE.ORD.order.time", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResCode", "Lab Test Result Code", "LAB.L.TEST.mnemonic", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResVal", "Lab Test Result Value", "LAB.L.SPEC.result", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResRepDate", "Lab Test Result Report Date", "LAB.L.SPEC.test.result.date", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResRepTime", "Lab Test Result Report Time", "LAB.L.SPEC.test.result.time", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestSpecSrc", "Lab Test Specimen Source", "LAB.L.SPEC.spec.source", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestUnits", "Lab Test Units", "LAB.L.SPEC.units", "StandartCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedAdmin", "Medication Administration", "", "NotApplicable", ["eMAR"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AED", "OR - Anesthesia end time date", "SCH.PAT.time.anes.2.date", "StandartCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AET", "OR - Anesthesia end time ", "SCH.PAT.time.anes.2", "StandartCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AST", "OR - Anesthesia start time", "SCH.PAT.time.anes.1", "StandartCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-ASD", "OR - Anesthesia start time date", "SCH.PAT.time.anes.1.date", "StandartCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PrefLang", "Preferred Language", "ADM.PAT.language", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["RaceEthn", "Race/Ethnicity", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["WUnits", "Weight Units", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["WValue", "Weight Value", "OE.PAT.wt.in.oz", "StandartCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedOrdCPOEflg", "Medication Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabOrdCPOEflg", "Laboratory Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["RadOrdCPOEflg", "Radiology Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["RadOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LangCodes", "Language (632-9 Codes)", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["EDQuery", "Education Query (multiple)", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabReqRec", "Flag or other identifier: Lab request received from Ambulatory Provider (Hospital specific flag/identifier)", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabResSentE", "Flag or other identifier: Lab results sent electronically to Ambulatory Providers (Hospital specific flag/identifier)", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabResRecE", "Flag or other identifier: Lab request received electronically from Ambulatory Provider (Hospital specific flag/identifier)", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PatStatus4Lab", "Patient Status for Lab (Hospital specific flag/identifier)", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["eProgressNote", "Electronic Progress note (Progress note format)", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DxPrescription", "Flag that a discharge prescription was electronically provided to another provider", "", "HospitalSpecific", ["eRx"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["FamHX", "Family History (structured data recording first degree relative health history)", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ImgRecNum", "Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed) : Are there image record numbers or identifiers that correspond to the text result?", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ImgRecFlg", "Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed) : Is there a flag that signifies an image has been read and a report generated and placed in the record?", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedRecCompl", "Medication Reconciliation Completed", "", "HospitalSpecific", ["MedRec (Menu)","MedRec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedRecComplDate", "Medication Reconciliation Completed Date", "", "HospitalSpecific", ["MedRec (Menu)","MedRec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedRecComplTime", "Medication Reconciliation Completed Time", "", "HospitalSpecific", ["MedRec (Menu)","MedRec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestDesc", "Lab Test Description (multiple)", "", "HospitalSpecific", ["LabTests (Menu)","LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["IPOS", "Inpatient (POS Code)", "21", "StandartCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OPOS", "Outpatient (POS Code)", "22", "StandartCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ERPOS", "Emergency Room (POS Code)", "23", "StandartCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmokSts", "Smoking Status (references standard codes)", "GEN.SMKST", "StandartCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmrCareDocComplFlg", "Flag that the Summary of care document was completed", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmrCareDocSentE", "Flag that the Summary of care document was sent electronically to another provider", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			
			// TEST
			["NDE1", "New Data Element 1", "Loc.1.1.1", "StandartCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE2", "New Data Element 2", "Loc.1.1.2", "StandartCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE3", "New Data Element 3", "Loc.1.1.3", "StandartCode", ["NM3","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE4", "New Data Element 4", "Loc.1.1.4", "StandartCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE5", "New Data Element 5", "Loc.1.1.5", "StandartCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE6", "New Data Element 6", "Loc.1.1.6", "StandartCode", ["NM1","NM3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE7", "New Data Element 7", "Loc.1.1.7", "StandartCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE8", "New Data Element 8", "Loc.1.1.8", "StandartCode", ["NM4","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE9", "New Data Element 9", "Loc.1.1.9", "StandartCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
		]
		for(_el in _elements){
			def _element = new DataElement(code:_el[0], name:_el[1], notes:"")
			
			for(_msr in _el[4]){
				def _measure = Measure.findByCode(_msr)
				if(!_measure)
					println _msr
				else
					_element.addToMeasures(Measure.findByCode(_msr))
			}
			if (!_element.save()){
				_element.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
			else{
				_element.save(flush:true)
			}
			
			for(_ehr in _el[5]){
				def dataElementDefaults = new DataElementDefaults(location:_el[2], valueType:_el[3], codeType:"NotApplicable", dataElement:_element, ehr : Ehr.findByCode(_ehr))
				if (!dataElementDefaults.save()){
					dataElementDefaults.errors.allErrors.each{error ->
						println "An error occured with event1: ${error}"
					}
				}
				else{
					dataElementDefaults.save(flush:true)
				}
			}
		}

		//---
    }
    def destroy = {
    }
}
