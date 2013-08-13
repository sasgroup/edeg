import ihm_demo.*

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
			["Lowell General Hospital",									"MEDITECH 6.0",""],
			["Hospital Corporation of America (HCA)",					"MEDITECH 6.1",""],
			["United States Marine Hospital",							"MEDITECH 6.1",""],
			["Chicago Hospital for Women and Children",					"MEDITECH 6.1",""],
			["Louis A. Weiss Memorial Hospital",						"MEDITECH 6.1",""],
			["Lurie Children's Hospital",								"MEDITECH 6.1",""],
			["Mercy Hospital and Medical Center",						"MEDITECH 6.2",""],
			["Michael Reese Hospital",									"MEDITECH 6.3",""],
			["Northwestern Memorial Hospital",							"MEDITECH 6.2",""],
			["Rehabilitation Institute of Chicago",						"MEDITECH 6.2",""],
			["Ruth M. Rothstein CORE Center",							"MEDITECH 6.2",""],
			["University of Chicago Medical Center",					"MEDITECH 6.3",""],
			["Swedish Covenant Hospital",								"MEDITECH 6.3",""],
			["Fawcett Memorial Hospital",								"MEDITECH 6.3",""],
			["HealthONE Colorado",										"MEDITECH 6.3",""],
			["Tulane Medical Center",									"MEDITECH 6.2",""],
			["Wellington Hospital, London",								"MEDITECH 6.2",""],
			["Spotsylvania Regional Medical Center",					"MEDITECH 6.0",""],
			["London Bridge Hospital",									"MEDITECH 6.0",""]
			
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
			}
		}

		//-----------MEASUREs-----------
		def _measures = [
		//	[CODE, NAME, NOTES, HELP, CATEGORY, CQM, PRODUCTS:[] ],
			["CPOE", 
				"CPOE", 
				"This measure reflects the percentage of Medication, Laboratory and Radiology ordered entered into the CEHR by a qualified professional using CPOE (each type of order is measured separately).\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"", 
				"CORE", "", ["MU1"]],
			["Demograph", 
				"Demographics", 
				"This measure reflects the percentage of patients who have demographic information recorded as structured data\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 80 percent of all unique patients seen by the EP or admitted to the eligible hospital’s or CAH’s inpatient or emergency department (POS 21 or 23) during the EHR reporting period have demographics recorded as structured data.<br><br>Note:&nbsp; For Race -fhjh&nbsp; and Ethnicity the Core Measure asks for it to be structured and the CQM measures ask that Race and Ethnicity be codified.<br>To satisfy both the hospital would need to provide two answers to each responses, one for the core measure and one for the CQM.<br>However, if the hospital would like to build in the CQM Value Set codes and request that the product use those to identify Race and Ethnicity, the product can do that.&nbsp; Please refer to the individual CQM value sets (available on all CQM measure pages) for the specific codes.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 53993</a>", 
				"CORE", "", ["MU1","MU2"]],
			["Prob List", "Problem List", "", "", "CORE", "", ["MU1"]],
			["Med List", "Medication List", "", "", "CORE", "", ["MU1"]],
			["MedAllerg", "Medication Allergy", "", "", "CORE", "", ["MU1"]],
			["Vitals", 
				"Vitals", 
				"This measure reflects the percentage of unique patients who have appropriate vital sign information recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 80 percent of all unique or admitted to the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period have blood pressure (for patients age 3 and over only) and height/length and weight (for all ages) recorded as structured data.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 53994</a>",
				"CORE", "", ["MU1","MU2"]],
			["Smoking", 
				"Smoking", 
				"This measure reflects the percentage of patients age 13 or older who have Smoking Status recorded as structured data.", 
				"More than 80 percent of all unique patients 13 years old or older seen by the EP or admitted to the eligible hospital’s or CAH’s inpatient or emergency departments (POS 21 or 23) during the EHR reporting period have smoking status recorded as structured data.",
				"CORE", "", ["MU1","MU2"]],
			["AdvDir", 
				"Advance Directive", 
				"This measure reflects the percentage of unique patients age 65 and older who have advance directive status recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 50 percent of all unique patients 65 years old or older admitted to the eligible hospital’s or CAH’s inpatient department (POS 21) during the EHR reporting period have an indication of an advance directive status recorded as structured data.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54041</a>",
				"MENU", "", ["MU1","MU2"]],
			["LabTests (Menu)", "Laboratory Test Results", "", "", "MENU", "", ["MU1"]],
			["EDURes (Menu)", "Education Resources", "", "", "MENU", "", ["MU1"]],
			["MedRec (Menu)", "Medication Reconciliation", "", "", "MENU", "", ["MU1"]],
			["Trans Sum", "Transition Summary", "", "", "MENU", "", ["MU1"]],
			["CPOE-Med", "CPOE - Medication Orders", "", "", "CORE", "", ["MU2"]],
			["CPOE-Lab", "CPOE - Laboratory Orders", "", "", "CORE", "", ["MU2"]],
			["CPOE-Rad", "CPOE - Radiology Orders", "", "", "CORE", "", ["MU2"]],
			["LabTests (Core)", 
				"Laboratory Test Results", 
				"This measure reflects the percentage of lab test results (whose results are positive/negative or numerical)recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 55 percent of all clinical lab tests results ordered by the EP or by authorized providers of the eligible hospital or CAH for patients admitted to its inpatient or emergency department (POS 21 or 23) during the EHR reporting period whose results are either in a positive/negative or numerical format are  incorporated in CEHRT as structured data.<br><br>Note: This measure allows the facility to chose the population for this measure.  The hospital should identify the list of lab tests which they expect to have a positive/negative result 100% of the time AND a list of lab tests that is expected to have a numeric value 100% of the time.<br><br>The MUS product will look for the hospital specified tests and then check to see that they have the correct results recorded in the electronic medical record.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54004</a>",
				"CORE", "", ["MU2"]],
			["EDURes (Core)", 
				"Education Resources", 
				"This measure reflects the percentage of unique patients who are provided patient-specific educational resources identified by the hospital's CEHRT.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 10 percent of all unique patients admitted to the eligible hospital’s or CAH’s inpatient or emergency departments (POS 21 or 23) are provided patient-specific education resources identified by Certified EHR Technology.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54012</a>",
				"CORE", "", ["MU2"]],
			["MedRec (Core)", 
				"Medication Reconciliation", 
				"This measure reflects the percentage of transitions of care where medication reconciliation was performed.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"The EP, eligible hospital or CAH performs medication reconciliation for more than 50 percent of transitions of care in which the patient is transitioned into the care of<br>the EP or admitted to the eligible hospital’s or CAH’s inpatient or<br>emergency department (POS 21 or 23).<br><br>Please list any additional codes, NUBC number and mnemonic for any other admit sources which signify that a patient was transferred INTO your facility that are not already listed.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54013</a>",
				"CORE", "", ["MU2"]],
			["SumOfCare", 
				"Summary of Care", 
				"This measure reflects the percentage of transitions of care/referrals  where a summary of care record is provided to the recieving provider/hospital.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"The EP, eligible hospital or CAH that transitions or refers their patient to another setting of care or provider of care provides a summary of care record for more than 50 percent of transitions of care and referrals.<br><br>For this indicator the population consists of those patients transferred from the hospital to another facility.  The discharge codes enable this.<br><br>Please identify all the codes that your facility uses.  The standard codes listed here are the codes which are referenced in the Federal Register.  Space has been provided to list any additional codes in use by the hospital.<br><br><b>IMPORTANT NOTE:</b>This indicator now contains the  Problem List, Med List and Med Allergy Listing from the Stage 1 indicators.  Furthermore, the regulations identify the components of a Summary of Care: Patient Name, Procedures, Encounter diagnosis, Immunizations, Laboratory test results, Vital Signs - Height, Vital Signs - Weight, Vital Signs - Blood Pressure, Vital Signs - BMI, Smoking Status, Functional Status - ADL, Functional Status - Cognitive Status, Functional Status - Disability Status, Demographic Information - Preferred Language, Demographic Information - Sex, Demographic Information - Race, Demographic Information - Ethnicity, Demographic Information - Date of Birth, Care Plan Field - Goals, Care Plan Field - Instructions, Care Team - Primary Care Provider, Care Team - Any Additional Team Members, Discharge Instructions, *Current Problem List, Current Medication List, Current Medication Allergy List<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54016</a>",
				"CORE", "", ["MU2"]],
			["eSumOfCare", "Summary of Care: Electronic Transmission", "", "CORE", "", ["MU2"]],
			["eMAR", 
				"eMAR", 
				"This measure reflects the percentage of medication orders tracked from order through administration using electronic medication administration record (eMAR).\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 10 percent of medication orders created by authorized providers of the eligible hospital’s or CAH’s inpatient or emergency department (POS 21 or 23) during the EHR reporting period are tracked using eMAR.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54033</a>",
				"CORE", "", ["MU2"]],
			["eNotes", 
				"Electronic Notes", 
				"This measure reflects the percentage of unique patients who have at least one electronic progress note created, edited and signed by an authorized provider.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"Enter at least one electronic progress note created, edited and signed by an authorized provider of the eligible hospital’s or CAH’s inpatient or emergency department (POS 21 or 23) for more than 30 percent of unique patients admitted to the eligible hospital or CAH’s inpatient or emergency department during the EHR reporting period.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54027</a>",
				"MENU", "", ["MU2"]],
			["ImageResults", 
				"Imaging Results", 
				"This measure reflects the percentage of scans and tests whose result is at least one image whose results are accessible via CEHRT.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 10 percent of all scans and tests whose result is one or more images ordered by the EP or by an authorized provider of the eligible hospital or CAH for patients admitted to its inpatient or emergency department (POS 21 or 23) during the EHR reporting period are accessible through CEHRT.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54044</a>",
				"MENU", "", ["MU2"]],
			["FamHx", 
				"Family Health History", 
				"",
				"<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54013</a>",
				"MENU", "", ["MU2"]],
			["eRx", 
				"Electronic Prescriptions", 
				"", 
				"",
				"MENU", "", ["MU2"]],
			["Amb Labs", "Lab Test Results to Ambulatory Providers", "", "", "MENU", "", ["MU2"]],
			["Amb Labs Alt", "Lab Test Results to Ambulatory Providers (Alternative)", "", "", "MENU", "", ["MU2"]],
			["ED-1", "ED-1: ED Arrival to Departure-Admitted Patients", "", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-2", "ED-2: Admit Decision to ED Departure-Admitted Patients", "", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-3", "ED-3: ED Arrival to ED Departure-Discharged Patients", "", "", "CQM", "Care Coordination", ["MU1","MU2"]],
			["STK-2", "STK-2: Discharged on Antithrombotic Therapy", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-3", "STK-3: Anticoagulation for Atrial Fibrillation/Flutter", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-4", "STK-4: Thrombolytic Therapy", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-5", "STK-5: Antithrombotic By End of Hospital Day 2", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-6", "STK-6: Discharged on Statin Medication", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-8", "STK-8: Stroke Education", "", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["STK-10", "STK-10: Assessed for Rehabilitation", "", "", "CQM", "Care Coordination", ["MU1","MU2"]],
			["VTE-1", "VTE-1: VTE Prophylaxis", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-2", "VTE-2:  ICU VTE Prophylaxis", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-3", "VTE-3: VTE Patients with Anticoagulation Overlap", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-4", "VTE-4: VTE Patients Monitored by Protocol/Nomogram", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-5", "VTE-5:  VTE Discharge Instructions", "", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["VTE-6", "VTE-6: Potentially-Preventable VTE", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["AMI-2", "AMI-2: Aspirin at Discharge", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-7a", "AMI-7a:  Fibrinolytic Within 30 Minutes of Arrival", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-8a", "AMI-8a:  Primary PCI Within 90 Minutes of Arrival", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-10", "AMI-10: Statin Prescribed at Discharge", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["SCIP-1", "SCIP-INF-1: Abx Within 1 Hour Prior to Incision", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["SCIP-2", "SCIP-INF-2: Abx Selection for Surg Patients", "", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["SCIP-9", "SCIP-INF-9: Urinary catheter removed on POD 1 or 2", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["PC-01", "PC-01: Elective Delivery", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["PC-05", "PC-05: Exclusive Breast Milk Feeding ", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["HTN-0716", "HTN-0716: Healthy Term Newborn", "", "", "CQM", "Patient Safety", ["MU1","MU2"]],
			["EHDI-1a", "EHDI-1a: Hearing Screening Prior To Discharge", "", "", "CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["CAC-3", "CAC-3: Home Management Plan of Care Given to Patient", "", "", "CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["PN-6a", "PN-6a:  Initial Antibiotic Selection for CAP-ICU", "", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["PN-6b", "PN-6b:  Initial Antibiotic Selection for CAP-Non ICU", "", "", "CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			// TEST
			["NM1", "New Measure 1", "", "", "CORE", "", ["NP1"]],
			["NM2", "New Measure 2", "", "", "MENU", "", ["NP1","NP2"]],
			["NM3", "New Measure 3", "", "", "CORE", "", ["NP1","NP2"]],
			["NM4", "New Measure 4", "", "", "MENU", "", ["NP2"]]

			]
		for(_m in _measures){
			def _measure = new Measure(code:_m[0], name:_m[1], notes:_m[2], help:_m[3], measureCategory:MeasureCategory.findByName(_m[4]), cqmDomain:CqmDomain.findByName(_m[5]))

			for(_p in _m[6])
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
			["AdmsDate", 	"Admission Date", 					"ADM.PAT.admit.date", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsTime", 	"Admission Time", 					"ADM.PAT.admit.time", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DxDate", 		"Discharge Date", 					"ADM.PAT.discharge.date", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DxTime", 		"Discharge Time",					"ADM.PAT.discharge.time", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PMRNum", 		"Patient Medical Record number", 	"ABS.PAT.unit.number", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PENum", 		"Patient Encounter Number", 		"ABS.PAT.account.number", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"]]
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
			["AdmsODate", "Admission Order Date", "OE.ORD.order.date", "StandardCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsOTime", "Admission Order Time", "OE.ORD.order.time", "StandardCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdmsSrc", "Admission Source", "ADM.PAT.admit.source", "StandardCode", ["MedRec (Menu)","MedRec (Core)", "PN-6a", "PN-6b"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["AdvDirSts", "Advance Directive Status", "GEN.AD", "StandardCode", ["AdvDir","AdvDir"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ArrDate", "Arrival Date", "ABS.PAT.arrival.date", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ArrTime", "Arrival Time", "ABS.PAT.arrival.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["BirthDate", "Birth Date", "ABS.PAT.birthdate", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["BloodPress", "Blood Pressure Value - Ratio", "VS.BP", "StandardCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthCause", "Death Cause", "ADM.PAT.ccdqr.response", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthDate", "Death Date", "ADM.PAT.discharge.date", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DthTime", "Death Time", "ADM.PAT.discharge.time", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DepDate", "Departure Date from ED", "ABS.PAT.er.depart.date", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DepTime", "Departure Time from ED", "ABS.PAT.er.depart.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["DiagCode", "Diagnosis Code", "ABS.PAT.dx", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["EdDecision", "ED Decision to Admit to Inpatient Time", "ABS.PAT.decision.to.admit.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["Gndr", "Gender", "ABS.PAT.sex", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["HUnits", "Height Units", "", "HospitalSpecific", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["HValue", "Height Value", "OE.PAT.ht.in.cm", "StandardCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabAddr", "Lab Address", "LAB.C.SITE.address.1", "StandardCode", ["CPOE", "LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabSpec", "Lab Specimen Unacceptable", "", "HospitalSpecific", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdCode", "Lab Test Order Code", "OE.ORD.procedure.mnemonic", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestCodeDesc", "Lab Test code description", "OE.PROC.name", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestCollDate", "Lab Test collected date", "LAB.L.SPEC.collection.date", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdID", "Lab Test order ID", "OE.ORD.order.num", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdBy", "Lab Test ordered by", "LAB.L.SPEC.subm.doctor", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestOrdTime", "Lab Test Ordered Time", "OE.ORD.order.time", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResCode", "Lab Test Result Code", "LAB.L.TEST.mnemonic", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResVal", "Lab Test Result Value", "LAB.L.SPEC.result", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResRepDate", "Lab Test Result Report Date", "LAB.L.SPEC.test.result.date", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestResRepTime", "Lab Test Result Report Time", "LAB.L.SPEC.test.result.time", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestSpecSrc", "Lab Test Specimen Source", "LAB.L.SPEC.spec.source", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["LabTestUnits", "Lab Test Units", "LAB.L.SPEC.units", "StandardCode", ["LabTests (Menu)", "LabTests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["MedAdmin", "Medication Administration", "", "NotApplicable", ["eMAR"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AED", "OR - Anesthesia end time date", "SCH.PAT.time.anes.2.date", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AET", "OR - Anesthesia end time ", "SCH.PAT.time.anes.2", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-AST", "OR - Anesthesia start time", "SCH.PAT.time.anes.1", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OR-ASD", "OR - Anesthesia start time date", "SCH.PAT.time.anes.1.date", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["PrefLang", "Preferred Language", "ADM.PAT.language", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["RaceEthn", "Race/Ethnicity", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["WUnits", "Weight Units", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["WValue", "Weight Value", "OE.PAT.wt.in.oz", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"]],
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
			["IPOS", "Inpatient (POS Code)", "21", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["OPOS", "Outpatient (POS Code)", "22", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["ERPOS", "Emergency Room (POS Code)", "23", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmokSts", "Smoking Status (references standard codes)", "GEN.SMKST", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmrCareDocComplFlg", "Flag that the Summary of care document was completed", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["SmrCareDocSentE", "Flag that the Summary of care document was sent electronically to another provider", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"]],

			// TEST
			["NDE1", "New Data Element 1", "Loc.1.1.1", "StandardCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE2", "New Data Element 2", "Loc.1.1.2", "StandardCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE3", "New Data Element 3", "Loc.1.1.3", "StandardCode", ["NM3","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE4", "New Data Element 4", "Loc.1.1.4", "StandardCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE5", "New Data Element 5", "Loc.1.1.5", "StandardCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE6", "New Data Element 6", "Loc.1.1.6", "StandardCode", ["NM1","NM3"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE7", "New Data Element 7", "Loc.1.1.7", "StandardCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE8", "New Data Element 8", "Loc.1.1.8", "StandardCode", ["NM4","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE9", "New Data Element 9", "Loc.1.1.9", "StandardCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"]],
			["NDE0", "New Data Element 0", "Loc.1.1.0", "StandardCode", ["NM4"],["MEDITECH 6.0", "MEDITECH 6.1"]]
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
