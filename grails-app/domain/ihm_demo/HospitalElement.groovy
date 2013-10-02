package ihm_demo

class HospitalElement {
	static auditable = true
	String internalNotes
	String location
	String notes
	String source
	Boolean sourceEHR
	String valueSet
	String valueSetFile
	ValueType valueType
	//CodeType codeType
	DataElement dataElement
	
	//new addition
	Hospital hospital
	
	
	//static hasMany = [hospitalMeasures : HospitalMeasure]
	
	//static belongsTo = [HospitalMeasure]
	
	static hasMany = [hospitalMeasureElements : HospitalMeasureElement]
		
    static constraints = {
		notes(maxSize:5000)
		internalNotes(maxSize:5000)
		location()
		valueSet()
    }
	
	String toString(){
		"$hospital.name, $dataElement.name, $location"
	}
}
