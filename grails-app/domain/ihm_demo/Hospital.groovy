package ihm_demo

class Hospital {

	String name
	String notes
	Ehr ehr
	static hasMany = [products : Product]
	static belongsTo = Product
		
    static constraints = {
		name()
		notes(maxSize:5000)
		ehr()
    }
	
	String toString(){
		"$name, $notes"
	}

}
