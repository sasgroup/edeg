package ihm_demo

class DataElementDefaults {
	String location
	//String source
	//String sourceEHR
	ValueType valueType
	//CodeType codeType
	DataElement dataElement
	Ehr ehr

	
    static constraints = {
		location()
		//source()
		//sourceEHR()
		valueType()
		//codeType()
    }
	
	String toString() {
		"$location"
	}
}
