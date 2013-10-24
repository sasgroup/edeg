package ihm_demo

class Ehr {
    String code
	String name
	String notes
   
	 static constraints = {
		code(blank:false,unique:true)
		name(blank:false,maxSize:1000)
		notes(nullable: true,maxSize:4000)
    }
	 
	 static mapping = {
		 notes defaultValue: "''"
	 }
	 
	String toString(){
		"$code"
	}
}
