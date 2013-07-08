package ihm_demo

class Hospital {

	String name
	String notes
	Ehr ehr
		
    static constraints = {
		name()
		notes(maxSize:5000)
		ehr()
    }
	
	String toString(){
		"$name, $notes"
	}

}
