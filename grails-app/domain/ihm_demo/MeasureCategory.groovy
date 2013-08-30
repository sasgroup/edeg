package ihm_demo

class MeasureCategory {
	
	String name
	String description
	
    static constraints = {
		name()
		description()
    }
	
	String toString(){
		"$name, $description"
	}
	
}
