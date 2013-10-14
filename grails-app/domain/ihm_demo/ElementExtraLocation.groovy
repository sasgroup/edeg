package ihm_demo 

class ElementExtraLocation {
	String location
	String source
	Boolean sourceEHR
	//CodeType codeType
	HospitalElement hospitalElement
	ValueType valueType
	ValuesType valuesType
	
    static constraints = {
		location(nullable: true)
		source(nullable: true)
		//codeType()
		hospitalElement()
		valueType(nullable: true)
    }
	
	String toString(){
		"$location, $source"
	}
}
