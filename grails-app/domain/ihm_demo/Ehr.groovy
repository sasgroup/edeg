package ihm_demo

class Ehr {
    String code
	String name
	String notes
   
	 static constraints = {
		code(blank:false,/*unique:true,*/validator:{return !Product.findByCodeIlike(it)})
		name(blank:false)
		notes(maxSize:5000)
    }
	 
	String toString(){
		"$code, $name"
	}
	 
}
