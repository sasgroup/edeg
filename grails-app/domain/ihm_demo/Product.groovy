package ihm_demo

class Product {

	String code
	String name
	String notes
	String help
	
	static hasMany = [measures : Measure]

			
	static constraints = {
		code(blank:false,unique:true)
		name(blank:false)
		notes(maxSize:5000)
		help(nullable: true,maxSize:5000)
	}

	String toString() {
		"$code, $name"
	}
}
