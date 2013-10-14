package ihm_demo

class Ehr {
    String code
	String name
	String notes
   
	 static constraints = {
		code(blank:false,unique:true)
		name(blank:false)
		notes(nullable: true,maxSize:4000)
    }
	 
	String toString(){
		"$code, $name"
	}
}
