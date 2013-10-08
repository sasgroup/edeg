package ihm_demo

class ValuesType {
	static auditable = true
	String name
	String description
	
	static constraints = {
		name(unique:true)
	}
	
	String toString(){
		"$name"
	}
}
