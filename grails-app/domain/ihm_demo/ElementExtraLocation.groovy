package ihm_demo 

class ElementExtraLocation {
	String location
	String source
	Boolean sourceEHR
	//CodeType codeType
	HospitalElement hospitalElement
	ValueType valueType
	
    static constraints = {
		location()
		source()
		//codeType()
		hospitalElement()
		valueType()
    }
	
	String toString(){
		"$location, $source"
	}
}
