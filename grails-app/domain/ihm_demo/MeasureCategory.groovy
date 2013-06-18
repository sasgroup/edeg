package ihm_demo

class MeasureCategory {
	
	String name
	String description
	CategoryType categoryType
	
    static constraints = {
		name()
		description()
		categoryType()
    }
	
	String toString(){
		"$name, $description"
	}
	
}
