package ihm_demo

class ValuesType {
	static auditable = true
	String name
	String description
	
	static constraints = {
		name(unique:true)
		description(nullable:true,maxSize:1000)
	}
	
	static mapping = {
		description defaultValue: "''"
	}
	
	String toString(){
		"$name"
	}
}
