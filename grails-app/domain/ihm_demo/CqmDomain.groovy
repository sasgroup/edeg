package ihm_demo

class CqmDomain {
	String name
	String notes
    
	static constraints = {
		name()
		notes(maxSize:4000)
    }
	
	String toString(){
		"$name"
	}
}
