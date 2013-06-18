import ihm_demo.*

class BootStrap {

    def init = { servletContext ->
		//-----------EHRs-----------
		def ehr1 = new Ehr(code:"EHR1",
						   name:"Ehr app1",
						   notes:'''Some notes
									for first Ehr''')
		if (!ehr1.save()){
			ehr1.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		def ehr2 = new Ehr(code:"EHR2",
			name:"Ehr app2",
			notes:'''Some notes
									for first Ehr2''')
		if (!ehr2.save()){
			ehr2.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------PRODUCTs-----------
		def product1 = new Product(code:"pr1",
								   name:"pr1 app1",
								   notes:'''Some notes
									for first Ehr''')
		if (!product1.save()){
			product1.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		def product2 = new Product(code:"pr2",
								   name:"pr2 app2",
								   notes:'''Some notes
											for first Ehr2''')
		if (!product2.save()){
			product2.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------HOSPITALs-----------
		def hospital1 = new Hospital(name:"Sun Fra Hospital",
									 notes:'''Some notes
											for first Hospital''',
									 ehr:ehr1,
									 product:product2)
		if (!hospital1.save()){
			hospital1.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------USERs-----------
		def user1 = new Users(username:"user1",
							  password:"password1",
							  role:"SystemUser",
							  hospital:hospital1)
		if (!user1.save()){
			user1.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		def user2 = new Users(username:"user2",
							  password:"password2",
							  role:"HospitalUser",
							  hospital:hospital1)
		if (!user2.save()){
			user2.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------MEASURE_CATEGORYs-----------
		def measureCategory =new MeasureCategory(name:"name",
								 				 description:"description",
												 categoryType:"Core")
		if (!measureCategory.save()){
			measureCategory.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------CQM_DOMAINs-----------
		def cqmDomain =new CqmDomain(name:"name",
					   			     notes:"!")
		if (!cqmDomain.save()){
			cqmDomain.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------MEASUREs-----------
		def measure = new Measure(code:"meas",
								  name:"name",
								  notes:"",
								  measureCategory:measureCategory,
								  cqmDomain:cqmDomain)
		if (!measure.save()){
			measure.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------DATA_ELEMENTs-----------
		def dataElement =new DataElement(code:"de",
							   			 name:"name",
										 notes:"")
		if (!dataElement.save()){
			dataElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------DATA_ELEMENT_DEFAULTSs-----------
		def dataElementDefaults =new DataElementDefaults(isIMO:true,
							   							location:"location",
														queryMnemonic:"queryMnemonic",
														valueSet:"valueSet",
														valueSetRequired:true,
														locationtype:"Internal",
														dataElements : [dataElement],
														ehrs : [ehr1])
		if (!dataElementDefaults.save()){
			dataElementDefaults.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		//-----------DATA_ELEMENT_DEFAULTSs-----------
		def hospitalMeasure =new HospitalMeasure(approved:true,
												 completed:false,
												 confirmed:false,
												 included:false,
												 hospitals : [hospital1],
												 measures : [measure])
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
												dataElementDefaults : [dataElementDefaults],
												dataElements : [dataElement])
		if (!hospitalElement.save()){
			hospitalElement.errors.allErrors.each{error ->
				println "An error occured with event1: ${error}"
			}
		}
		
		//-----------LINKs-----------
		measure.addToProducts(product1)
		measure.save()
		
		dataElement.addToMeasures(measure)
		dataElement.save()
	//end	
    }
	
    def destroy = {
    }
}
