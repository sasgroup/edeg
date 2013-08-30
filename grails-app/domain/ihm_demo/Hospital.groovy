package ihm_demo

class Hospital {

	String name
	String email
	String notes
	Ehr ehr
		
    static constraints = {
		name()
		notes(maxSize:5000)
		email(nullable:true)
    }
	
	String toString(){
		"$name, $notes"
	}

}
