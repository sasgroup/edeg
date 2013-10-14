package ihm_demo

class DataElementDefaults {
	String location
	ValueType valueType
	DataElement dataElement
	Ehr ehr
	String ids

	
    static constraints = {
		location(nullable: true)
		ids(nullable: true)
    }
	
	String toString() {
		"$location"
	}
}
