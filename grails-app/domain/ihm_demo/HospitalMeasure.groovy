package ihm_demo

class HospitalMeasure {
	static auditable = true
	Boolean accepted
	Boolean completed
	Boolean confirmed
	Boolean verified
	Measure measure
	String qa
	Boolean notifyAdmin
	Boolean notifyUser
	Hospital hospital

	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure,
					  hospitalMeasureElements : HospitalMeasureElement]
	
    static constraints = {
		accepted()
		completed()
		confirmed()
		qa(nullable: true,maxSize:4000)
    }

	static mapping = {
		qa defaultValue: "''"
	}
	
	String toString(){
		"$measure, $hospital"
	}
}
