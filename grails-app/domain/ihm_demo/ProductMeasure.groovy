package ihm_demo

class ProductMeasure {

	Boolean accepted
	Boolean completed
	Boolean confirmed
	Boolean included
	Boolean verified
	
	String code
	String name
	String notes
	//static hasMany = [measures : Measure, hospitals : Hospital]
	//static belongsTo = Measure
	
	/*static mapping = {
		measures cascade: 'all-delete-orphan'
		hospitals cascade: 'all-delete-orphan'
	}*/
	
	 static constraints = {
		code()
		name()
		notes(nullable:true,maxSize:4000)
	}
	 
	String toString() {
		"$code, $name"
	}
   
}
