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
	DataElement dataElement
	ValuesType valuesType
	
	//new addition
	Hospital hospital
	
	
	static hasMany = [hospitalMeasureElements : HospitalMeasureElement]
		
    static constraints = {
		notes(nullable:true,maxSize:4000)
		internalNotes(nullable:true,maxSize:4000)
		location(nullable:true)
		notes(nullable:true)
		source(nullable:true)
		valueSet(nullable:true)
		valueSetFile(nullable:true)
		
    }
	
	String toString(){
		"$hospital.name, $dataElement.name, $location"
	}
}
