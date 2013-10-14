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
		name(blank:false)
		notes(nullable: true,maxSize:4000)
		help(nullable: true,maxSize:4000)
    }
	
	String toString() {
		"$code, $name"
	}
}
