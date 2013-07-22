package ihm_demo

class Product {

	String code
	String name
	String notes
	static hasMany = [measures : Measure]
	//static belongsTo = Measure
	
	/*static mapping = {
		measures cascade: 'all-delete-orphan'
		hospitals cascade: 'all-delete-orphan'
	}*/
	
	 static constraints = {
		code(blank:false)
		name(blank:false)
		notes(maxSize:5000)
	}
	 
	String toString() {
		"$code, $name"
	}
   
}
