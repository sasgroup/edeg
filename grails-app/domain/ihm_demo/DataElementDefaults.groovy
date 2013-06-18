package ihm_demo

class DataElementDefaults {
	Boolean isIMO
	String location
	String queryMnemonic
	String valueSet
	Boolean valueSetRequired
	LocationType locationtype
	
	static hasMany = [dataElements : DataElement,
					  ehrs : Ehr]
	
    static constraints = {
		isIMO()
		location()
		queryMnemonic()
		valueSet()
		valueSetRequired()
		locationtype()
    }
	
	String toString() {
		"$isIMO, $location, $queryMnemonic, $valueSet, $valueSetRequired, $locationtype"
	}
}
