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
	//ValueType valueType
	DataElement dataElement
	ValuesType valuesType
	
	//new addition
	Hospital hospital
	
	
	static hasMany = [hospitalMeasureElements : HospitalMeasureElement]
		
    static constraints = {
		notes(nullable:true,maxSize:4000)
		internalNotes(nullable:true,maxSize:4000)
		location(nullable:true,maxSize:1000)
		notes(nullable:true,maxSize:4000)
		source(nullable:true,maxSize:1000)
		valueSet(nullable:true)
		valueSetFile(nullable:true,maxSize:1000)
		
    }
	
	static mapping = {
		notes defaultValue: "''"
		location defaultValue: "''"
		internalNotes defaultValue: "''"
	}
	
	String toString(){
		"$hospital.name, $dataElement.name, $location"
	}
}
