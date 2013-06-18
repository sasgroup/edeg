package ihm_demo

class Product {

	String code
	String name
	String notes
	static hasMany = [measures : Measure, hospitals : Hospital]
	static belongsTo = Measure
	
	 static constraints = {
		code()
		name()
		notes(maxSize:5000)
	}
	 
	String toString() {
		"$code, $name"
	}
   
}
