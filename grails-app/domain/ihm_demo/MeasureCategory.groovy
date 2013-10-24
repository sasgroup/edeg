package ihm_demo

class MeasureCategory {
	
	String name
	String description
	
    static constraints = {
		name(maxSize:1000)
		description(nullable:true)
    }
	
	String toString(){
		"$name"
	}
	
}
