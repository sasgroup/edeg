package ihm_demo

class DataElementDefaults {
	String location
	ValueType valueType
	DataElement dataElement
	Ehr ehr
	String ids

	
    static constraints = {
		location()
		valueType()
    }
	
	String toString() {
		"$location"
	}
}
