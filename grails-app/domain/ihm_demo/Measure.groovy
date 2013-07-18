package ihm_demo

class Measure {
	
	String code
	String name
	String notes
	MeasureCategory measureCategory
	CqmDomain cqmDomain
	
	static hasMany = [products : Product,
					  dataElements : DataElement]
	
	static belongsTo = [Product]
	
    static constraints = {
		code()
		name()
		notes(nullable: true)
		measureCategory(nullable: true)
		cqmDomain(nullable: true)
    }
	
	String toString() {
		"$code, $name"
	}
	
}
