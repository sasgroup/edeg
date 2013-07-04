package ihm_demo

class HospitalElement {
	
	String internalNotes
	String location
	String notes
	String source
	Boolean sourceEHR
	String valueSet
	String valueSetFile
	ValueType valueType
	CodeType codeType
	
	
	static hasMany = [dataElements : DataElement,
					  dataElementDefaults : DataElementDefaults,
					  hospitalMeasure : HospitalMeasure]
		
    static constraints = {
		internalNotes()
		location()
		valueSet()
    }
	
	String toString() {
		"$isIMO, $location, $queryMnemonic, $valueSet, $valueSetRequired, $locationtype"
	}
}
