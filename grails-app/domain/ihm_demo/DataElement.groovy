package ihm_demo

class DataElement {
	
	String code
	String name
	String notes
	
	static hasMany = [measures : Measure]
	
	static belongsTo = [Measure]
	
    static constraints = {
		code(blank:false)
		name(blank:false)
		notes(maxSize:5000)
    }
	
	String toString() {
		"$code, $name"
	}
}
