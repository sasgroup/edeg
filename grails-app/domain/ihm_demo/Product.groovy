package ihm_demo

class Product {
	static auditable = true
	String code
	String name
	String notes
	String help
	
	static hasMany = [measures : Measure]

			
	static constraints = {
		code(blank:false,unique:true)
		name(blank:false,maxSize:1000)
		notes(nullable:true,blank:true,maxSize:4000)
		help(nullable: true,blank:true,maxSize:4000)
	}
	static mapping = {
		notes defaultValue: "''"
		help defaultValue: "''"
	}

	String toString() {
		"$code, $name"
	}
}
