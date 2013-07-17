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
	DataElement dataElement
	
	
	
	static hasMany = [hospitalMeasure : HospitalMeasure]
		
    static constraints = {
		internalNotes()
		location()
		valueSet()
    }
	
	String toString() {
		"$location, $valueSet"
	}
}
