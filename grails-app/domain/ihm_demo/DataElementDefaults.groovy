package ihm_demo

class DataElementDefaults {
	String location
	ValueType valueType
	DataElement dataElement
	Ehr ehr
	String ids

	
    static constraints = {
		location(nullable: true,maxSize:1000)
		ids(nullable: true)
    }
	
	static mapping = {
		location defaultValue: "''"
	}
	
	String toString() {
		"$location"
	}
}
