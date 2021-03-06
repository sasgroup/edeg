package ihm_demo

class DataElement {
	static auditable = true
	String code
	String name
	String notes
	String help
	
	static hasMany = [measures : Measure]
	
	static belongsTo = [Measure]
	
    static constraints = {
		code(blank:false,unique:true)
		name(blank:false,maxSize:1000)
		notes(nullable: true,maxSize:4000)
		help(nullable: true,maxSize:4000)
    }
	
	static mapping = {
		notes defaultValue: "''"
		help defaultValue: "''"
	}
	
	String toString() {
		"$code"
	}
}
