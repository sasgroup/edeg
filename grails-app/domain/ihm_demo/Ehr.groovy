package ihm_demo

class Ehr {
    String code
	String name
	String notes
   
	 static constraints = {
		code()
		name()
		notes(maxSize:5000)
    }
	 
	String toString(){
		"$code, $name"
	}
	 
}
