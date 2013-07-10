package ihm_demo

class DataElement {
	
	String code
	String name
	String notes
	
	static hasMany = [measures : Measure]
	
	static belongsTo = [Measure]
	
    static constraints = {
		code()
		name()
		notes()
    }
	
	String toString() {
		"$code, $name"
	}
}
