package ihm_demo

class Ehr {
    String code
	String name
	String notes
   
	 static constraints = {
		code(blank:false)
		name(blank:false)
		notes(maxSize:5000)
    }
	 
	String toString(){
		"$code, $name"
	}
	 
}
