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
		notes(maxSize:5000)
		externalEHRs(nullable:true,maxSize:1000)
		//populationMethod()
		email(nullable:true)
    }
	
	String toString(){
		"$name"
	}

}
