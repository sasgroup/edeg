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
			["Community Hospital",										"MEDITECH 6.0",""],
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
			["MU1",	"Meaningful Use Solution 2014 Stage 1",	
				"Meaningful Use was created to help improve the overall quality of patient care in the U.S. This is why reporting is so challenging; because you can't improve quality of care without improving the way in which care is delivered.", 
				"IHM's new Meaningful Use Solution will not only provide hospitals with an accurate, Drummond-certified report but will combine retrospective data with daily, near real-time information while patients are still in the bed.  This information is critical to helping hospitals go beyond MU and regulatory requirements to get the actionable information needed to improve processes, care and outcomes.  Additional benefits of IHM's New Meaningful Use Solution include:<ul><li>Extensive data validation tools that:<ul><li>enable hospitals to drill down from a summary view all the way to patient level detail</li><li>highlight cases contained in numerator, denominator, and population</li><li>deliver specific inclusion and exclusion criteria to help clinicians understand how the MU requirements differ  from data generated by chart abstraction</li><li>provide detailed information about variations in the quality of data entry as they directly relate to performance</li><li>deliver tools that not only measure performance for reporting, but assist hospitals in the identification of data structure or entry errors that are otherwise invisible</li></ul></li><li>SaaS-based to minimize IT staff time; no need to hire a SQL programmer to write reports; no need for IT staff to manage interfaces, upgrades and hardware</li><li>All  storage of reports and individual patient data maintained by IHM</li><li>Two options for reporting directly from the operating system to CMS (one with aggregate data and the other with patient specific data reporting)</li><li>All  storage of reports and individual patient data maintained by IHM</li><li>Reporting for all stages of Meaningful Use included in solution price</li></ul>"],
			["MU2",	"Meaningful Use Solution 2014 Stage 2",	
				"Meaningful Use was created to help improve the overall quality of patient care in the U.S. This is why reporting is so challenging; because you can't improve quality of care without improving the way in which care is delivered.", 
				"IHM's new Meaningful Use Solution will not only provide hospitals with an accurate, Drummond-certified report but will combine retrospective data with daily, near real-time information while patients are still in the bed.  This information is critical to helping hospitals go beyond MU and regulatory requirements to get the actionable information needed to improve processes, care and outcomes.  Additional benefits of IHM's New Meaningful Use Solution include:<ul><li>Extensive data validation tools that:<ul><li>enable hospitals to drill down from a summary view all the way to patient level detail</li><li>highlight cases contained in numerator, denominator, and population</li><li>deliver specific inclusion and exclusion criteria to help clinicians understand how the MU requirements differ  from data generated by chart abstraction</li><li>provide detailed information about variations in the quality of data entry as they directly relate to performance</li><li>deliver tools that not only measure performance for reporting, but assist hospitals in the identification of data structure or entry errors that are otherwise invisible</li></ul></li><li>SaaS-based to minimize IT staff time; no need to hire a SQL programmer to write reports; no need for IT staff to manage interfaces, upgrades and hardware</li><li>All  storage of reports and individual patient data maintained by IHM</li><li>Two options for reporting directly from the operating system to CMS (one with aggregate data and the other with patient specific data reporting)</li><li>All  storage of reports and individual patient data maintained by IHM</li><li>Reporting for all stages of Meaningful Use included in solution price</li></ul>"],
			["IA",	"Infection Alert",
				"", 
				""],
			["QA",	"Quality Alert",
				"", 
				""],
			
			// TEST
			["NP1",	"New Product 1",							
				"Notes for New Product 1",
				"<b>Help</b> <i> for New Product 1</i>"],
			["NP2",	"New Product 2",
				"Notes for New Product 2",
				"<b>Help</b> <i> for New Product 2</i>"]
			]
		for(_p in _products){
			def _product = new Product(code:_p[0], name:_p[1], notes:_p[2], help:_p[3])
			if (!_product.save()){
				_product.errors.allErrors.each{error ->
					println "An error occured with event1: ${error}"
				}
			}
		}

		//-----------MEASUREs-----------
		def _measures = [
		//	[CODE, NAME, NOTES, HELP, CATEGORY, CQM, PRODUCTS:[] ],
			["CPOE", "CPOE", 
				"This measure reflects the percentage of Medication, Laboratory and Radiology ordered entered into the CEHR by a qualified professional using CPOE (each type of order is measured separately).\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"<ul><li>More than 60 percent of medication orders created by the EP or authorized providers of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period are recorded using CPOE.</li><li>More than 30 percent of laboratory orders created by the EP or authorized providers of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period are recorded using CPOE.</li><li>More than 30 percent of radiology orders created by the EP or authorized providers of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period are recorded using CPOE.</li></ul><br><i>All three thresholds must be exceeded in order for the hospital to demonstrate compliance with the measure</i><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">From Federal Register/Vol.77, No 171: P53988</a>", 
				"CORE", "", ["MU1"]],
			["Demograph", 
				"Demographics", 
				"This measure reflects the percentage of patients who have demographic information recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 80 percent of all unique patients seen by the EP or admitted to the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period have demographics recorded as structured data.<br><br>Note: For <b>Race</b> and <b>Ethnicity</b> the Core Measure asks for it to be structured and the CQM measures ask that Race and Ethnicity be codified.<br><br>To satisfy both the hospital would need to provide two answers to each responses, one for the core measure and one for the CQM.<br><br>However, if the hospital would like to build in the CQM Value Set codes and request that the product use those to identify Race and Ethnicity, the product can do that.<br>Please refer to the individual CQM value sets (available on all CQM measure pages) for the specific codes.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 53993</a>", 
				"CORE", "", ["MU1","MU2"]],
			["Prob List", "Problem List", 
				"", 
				"", 
				"CORE", "", ["MU1"]],
			["Med List", "Medication List", 
				"", 
				"", 
				"CORE", "", ["MU1"]],
			["MedAllerg", "Medication Allergy", 
				"", 
				"", 
				"CORE", "", ["MU1"]],
			["Vitals", 
				"Vitals", 
				"This measure reflects the percentage of unique patients who have appropriate vital sign information recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 80 percent of all unique or admitted to the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period have blood pressure (for patients age 3 and over only) and height/length and weight (for all ages) recorded as structured data.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 53994</a>",
				"CORE", "", ["MU1","MU2"]],
			["Smoking", 
				"Smoking", 
				"This measure reflects the percentage of patients age 13 or older who have Smoking Status recorded as structured data.", 
				"More than 80 percent of all unique patients 13 years old or older seen by the EP or admitted to the eligible hospital's or CAH's inpatient or emergency departments (POS 21 or 23) during the EHR reporting period have smoking status recorded as structured data.",
				"CORE", "", ["MU1","MU2"]],
			["Adv Dir", 
				"Advance Directive", 
				"This measure reflects the percentage of unique patients age 65 and older who have advance directive status recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 50 percent of all unique patients 65 years old or older admitted to the eligible hospital's or CAH's inpatient department (POS 21) during the EHR reporting period have an indication of an advance directive status recorded as structured data.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54041</a>",
				"MENU", "", ["MU1","MU2"]],
			["Lab Tests (Menu)", "Laboratory Test Results", 
				"", 
				"", 
				"MENU", "", ["MU1"]],
			["EDU Res (Menu)", "Education Resources", 
				"", 
				"", 
				"MENU", "", ["MU1"]],
			["Med Rec (Menu)", "Medication Reconciliation", 
				"", 
				"", 
				"MENU", "", ["MU1"]],
			["Trans Sum", "Transition Summary", 
				"", 
				"", 
				"MENU", "", ["MU1"]],
			["CPOE-Med", "CPOE - Medication Orders", 
				"", 
				"", 
				"CORE", "", ["MU2"]],
			["CPOE-Lab", "CPOE - Laboratory Orders", 
				"", 
				"", 
				"CORE", "", ["MU2"]],
			["CPOE-Rad", "CPOE - Radiology Orders", 
				"", 
				"", 
				"CORE", "", ["MU2"]],
			["Lab Tests (Core)", 
				"Laboratory Test Results", 
				"This measure reflects the percentage of lab test results (whose results are positive/negative or numerical) recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 55 percent of all clinical lab tests results ordered by the EP or by authorized providers of the eligible hospital or CAH for patients admitted to its inpatient or emergency department (POS 21 or 23) during the EHR reporting period whose results are either in a positive/negative or numerical format are  incorporated in CEHRT as structured data.<br><br>Note: This measure allows the facility to chose the population for this measure.  The hospital should identify the list of lab tests which they expect to have a positive/negative result 100% of the time AND a list of lab tests that is expected to have a numeric value 100% of the time.<br><br>The MUS product will look for the hospital specified tests and then check to see that they have the correct results recorded in the electronic medical record.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54004</a>",
				"CORE", "", ["MU2"]],
			["EDU Res (Core)", 
				"Education Resources", 
				"This measure reflects the percentage of unique patients who are provided patient-specific educational resources identified by the hospital's CEHRT.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 10 percent of all unique patients admitted to the eligible hospital's or CAH's inpatient or emergency departments (POS 21 or 23) are provided patient-specific education resources identified by Certified EHR Technology.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54012</a>",
				"CORE", "", ["MU2"]],
			["Med Rec (Core)", 
				"Medication Reconciliation", 
				"This measure reflects the percentage of transitions of care where medication reconciliation was performed.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"The EP, eligible hospital or CAH performs medication reconciliation for more than 50 percent of transitions of care in which the patient is transitioned into the care of <br>the EP or admitted to the eligible hospital's or CAH's inpatient or <br>emergency department (POS 21 or 23).<br><br>Please list any additional codes, NUBC number and mnemonic for any other admit sources which signify that a patient was transferred INTO your facility that are not already listed.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54013</a>",
				"CORE", "", ["MU2"]],
			["SumOfCare", 
				"Summary of Care", 
				"This measure reflects the percentage of transitions of care/referrals  where a summary of care record is provided to the recieving provider/hospital.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"The EP, eligible hospital or CAH that transitions or refers their patient to another setting of care or provider of care provides a summary of care record for more than 50 percent of transitions of care and referrals.<br><br>For this indicator the population consists of those patients transferred from the hospital to another facility. The discharge codes enable this.<br><br>Please identify all the codes that your facility uses. The standard codes listed here are the codes which are referenced in the Federal Register.  Space has been provided to list any additional codes in use by the hospital.<br><br><b>IMPORTANT NOTE:</b> This indicator now contains the  Problem List, Med List and Med Allergy Listing from the Stage 1 indicators.  Furthermore, the regulations identify the components of a Summary of Care: Patient Name, <br>Procedures, <br>Encounter diagnosis, <br>Immunizations, <br>Laboratory test results, <br>Vital Signs - Height, <br>Vital Signs - Weight, <br>Vital Signs - Blood Pressure, <br>Vital Signs - BMI, <br>Smoking Status, <br>Functional Status - ADL, <br>Functional Status - Cognitive Status, <br>Functional Status - Disability Status, <br>Demographic Information - Preferred Language, <br>Demographic Information - Sex, <br>Demographic Information - Race, <br>Demographic Information - Ethnicity, <br>Demographic Information - Date of Birth, <br>Care Plan Field - Goals, <br>Care Plan Field - Instructions, <br>Care Team - Primary Care Provider, <br>Care Team - Any Additional Team Members, <br>Discharge Instructions, <br>*Current Problem List, <br>Current Medication List, <br>Current Medication Allergy List.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54016</a>",
				"CORE", "", ["MU2"]],
			["eSumOfCare", "Summary of Care: Electronic Transmission", 
				"",
				"", 
				"CORE", "", ["MU2"]],
			["eMAR", 
				"eMAR", 
				"This measure reflects the percentage of medication orders tracked from order through administration using electronic medication administration record (eMAR).\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 10 percent of medication orders created by authorized providers of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period are tracked using eMAR.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54033</a>",
				"CORE", "", ["MU2"]],
			["eNote", 
				"Electronic Note", 
				"This measure reflects the percentage of unique patients who have at least one electronic progress note created, edited and signed by an authorized provider.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"Enter at least one electronic progress note created, edited and signed by an authorized provider of the eligible hospital's or CAH's inpatient or emergency department (POS 21 or 23) for more than 30 percent of unique patients admitted to the eligible hospital or CAH's inpatient or emergency department during the EHR reporting period.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54027</a>",
				"MENU", "", ["MU2"]],
			["ImageResults", 
				"Imaging Results", 
				"This measure reflects the percentage of scans and tests whose result is at least one image whose results are accessible via CEHRT.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 10 percent of all scans and tests whose result is one or more images ordered by the EP or by an authorized provider of the eligible hospital or CAH for patients admitted to its inpatient or emergency department (POS 21 or 23) during the EHR reporting period are accessible through CEHRT.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54044</a>",
				"MENU", "", ["MU2"]],
			["Fam Hx", 
				"Family Health History", 
				"This measure reflects the percentage of unique patients who have first-degree relative health history recorded as structured data.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).",
				"More than 20 percent of all unique patients seen by the EP, or admitted to the eligible hospital or CAH's inpatient or emergency department (POS 21 or 23) during the EHR reporting period have a structured data entry for one or more first-degree relatives.<br><i>(First degree relatives include parents, offspring, and siblings)</i><br><br>The example in blue is a guideline of the minimal data set that is present on one of the government sites that are referred to within the discussion of this measure in the Federal Register. However, your facility can structure the family history information in any way that you believe is best for your organization. There is currently no available codified standard, that is a single 'right' way to do it according to CMS.<br><br>This measure requests only structured data and therefore, your questions needs to have multiple choice answers in order to be considered 'structured'. Narrative fields do not count as structured data. <br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54029</a><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.hhs.gov/familyhistory/portrait/portraiteng.pdf\">Implementation reference</a>",
				"MENU", "", ["MU2"]],
			["eRx", 
				"Electronic Prescriptions", 
				"This measure reflects the percentage of discharge medication orders elenctronically transmitted to a pharmacy using CEHRT.\n\nIn order for ihm to assist in reporting this measure, we require the following pieces of data (DPM/Mnemonic/Query/Location).", 
				"More than 10 percent of hospital discharge medication orders for permissible prescriptions (for new, changed, and refilled prescriptions) are queried for a drug formulary and transmitted electronically using CEHRT.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://www.gpo.gov/fdsys/pkg/FR-2012-09-04/pdf/2012-21050.pdf\">Federal Register, Vol.77, No. 171, Tuesday, Sept 4 2012; Page 54036</a>",
				"MENU", "", ["MU2"]],
			
			["Amb Labs", "Lab Test Results to Ambulatory Providers", 
				"", 
				"", 
				"MENU", "", ["MU2"]],
			["Amb Labs Alt", "Lab Test Results to Ambulatory Providers (Alternative)", 
				"", 
				"", 
				"MENU", "", ["MU2"]],
			
			["ED-1", "ED-1: ED Arrival to Departure-Admitted Patients", 
				"Median time from emergency department arrival to time of departure from the emergency room for patients admitted to the facility from the emergency department.", 
				"<a rel=\"nofollow\" target=\"_blank\" href=\"http://www.lantanagroup.com/especnavigator/#measure/8a4d92b2-3887-5df3-0139-11b262260a92\">From eSpec Navigator</a>", 
				"CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-2", "ED-2: Admit Decision to ED Departure-Admitted Patients", 
				"Median time (in minutes) from admit decision time to time of departure from the emergency department for emergency department patients admitted to inpatient status.", 
				"<a rel=\"nofollow\" target=\"_blank\" href=\"http://www.lantanagroup.com/especnavigator/#measure/8a4d92b2-3887-5df3-0139-11b262260a92\">From eSpec Navigator</a>", 
				"CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["ED-3", "ED-3: ED Arrival to ED Departure-Discharged Patients", 
				"Median time from emergency department arrival to time of departure from the emergency room for patients discharged from the emergency department.", 
				"<a rel=\"nofollow\" target=\"_blank\" href=\"http://www.lantanagroup.com/especnavigator/#measure/8a4d92b2-3887-5df3-0139-11b262260a92\">From eSpec Navigator</a>", 
				"CQM", "Care Coordination", ["MU1","MU2"]],
			["STK-2", "STK-2: Discharged on Antithrombotic Therapy", 
				"Ischemic stroke patients prescribed antithrombotic therapy at hospital discharge.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a principal diagnosis code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Ischemic stroke patients<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients admitted for elective carotid intervention<br>Patients discharged to another hospital<br>Patients who left against medical advice<br>Patients who expired<br>Patients discharged to home for hospice care<br>Patients discharged to a health care facility for hospice care<br><br><br><b>Numerator</b><br>Ischemic stroke patients prescribed antithrombotic therapy at hospital discharge.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-3", "STK-3: Anticoagulation for Atrial Fibrillation/Flutter", 
				"Ischemic stroke patients with atrial fibrillation/flutter who are prescribed anticoagulation therapy at hospital discharge.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a Principal Diagnosis Code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Ischemic stroke patients with documented atrial fibrillation/flutter<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients admitted for elective carotid intervention<br>Patients discharged to another hospital<br>Patients who left against medical advice<br>Patients who expired<br>Patients discharged to home for hospice care<br>Patients discharged to a health care facility for hospice care<br><br><br><b>Numerator</b><br>Ischemic stroke patients prescribed anticoagulation therapy at hospital discharge<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-4", "STK-4: Thrombolytic Therapy", 
				"Acute ischemic stroke patients who arrive at this hospital within 2 hours of time last known well and for whom IV t-PA was initiated at this hospital within 3 hours of time last known well.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a Principal Diagnosis Code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Acute ischemic stroke patients whose time of arrival is within 2 hours (less than or equal to 120 minutes) of time last known well.<br><br><b>Denominator Exclusions</b><br>Patients admitted for Elective Carotid Intervention, or<br>Patients with a documented Reason For Not Initiating IV Thrombolytic<br><br><b>Numerator</b><br>Acute ischemic stroke patients for whom IV thrombolytic therapy was initiated at this hospital within 3 hours (less than or equal to 180 minutes) of time last known well.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-5", "STK-5: Antithrombotic By End of Hospital Day 2", 
				"Ischemic stroke patients administered antithrombotic therapy by the end of hospital day 2.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a Principal Diagnosis Code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Ischemic stroke patients<br><br><b>Denominator Exclusions</b><br>Patients who have a duration of stay less than 2 days<br>Patients with comfort measures only documented on day or the day after arrival<br>Patients admitted for elective carotid intervention<br>Patients discharged prior to the end of hospital day 2<br>Patients with IV OR IA Thrombolytic (t-PA) Therapy administered at this hospital or within 24 hours prior to arrival<br><br><b>Numerator</b><br>Ischemic stroke patients who had antithrombotic therapy administered by end of hospital day 2.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-6", "STK-6: Discharged on Statin Medication", 
				"Ischemic stroke patients with LDL greater than or equal to 100 mg/dL, or LDL not measured, or who were on a lipid-lowering medication prior to hospital arrival are prescribed statin medication at hospital discharge.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a principal diagnosis code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Ischemic stroke patients with an LDL greater than or equal to 100 mg/dL, OR LDL not measured, OR who were on a lipid-lowering medication prior to hospital arrival.<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients admitted for elective carotid intervention<br>Patients discharged to another hospital<br>Patients who left against medical advice<br>Patients who expired<br>Patients discharged to home for hospice care<br>Patients discharged to a health care facility for hospice care<br><br><b>Numerator</b><br>Ischemic stroke patients prescribed statin medication at hospital discharge.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["STK-8", "STK-8: Stroke Education", 
				"Ischemic or hemorrhagic stroke patients or their caregivers who were given educational materials during the hospital stay addressing all of the following: activation of emergency medical system, need for follow-up after discharge, medications prescribed at discharge, risk factors for stroke, and warning signs and symptoms of stroke.", 
				"<b>Initial Patient Population</b><br>Ischemic stroke or hemorrhagic stroke patients discharged home.<br><br><b>Denominator</b><br>Ischemic stroke or hemorrhagic stroke patients discharged home.<br><br><b>Denominator Exclusions</b><br>Patients less than 18 years of age<br>Patients who have a length of stay greater than 120 days<br>Patients with comfort measures only documented<br>Patients admitted for elective carotid intervention<br><br><b>Numerator</b><br>Ischemic or hemorrhagic stroke patients with documentation that they or their caregivers were given educational material addressing all of the following:<ol><li>Activation of emergency medical system</li><li>Follow-up after discharge</li><li>Medications prescribed at discharge</li><li>Risk factors for stroke</li><li>Warning signs and symptoms of stroke.</li></ol><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["STK-10", "STK-10: Assessed for Rehabilitation", 
				"Ischemic or hemorrhagic stroke patients who were assessed for rehabilitation services.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with a principal diagnosis code for ischemic or hemorrhagic stroke with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Equals Initial Patient Population<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients admitted for elective carotid intervention<br>Patients discharged to another hospital<br>Patients who left against medical advice<br>Patients who expired<br>Patients discharged to home for hospice care<br>Patients discharged to a health care facility for hospice care<br><br><b>Numerator</b><br>Ischemic or hemorrhagic stroke patients assessed for or who received rehabilitation services.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Care Coordination", ["MU1","MU2"]],
			["VTE-1", "VTE-1: VTE Prophylaxis", 
				"This measure assesses the number of patients who received VTE prophylaxis or have documentation why no VTE prophylaxis was given the day of or the day after hospital admission or surgery end date for surgeries that start the day of or the day after hospital admission.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with no diagnosis of obstetrics or venous thromboembolism (VTE with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>All patients in the initial patient population<br><br><b>Denominator Exclusions</b><br>Patients who have a length of stay less than 2 days<br>Patients with comfort measures only documented by the day after hospital admission<br>Patients with comfort measures only documented by the day after surgery end date for surgeries that start the day of or the day after hospital admission<br>Patients who are direct admits to intensive care unit (ICU), or transferred to ICU the day of or the day after hospital admission with ICU length of stay greater than or equal to one day<br>Patients with a principal diagnosis of mental disorders or stroke<br>Patients with a principal procedure of Surgical Care Improvement Project (SCIP) VTE selected surgeries<br><br><b>Numerator</b><br>Patients who received VTE prophylaxis or have documentation why no VTE prophylaxis was given:<ul><li>the day of or the day after hospital admission</li><li>the day of or the day after surgery end date for surgeries that start the day of or the day after hospital admission</li></ul><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-2", "VTE-2:  ICU VTE Prophylaxis", 
				"This measure assesses the number of patients who received VTE prophylaxis or have documentation why no VTE prophylaxis was given the day of or the day after the initial admission (or transfer) to the Intensive Care Unit (ICU) or surgery end date for surgeries that start the day of or the day after ICU admission (or transfer).", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with no diagnosis of obstetrics or venous thromboembolism (VTE) with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Patients directly admitted or transferred to ICU during the hospitalization.<br><br><b>Denominator Exclusions</b><br>Patients who have a  hospital length of stay (LOS) less than two days.<br>Patients with comfort measures only documented during the specified date range.<br>Patients with a principal procedure of surgical care improvement Project (SCIP) VTE selected surgeries that start the day of or the day after ICU admission or transfer.<br><br><b>Numerator</b><br>Patients who received VTE prophylaxis or have documentation why no VTE prophylaxis was given:<ul><li>the day of or the day after ICU admission (or transfer)</li><li>the day of or the day after surgery end date for surgeries that start the day of or the day after ICU admission (or transfer)</li></ul><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["VTE-3", "VTE-3: VTE Patients with Anticoagulation Overlap", 
				"This measure assesses the number of patients diagnosed with confirmed VTE who received an overlap of parenteral (intravenous [IV] or subcutaneous [subcu]) anticoagulation and warfarin therapy. For patients who received less than five days of overlap therapy, they should be discharged on both medications or have a reason for discontinuation of overlap therapy. Overlap therapy should be administered for at least five days with an international normalized ratio (INR) greater than or equal to 2 prior to discontinuation of the parenteral anticoagulation therapy, discharged on both medications or have a reason for discontinuation of overlap therapy.", 
				"<b>Initial Patient Population</b><br>Patients with a diagnosis code for venous thromboembolism (VTE), a patient age greater than or equal to 18 years, and a length of stay less than or equal to 120 days.<br><br><b>Denominator</b><br>Patients with confirmed VTE who received warfarin.<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients discharged to a health care facility for hospice care<br>Patients discharged to home for hospice care<br>Patients who expired<br>Patients who left against medical advice<br>Patients discharged to another hospital<br>Patients without warfarin during hospitalization<br><br><b>Numerator</b><br>Patients who received overlap therapy (warfarin or parenteral anticoagulation):<ul><li>Five or more days, with an INR greater than or equal to 2 prior to discontinuation of parenteral therapy, or</li><li>Five or more days, with an INR less than 2 and discharged on overlap therapy, or</li><li>Less than five days and discharged on overlap therapy, or</li><li>With documentation of reason for discontinuation of overlap therapy, or</li><li>With documentation of a reason for no overlap therapy</li></ul><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-4", "VTE-4: VTE Patients Monitored by Protocol/Nomogram", 
				"This measure assesses the number of patients diagnosed with confirmed VTE who received intravenous (IV) UFH therapy dosages AND had their platelet counts monitored using defined parameters such as a nomogram or protocol.", 
				"<b>Initial Patient Population</b><br>Patients with a diagnosis of venous thromboembolism (VTE), a patient age greater than or equal to 18 years, and a length of stay less than or equal to 120 days.<br><br><b>Denominator</b><br>Patients with VTE confirmed through a diagnostic test and receiving IV UFH therapy<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients discharged to another hospital<br>Patients who left against medical advice<br>Patients who expired<br>Patients discharged to home for hospice care<br>Patients discharged to a health care facility for hospice care<br><br><b>Numerator</b><br>Patients who have their IV UFH therapy dosages AND platelet counts monitored according to defined parameters such as a nomogram or protocol.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["VTE-5", "VTE-5:  VTE Discharge Instructions", 
				"This measure assesses the number of patients diagnosed with confirmed VTE that are discharged to home, home care, court/law enforcement or home on hospice care on warfarin with written discharge instructions that address all four criteria: compliance issues, dietary advice, follow-up monitoring, and information about the potential for adverse drug reactions/interactions.", 
				"<b>Initial Patient Population</b><br>Patients with a diagnosis of venous thromboembolism (VTE), a patient age greater than or equal to 18 years, and a length of stay less than or equal to 120 days.<br><br><b>Denominator</b><br>Patients with VTE confirmed through a diagnostic test and discharged to home or court/law enforcement on warfarin therapy.<br><br><b>Denominator Exclusions</b><br>None<br><br><b>Numerator</b><br>Patients with documentation that they or their caregivers were given written discharge instructions or other educational material about warfarin that addressed all of the following:<ol><li>compliance issues</li><li>dietary advice</li><li>follow-up monitoring</li><li>potential for adverse drug reactions and interactions</li></ol><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["VTE-6", "VTE-6: Potentially-Preventable VTE", 
				"This measure assesses the number of patients diagnosed with confirmed VTE during hospitalization (not present at admission) who did not receive VTE prophylaxis between hospital admission and the day before the VTE diagnostic testing order date.", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care with no principal diagnosis code for venous thromboembolism (VTE), with at least one other diagnosis code for venous thromboembolism (VTE) with hospital stays <= 120 days during the measurement period for patients age 18 and older at the time of hospital admission.<br><br><b>Denominator</b><br>Patients who developed VTE confirmed by a diagnostic test during hospitalization.<br><br><b>Denominator Exclusions</b><br>Patients with comfort measures only documented<br>Patients with a principal diagnosis of VTE<br>Patients with VTE present at admission<br>Patients with reasons for not administering mechanical and pharmacologic prophylaxis<br><br><b>Numerator</b><br>Patients who received no VTE prophylaxis prior to the VTE diagnostic test order date.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["AMI-2", "AMI-2: Aspirin at Discharge", 
				"Acute myocardial infarction (AMI) patients who are prescribed aspirin at hospital discharge", 
				"<b>Initial Patient Population</b><br>All hospital discharges for acute myocardial infarction (AMI) with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission<br><br><b>Denominator</b><br>All Acute Myocardial Infarctions patients age 18 and older with an ICD-9-CM Principal Diagnosis Code for Acute Myocardial Infarction<br><br><b>Denominator Exclusions</b><br>Patients with Comfort Measures Only documented.<br>Patients enrolled in clinical trials.<br>Patients discharged to another hospital.<br>Patients who left against medical advice.<br>Patients who expired.<br>Patients discharged to home for hospice care.<br>Patients discharged to a health care facility for hospice care.<br>Patients with a documented Reason for No Aspirin at Discharge<br><br><b>Numerator</b><br>Acute Myocardial Infarction patients who are prescribed aspirin at hospital discharge.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-7a", "AMI-7a:  Fibrinolytic Within 30 Minutes of Arrival", 
				"Acute myocardial infarction (AMI) patients with ST-segment elevation or LBBB on the ECG closest to arrival time receiving fibrinolytic therapy during the hospital stay and having a time from hospital arrival to fibrinolysis of 30 minutes or less", 
				"<b>Initial Patient Population</b><br>All hospital discharges for acute myocardial infarction (AMI) with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission with ST-elevation or left bundle branch block (LBBB) on electrocardiogram (ECG) who received fibrinolytic therapy.<br><br><b>Denominator</b><br>AMI patients age 18 and older with ST-elevation or LBBB on ECG who received fibrinolytic therapy with an ICD-9-CM Principal Diagnosis Code for AMI AND ST-segment elevation or LBBB on the ECG performed closest to hospital arrival AND Fibrinolytic therapy within 6 hours after hospital arrival AND Fibrinolytic therapy is primary reperfusion therapy.<br><br><b>Denominator Exclusions</b><br>Patients enrolled in clinical trials.<br>Patients received as a transfer from an inpatient or outpatient department of another hospital.<br>Patients received as a transfer from the emergency/observation department of another hospital.<br>Patients received as a transfer from an ambulatory surgery center.<br>Patients who did not receive fibrinolytic therapy within 30 minutes and had a documented reason for delay in fibrinolytic therapy.<br><br><b>Numerator</b><br>AMI patients whose time from hospital arrival to fibrinolysis is 30 minutes or less.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-8a", "AMI-8a:  Primary PCI Within 90 Minutes of Arrival", 
				"Acute myocardial infarction (AMI) patients with ST-segment elevation or LBBB on the ECG closest to arrival time receiving primary PCI during the hospital stay with a time from hospital arrival to PCI of 90 minutes or less.", 
				"<b>Initial Patient Population</b><br>All hospital discharges for acute myocardial infarction (AMI) with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission with ST-elevation or left bundle branch block (LBBB) on electrocardiogram (ECG) who received primary percutaneous coronary intervention (PCI)<br><br><b>Denominator</b><br>AMI patients age 18 and older with ST-elevation or LBBB on ECG who received primary PCI with an ICD-9-CM Principal Diagnosis Code for AMI AND PCI (ICD-9-CM Principal and Other Procedure Codes for PCI) AND ST-segment elevation or LBBB on the ECG performed closest to hospital arrival AND PCI performed within 24 hours after hospital arrival<br><br><b>Denominator Exclusions</b><br>Patients enrolled in clinical trials.<br>Patients received as a transfer from an inpatient or outpatient department of another hospital.<br>Patients received as a transfer from the emergency/observation department of another hospital.<br>Patients received as a transfer from an ambulatory surgery center.<br>Patients administered fibrinolytic agent prior to PCI<br>PCI described as non-primary by a physician/advanced practice nurse/physician assistant (physician/APN/PA). Patients who did not receive PCI within 90 minutes and had a documented reason for delay in PCI.<br><br><b>Numerator</b><br>AMI patients whose time from hospital arrival to primary PCI is 90 minutes or less<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["AMI-10", "AMI-10: Statin Prescribed at Discharge", 
				"Acute myocardial infarction (AMI) patients who are prescribed a statin at hospital discharge.", 
				"<b>Initial Patient Population</b><br>AMI patients > = 18 years of age with length of stay < = 120 days<br><br><b>Denominator</b><br>AMI patients.<br><br><b>Denominator Exclusions</b><ul><li>Patients with Comfort Measures Only documented</li><li>Patients enrolled in clinical trials</li><li>Patients discharged to another hospital</li><li>Patients who left against medical advice</li><li>Patients who expired</li><li>Patients discharged to home for hospice care</li><li>Patients discharged to a health care facility for hospice care</li></ul><br><br><b>Numerator</b><br>AMI patients who are prescribed a statin medication at hospital discharge.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["SCIP-1", "SCIP-INF-1: Abx Within 1 Hour Prior to Incision", 
				"Surgical patients with prophylactic antibiotics initiated within one hour prior to surgical incision. Patients who received vancomycin or a fluoroquinolone for prophylactic antibiotics should have the antibiotics initiated within two hours prior to surgical incision. Due to the longer infusion time required for vancomycin or a fluoroquinolone, it is acceptable to start these antibiotics within two hours prior to incision time.", 
				"<b>Initial Patient Population</b><br>All hospital discharges for selective surgery with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission with no evidence of prior infection<br><br><b>Denominator</b><br>All selected surgical patients 18 years of age and older with no evidence of prior infection with an ICD-9-CM Principal Procedure Code of selected surgeries.<br>Denominator for population 1 - Coronary artery bypass graft (CABG) procedures<br>Denominator for population 2 - Other cardiac surgery<br>Denominator for population 3 - Hip arthroplasty<br>Denominator for population 4 - Knee arthroplasty<br>Denominator for population 5 - Colon surgery<br>Denominator for population 6 - Abdominal hysterectomy<br>Denominator for population 7 - Vaginal hysterectomy<br>Denominator for population 8 - Vascular surgery<br><br><b>Denominator Exclusions</b><br><ul><li>Patients who had a hysterectomy and a caesarean section performed during this hospitalization</li><li>Patients who had a principal diagnosis suggestive of preoperative infectious diseases (as defined in Appendix A, Table 5.09 for ICD-9-CM codes)</li><li>Patients enrolled in clinical trials-this exclusion is limited to patients participating in a clinical trial for the same conditions as covered by the measure.  Other clinical trials are not valid reasons for exclusion.</li><li>Patients with physician/advanced practice nurse/physician assistant (physician/APN/PA) documented infection prior to surgical procedure of interest</li><li>Patients who had other procedures requiring general or spinal anesthesia that occurred within 3 days (4 days for CABG or Other Cardiac Surgery) prior to or after the procedure of interest (during separate surgical episodes) during this hospital stay</li><li>Patients who were receiving antibiotics more than 24 hours prior to surgery</li><li>Patients who were receiving antibiotics within 24 hours prior to arrival (except colon surgery patients taking oral prophylactic antibiotics)</li></ul><br><br><b>Numerator</b><br>Number of surgical patients with prophylactic antibiotics initiated within one hour prior to surgical incision (two hours if receiving vancomycin, in Appendix C, Table 3.8, or a fluoroquinolone, in Appendix C, Table 3.10).<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["SCIP-2", "SCIP-INF-2: Abx Selection for Surg Patients", 
				"Surgical patients who received prophylactic antibiotics consistent with current guidelines (specific to each type of surgical procedure).", 
				"<b>Initial Patient Population</b><br>All hospital discharges for selective surgery with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission with no evidence of prior infection.<br>The measure is divided into 8 populations by type of surgery. Each denominator provides criteria for the types of surgery.<br><br><b>Denominator</b><br>All selected surgical patients 18 years of age and older with no evidence of prior infection with an ICD-9-CM Hospital Measures-Principal Procedure Code of selected surgeries.<br>Denominator for population 1 - Coronary artery bypass graft (CABG) procedures<br>Denominator for population 2 - Other cardiac surgery<br>Denominator for population 3 - Hip arthroplasty<br>Denominator for population 4 - Knee arthroplasty<br>Denominator for population 5 - Colon surgery<br>Denominator for population 6 - Abdominal hysterectomy<br>Denominator for population 7 - Vaginal hysterectomy<br>Denominator for population 8 - Vascular surgery<br><br><b>Denominator Exclusions</b><br>Patients who had a Hospital Measures-Principal diagnosis suggestive of preoperative infectious diseases.<br>Patients enrolled in clinical trials-this exclusion is limited to patients participating in a clinical trial for the same conditions as covered by the measure.  Other clinical trials are not valid reasons for exclusion.<br>Patients with physician/advanced practice nurse/physician assistant (physician/APN/PA) documented infection prior to surgical procedure of interest.<br>Patients who expired perioperatively<br>Patients who were receiving antibiotics more than 24 hours prior to surgery (except colon surgery patients taking oral prophylactic antibiotics).<br>Patients who had other procedures requiring general or spinal anesthesia that occurred within 3 days (4 days for CABG or Other Cardiac Surgery) prior to or after the procedure of interest (during separate surgical episodes) during this hospital stay<br>Patients who were receiving antibiotics within 24 hours prior to arrival (except colon surgery patients taking oral prophylactic antibiotics).<br>Patients who did not receive any antibiotics before or during surgery, or within 24 hours after Anesthesia End Time (i.e., patient did not receive prophylactic antibiotics).<br>Patients who did not receive any antibiotics during this hospitalization.<br><br><b>Numerator</b><br>Number of surgical patients who received prophylactic antibiotics recommended for their specific surgical procedure.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["SCIP-9", "SCIP-INF-9: Urinary catheter removed on POD 1 or 2", 
				"Surgical patients with urinary catheter removed on Postoperative Day 1 or Postoperative Day 2 with day of surgery being day zero.", 
				"<b>Initial Patient Population</b><br>All hospital discharges for selective surgery with hospital stays <= 120 days during the measurement year for patients age 18 and older at the time of hospital admission with a catheter in place postoperatively.<br><br><b>Denominator</b><br>All selected surgical patients 18 years of age and older with a catheter in place postoperatively with An ICD-9-CM Principal Procedure Code of selected surgeries.<br><br><b>Denominator Exclusions</b><br>Patients enrolled in clinical trials.<br>Patients who had a urological, gynecological or perineal procedure performed.<br>Patients who expired perioperatively.<br>Patients whose length of stay was less than two days postoperatively.<br>Patients who had a urinary diversion or a urethral catheter or were being intermittently catheterized prior to hospital arrival.<br>Patients who did not have a catheter in place postoperatively.<br>Patients who had physician/APN/PA documentation of a reason for not removing the urinary catheter postoperatively.<br><br><b>Numerator</b><br>Number of surgical patients whose urinary catheter is removed on postoperative day (POD) 1 or postoperative day (POD) 2 with day of surgery being day zero.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["PC-01", "PC-01: Elective Delivery", 
				"Patients with elective vaginal deliveries or elective cesarean sections at >= 37 and < 39 weeks of gestation completed", 
				"<b>Initial Patient Population</b><br>Patients admitted to the hospital for inpatient acute care are included if they have a diagnosis related to pregnancy with reference to delivery, a patient age >= 8 years and < 65, and a length of stay < 120 days.<br><br><b>Denominator</b><br>Patients delivering newborns with >= 37 and < 39 weeks of gestation completed<br><br><b>Denominator Exclusions</b><br>Patients who experienced a prior uterine surgery (classical cesarean section or myomectomy)<br>Patients with conditions possibly justifying elective delivery prior to 39 weeks gestation<br><br><b>Numerator</b><br>Patients with elective deliveries<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["PC-05", "PC-05: Exclusive Breast Milk Feeding ", 
				"Exclusive breast milk feeding during the newborn's entire hospitalization.", 
				"<b>Initial Patient Population</b><br>Single term newborns discharged from the hospital who has no diagnosis of galactosemia, no performed procedure of parenteral infusion, no diagnosis of premature newborn, and length of stay less than or equal to 120 days.<br><br><b>Denominator</b><br>PC-05 Single term newborns discharged from the hospital who has no diagnosis of galactosemia, no procedure of parenteral infusion, no diagnosis of premature newborn, and length of stay less than or equal to 120 days.<br>PC-05a Single term newborns discharged from the hospital excluding those whose mothers chose not to breast feed, who has no diagnosis of galactosemia, no procedure of parenteral infusion, no diagnosis of premature newborn, and length of stay less than or equal to 120 days.<br><br><b>Denominator Exclusions</b><br>PC-05 Admitted to the Neonatal Intensive Care Unit (NICU) at this hospital during the hospitalization; Experienced death; Had documented reason for not exclusively feeding breast milk; Patients transferred to another hospital.<br>PC-05a Admitted to the Neonatal Intensive Care Unit (NICU) at this hospital during the hospitalization; Experienced death; Had documented reason for not exclusively feeding breast milk; Patients transferred to another hospital; Parental Refusal.<br><br><b>Numerator</b><br>PC-05 Newborns that were fed breast milk only since birth<br>PC-05a Newborns that were fed breast milk only since birth<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["NQF-0716", "NQF-0716: Healthy Term Newborn", 
				"Percent of term singleton live births (excluding those with diagnoses originating in the fetal period) who DO NOT have significant complications during birth or the nursery care.", 
				"<b>Initial Patient Population</b><br>All patients who are single liveborn term newborns born in a hosptital.<br><br><b>Denominator</b><br>The denominator is composed of singleton, term (>=37 weeks), inborn, livebirths in their birth admission. The denominator further has eliminated fetal conditions likely to be present before labor. Maternal and obstetrical conditions (e.g. hypertension, prior cesarean, malpresentation) are not excluded unless evidence of fetal effect prior to labor (e.g. IUGR/SGA).<br><br><b>Denominator Exclusions</b><br>Denominator exclusions: multiple gestations, preterm, congenital anomalies or fetuses affected by selected maternal conditions.<br><br><b>Numerator</b><br>The absence of conditions or procedures reflecting morbidity that happened during birth and nursery care to an otherwise normal infant.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient Safety", ["MU1","MU2"]],
			["EHDI-1a", "EHDI-1a: Hearing Screening Prior To Discharge", 
				"This measure assesses the proportion of births that have been screened for hearing loss before hospital discharge.", 
				"<b>Initial Patient Population</b><br>Newborn<br><br><b>Denominator</b><br>All live births during the measurement time period born at a facility and, discharged without being screened, or screened prior to discharge, or screened but still not discharged.<br><br><b>Denominator Exclusions</b><br>Patient deceased prior to discharge and has not received hearing screening.<br><br><b>Numerator</b><br>All live births during the measurement time period born at a facility and screened for<br>hearing loss prior to discharge, or screened but still not discharged;<br>Or not being screened due to medical reasons or medical exclusions.<br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Clinical Process / Effectiveness", ["MU1","MU2"]],
			["CAC-3", "CAC-3: Home Management Plan of Care Given to Patient", 
				"An assessment that there is documentation in the medical record that a Home Management Plan of Care (HMPC) document was given to the pediatric asthma patient/caregiver.", 
				"<b>Initial Patient Population</b><br>Pediatric asthma inpatients with an age of 2 through 17 years, and length of stay less than or equal to 120 days.<br><br><b>Denominator</b><br>Pediatric asthma inpatients with an age of 2 through 17 years, length of stay less than or equal to 120 days, and discharged to home or police custody.<br><br><b>Denominator Exclusions</b><br>None<br><br><b>Numerator</b><br>Pediatric asthma inpatients with documentation that they or their caregivers were given a written Home Management Plan of Care (HMPC) document that addresses all of the following:<ol><li>Arrangements for follow-up care</li><li>Environmental control and control of other triggers</li><li>Method and timing of rescue actions</li><li>Use of controllers</li><li>Use of relievers</li></ol><br><br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Patient and Family Engagement", ["MU1","MU2"]],
			["PN-6a", "PN-6a:  Initial Antibiotic Selection for CAP-ICU", 
				"Immunocompetent patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines (Population 1) Immunocompetent ICU patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines (Population 2) Immunocompetent non-Intensive Care Unit (ICU) patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines", 
				"<br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			["PN-6b", "PN-6b:  Initial Antibiotic Selection for CAP-Non ICU", 
				"Immunocompetent patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines (Population 1) Immunocompetent ICU patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines (Population 2) Immunocompetent non-Intensive Care Unit (ICU) patients with Community-Acquired Pneumonia who receive an initial antibiotic regimen during the first 24 hours that is consistent with current guidelines", 
				"<br><a rel=\"nofollow\" target=\"_blank\" href=\"http://lantanagroup.com/especnavigator2013/#home\">From eSpec Navigator</a>", 
				"CQM", "Efficient Use of Healthcare Resources", ["MU1","MU2"]],
			
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
			["AdmsDate", 	"Admission Date", 					"ADM.PAT.admit.date", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["AdmsTime", 	"Admission Time", 					"ADM.PAT.admit.time", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DxDate", 		"Discharge Date", 					"ADM.PAT.discharge.date", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DxTime", 		"Discharge Time",					"ADM.PAT.discharge.time", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["PMRNum", 		"Patient Medical Record number", 	"ABS.PAT.unit.number", 		"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["PENum", 		"Patient Encounter Number", 		"ABS.PAT.account.number", 	"StandardCode", ["MEDITECH 6.0", "MEDITECH 6.1"], "", ""]
	    ]
		for(_el in _elementsAllMeasures){
			def _element = new DataElement(code:_el[0], name:_el[1], notes:_el[5], help:_el[6])

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
				def dataElementDefaults = new DataElementDefaults(location:_el[2], valueType:_el[3], dataElement:_element, ehr:Ehr.findByCode(_ehr))
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
			["AdmsODate", "Admission Order Date", "OE.ORD.order.date", "StandardCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["AdmsOTime", "Admission Order Time", "OE.ORD.order.time", "StandardCode", ["ED-1", "ED-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["AdmsSrc", "Admission Source", "ADM.PAT.admit.source", "StandardCode", ["Med Rec (Menu)","Med Rec (Core)", "PN-6a", "PN-6b"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["AdvDirSts", "Advance Directive Status", "GEN.AD", "StandardCode", ["Adv Dir"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["ArrDate", "Arrival Date", "ABS.PAT.arrival.date", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["ArrTime", "Arrival Time", "ABS.PAT.arrival.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["BirthDate", "Birth Date", "ABS.PAT.birthdate", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["BloodPress", "Blood Pressure Value - Ratio", "VS.BP", "StandardCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DthCause", "Death Cause", "ADM.PAT.ccdqr.response", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DthDate", "Death Date", "ADM.PAT.discharge.date", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DthTime", "Death Time", "ADM.PAT.discharge.time", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DepDate", "Departure Date from ED", "ABS.PAT.er.depart.date", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DepTime", "Departure Time from ED", "ABS.PAT.er.depart.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["DiagCode", "Diagnosis Code", "ABS.PAT.dx", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["EdDecision", "ED Decision to Admit to Inpatient Time", "ABS.PAT.decision.to.admit.time", "StandardCode", ["ED-1", "ED-2", "ED-3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["Gndr", "Gender", "ABS.PAT.sex", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["HUnits", "Height Units", "", "HospitalSpecific", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please describe units of measurement for height (in, cm)"],
			["HValue", "Height Value", "OE.PAT.ht.in.cm", "StandardCode", ["Vitals"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabAddr", "Lab Address", "LAB.C.SITE.address.1", "StandardCode", ["CPOE", "Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabSpec", "Lab Specimen Unacceptable", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please specify query that identifies a specimen to be unacceptable for testing.<br /><br />Please identify how the flag is associated with the lab test."],
			["LabTestOrdCode", "Lab Test Order Code", "OE.ORD.procedure.mnemonic", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestCodeDesc", "Lab Test code description", "OE.PROC.name", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestCollDate", "Lab Test collected date", "LAB.L.SPEC.collection.date", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestOrdID", "Lab Test order ID", "OE.ORD.order.num", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestOrdBy", "Lab Test ordered by", "LAB.L.SPEC.subm.doctor", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestOrdTime", "Lab Test Ordered Time", "OE.ORD.order.time", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestResCode", "Lab Test Result Code", "LAB.L.TEST.mnemonic", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestResVal", "Lab Test Result Value", "LAB.L.SPEC.result", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestResRepDate", "Lab Test Result Report Date", "LAB.L.SPEC.test.result.date", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestResRepTime", "Lab Test Result Report Time", "LAB.L.SPEC.test.result.time", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestSpecSrc", "Lab Test Specimen Source", "LAB.L.SPEC.spec.source", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabTestUnits", "Lab Test Units", "LAB.L.SPEC.units", "StandardCode", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["MedAdmin", "Medication Administration", "", "NotApplicable", ["eMAR"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["OR-AED", "OR - Anesthesia end time date", "SCH.PAT.time.anes.2.date", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["OR-AET", "OR - Anesthesia end time ", "SCH.PAT.time.anes.2", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["OR-AST", "OR - Anesthesia start time", "SCH.PAT.time.anes.1", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["OR-ASD", "OR - Anesthesia start time date", "SCH.PAT.time.anes.1.date", "StandardCode", ["VTE-1", "VTE-2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["PrefLang", "Preferred Language", "ADM.PAT.language", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["RaceEthn", "Race/Ethnicity", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["WUnits", "Weight Units", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please describe units of measurement for weight (oz, lb, kg)"],
			["WValue", "Weight Value", "OE.PAT.wt.in.oz", "StandardCode", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["MedOrdCPOEflg", "Medication Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please describe in the last column how the order in CPOE can be associated with the medication", 
				"Please identify the type and location of the flag that signifies an order was placed into CPOE directly by the qualified provider.<br /><br />Please identify how the flag is associated with the medication"],
			["MedOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the Medication Order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please identify in last column with a 'yes' that these same identifiers can be used to find physician/NP/PA orders for Imaging Tests, If there is a difference for Imaging orders please identify in last column how that is different.", 
				"Please list the codes and positions that the code identifies, this list will be used to identify the 'qualified individuals' who can enter a Medication order into CPOE within your facility."],
			["LabOrdCPOEflg", "Laboratory Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please describe in the last column how the order in CPOE can be associated with the laboratory test.", 
				"Please identify the type and location of the flag that signifies an order was placed into CPOE directly by the qualified provider.<br /><br />Please identify how the flag is associated with the lab test"],
			["LabOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the Laboratory Order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please identify in last column with a 'yes' that these same identifiers can be used to find physician/NP/PA orders for Imaging Tests, If there is a difference for Imaging orders please identify in last column how that is different.", 
				"Please list the codes and positions that the code identifies, this list will be used to identify the 'qualified individuals' who can enter a Lab order into CPOE within your facility."],
			["RadOrdCPOEflg", "Radiology Ordered in CPOE Flag", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please describe in the last column how the order in CPOE can be associated with the radiology test.", 
				"Please identify the type and location of the flag that signifies an order was placed into CPOE directly by the qualified provider.<br /><br />Please identify how the flag is associated with the rad test"],
			["RadOrdIndCodes", "Code (s) which identifies the qualified individual who wrote the Radiology order", "", "HospitalSpecific", ["CPOE"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"Please identify in last column with a 'yes' that these same identifiers can be used to find physician/NP/PA orders for Imaging Tests, If there is a difference for Imaging orders please identify in last column how that is different.", 
				"Please list the codes and positions that the code identifies, this list will be used to identify the 'qualified individuals' who can enter a Radiology order into CPOE within your facility."],
			["LangCodes", "Language (632-9 Codes)", "", "HospitalSpecific", ["Demograph"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["EDQuery", "Education Query (multiple)", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabReqRec", "Flag or other identifier: Lab request received from Ambulatory Provider (Hospital specific flag/identifier)", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["LabResSentE", "Flag or other identifier: Lab results sent electronically to Ambulatory Providers (Hospital specific flag/identifier)", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please identify how the documentation occurs that the results were sent electronically."],
			["LabResRecE", "Flag or other identifier: Lab request received electronically from Ambulatory Provider (Hospital specific flag/identifier)", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please identify how the lab receives electronic requests for lab tests, and how that would be identified in the record."],
			["PatStatus4Lab", "Patient Status for Lab (Hospital specific flag/identifier)", "", "HospitalSpecific", ["Lab Tests (Menu)", "Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please identify the patient status(s) of a patient that only received a lab test"],
			["eProgressNote", "Electronic Progress note (Progress note format)", "", "HospitalSpecific", ["eNote"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please identify how the product should recognize a progress note"],
			["eNoteIndCodes", "Code (s) which identifies the qualified individual who wrote the order", "", "HospitalSpecific", ["eNote"],["MEDITECH 6.0", "MEDITECH 6.1"],
				"",
				"Please list the codes and positions that the code identifies, this list will be used to identify the 'qualified individuals' who can enter a progress note within your facility."],
			["DxPrescription", "Flag that a discharge prescription was electronically provided to another provider", "", "HospitalSpecific", ["eRx"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please describe how the flag relates to a specific medication.<br /><br />Please identify the categories of drugs that your facility would not 'permit' to be electronically sent to another provider (Please list all medications that are not to be sent electronically)."],
			["FamHX", "Family History (structured data recording first degree relative health history)", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["ImgRecNum", "Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed) : Are there image record numbers or identifiers that correspond to the text result?", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"If  stored in Meditech please provide the DPM/Query information.<br /><br />If stored in another system, please provide the location within that system and the name of that system."],
			["ImgRecFlg", "Explanation(results) that corresponds to an image (please answer both questions and add additional information if needed) : Is there a flag that signifies an image has been read and a report generated and placed in the record?", "", "HospitalSpecific", ["Fam Hx"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please explain how the images are accessed by the clinician or provider reviewing the record.<br /><br />If a link is used, please provide information regarding the link an how to recognize that link within the record."],
			["MedRecCompl", "Medication Reconciliation Completed", "", "HospitalSpecific", ["Med Rec (Menu)","Med Rec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"These particular indicators require the connection of non-related data elements which could even come from different sources or modules, please identify how we should correlate the separate data elements."],
			["MedRecComplDate", "Medication Reconciliation Completed Date", "", "HospitalSpecific", ["Med Rec (Menu)","Med Rec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"These particular indicators require the connection of non-related data elements which could even come from different sources or modules, please identify how we should correlate the separate data elements."],
			["MedRecComplTime", "Medication Reconciliation Completed Time", "", "HospitalSpecific", ["Med Rec (Menu)","Med Rec (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"These particular indicators require the connection of non-related data elements which could even come from different sources or modules, please identify how we should correlate the separate data elements."],
			["LabTestDesc", "Lab Test Description (multiple)", "", "HospitalSpecific", ["Lab Tests (Menu)","Lab Tests (Core)"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["IPOS", "Inpatient (POS Code)", "21", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["OPOS", "Outpatient (POS Code)", "22", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["ERPOS", "Emergency Room (POS Code)", "23", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["SmokSts", "Smoking Status (references standard codes)", "GEN.SMKST", "StandardCode", [],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["SmrCareDocComplFlg", "Flag that the Summary of care document was completed", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please specify query that identifies that a summary of care document was completed"],
			["SmrCareDocSentE", "Flag that the Summary of care document was sent electronically to another provider", "", "HospitalSpecific", [],["MEDITECH 6.0", "MEDITECH 6.1"], 
				"", 
				"Please specify query that identifies that a summary of care document was electronically sent to another provider"],

			// TEST
			["NDE1", "New Data Element 1", "Loc.1.1.1", "StandardCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE2", "New Data Element 2", "Loc.1.1.2", "StandardCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE3", "New Data Element 3", "Loc.1.1.3", "StandardCode", ["NM3","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE4", "New Data Element 4", "Loc.1.1.4", "StandardCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE5", "New Data Element 5", "Loc.1.1.5", "StandardCode", ["NM1","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE6", "New Data Element 6", "Loc.1.1.6", "StandardCode", ["NM1","NM3"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE7", "New Data Element 7", "Loc.1.1.7", "StandardCode", ["NM1","NM4"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE8", "New Data Element 8", "Loc.1.1.8", "StandardCode", ["NM4","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE9", "New Data Element 9", "Loc.1.1.9", "StandardCode", ["NM3","NM2"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""],
			["NDE0", "New Data Element 0", "Loc.1.1.0", "StandardCode", ["NM4"],["MEDITECH 6.0", "MEDITECH 6.1"], "", ""]
		]
		for(_el in _elements){
			def _element = new DataElement(code:_el[0], name:_el[1], notes:_el[6], help:_el[7])

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
				def dataElementDefaults = new DataElementDefaults(location:_el[2], valueType:_el[3], dataElement:_element, ehr : Ehr.findByCode(_ehr))
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
