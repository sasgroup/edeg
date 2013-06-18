package ihm_demo

class HospitalElement {
	
	String answer
	String question
	Boolean isIMO
	String location
	String queryMnemonic
	String valueSet
	Boolean valueSetRequired
	LocationType locationtype
	
	static hasMany = [dataElements : DataElement,
					  dataElementDefaults : DataElementDefaults,
					  hospitalMeasure : HospitalMeasure]
		
    static constraints = {
		answer()
		question()
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
