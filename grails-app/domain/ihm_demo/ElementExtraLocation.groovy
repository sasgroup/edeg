package ihm_demo 

class ElementExtraLocation {
	static auditable = true
	String location
	String source
	Boolean sourceEHR
	//CodeType codeType
	HospitalElement hospitalElement
	//ValueType valueType
	ValuesType valuesType
	
    static constraints = {
		location(nullable: true,maxSize:1000)
		source(nullable: true)
		//codeType()
		hospitalElement()
		//valueType(nullable: true)
		valuesType(nullable: true)
    }
	
	static mapping = {
		location defaultValue: "''"
	}
	
	String toString(){
		"$location"
	}
}
