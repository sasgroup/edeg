package ihm_demo

class Hospital {

	String name
	String notes
	Ehr ehr
		
    static constraints = {
		name()
		notes(maxSize:5000)
    }
	
	String toString(){
		"$name, $notes"
	}

}
