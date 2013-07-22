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
		code(blank:false)
		name(blank:false)
		notes(nullable: true,maxSize:5000)		
		measureCategory(nullable: true)
		cqmDomain(nullable: true)
    }
	
	String toString() {
		"$code, $name"
	}
	
}
