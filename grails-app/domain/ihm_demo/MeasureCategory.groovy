package ihm_demo

class MeasureCategory {
	
	String name
	String description
	
    static constraints = {
		name()
		description(nullable:true)
    }
	
	String toString(){
		"$name, $description"
	}
	
}
