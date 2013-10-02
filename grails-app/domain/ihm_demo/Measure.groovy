package ihm_demo

class Measure {
	static auditable = true
	String code
	String name
	String notes
	MeasureCategory measureCategory
	CqmDomain cqmDomain
	String help
	
	static hasMany = [products : Product,
					  dataElements : DataElement]
	
	static belongsTo = [Product]
	
    static constraints = {
		code(blank:false,unique:true)
		name(blank:false)
		notes(nullable: true,maxSize:5000)
		help(nullable: true,maxSize:5000)
		measureCategory(nullable: true)
		cqmDomain(nullable: true)
    }
	
	String toString() {
		"$code, $name"
	}
}
