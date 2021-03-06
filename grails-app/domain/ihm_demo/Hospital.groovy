package ihm_demo

class Hospital {
	static auditable = true
	String name
	String ihmid
	String email
	String notes
	String externalEHRs
	String populationMethod
	Ehr ehr
		
    static constraints = {
		ihmid()
		name()
		notes(nullable:true,maxSize:4000)
		email(nullable:true,maxSize:1000)
		externalEHRs(nullable:true,maxSize:1000)
		populationMethod(nullable:true)
    }
	
	
	static mapping = {
		notes defaultValue: "''"
		externalEHRs defaultValue: "''"
	}
	String toString(){
		"$name"
	}

}
