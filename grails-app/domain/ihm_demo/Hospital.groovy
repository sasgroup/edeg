package ihm_demo

class Hospital {
	static auditable = true
	String name
	String email
	String notes
	String externalEHRs
	String populationMethod
	Ehr ehr
		
    static constraints = {
		name()
		notes(nullable:true,maxSize:4000)
		externalEHRs(nullable:true,maxSize:4000)
		//populationMethod()
		email(nullable:true)
		externalEHRs(nullable:true)
		populationMethod(nullable:true)
    }
	
	String toString(){
		"$name"
	}

}
