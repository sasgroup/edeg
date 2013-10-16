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
		
	static constraints = {
		code()
		name(maxSize:1000)
		notes(nullable:true,maxSize:4000)
	}
	 
	 static mapping = {
		 notes defaultValue: "''"
	 }
	 
	String toString() {
		"$code, $name"
	}
   
}
