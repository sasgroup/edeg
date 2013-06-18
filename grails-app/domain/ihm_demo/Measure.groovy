package ihm_demo

class Measure {
	
	String code
	String name
	String notes
	MeasureCategory measureCategory
	CqmDomain cqmDomain
	
	static hasMany = [products : Product,
					  dataElements : DataElement]
	
	static belongsTo = DataElement
	
    static constraints = {
		code()
		name()
		notes()
		measureCategory()
		cqmDomain()
    }
	
	String toString() {
		"$code, $name"
	}
	
}
