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
	
	//new addition
	Hospital hospital

	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure,
					  hospitalMeasureElements : HospitalMeasureElement]
	
    static constraints = {
		accepted()
		completed()
		confirmed()
		//included()
		qa(nullable: true, maxSize:5000)
    }

	static mapping = {
		qa defaultValue: "''"
	}
	
	String toString(){
		"$hospital.name, $measure.name"
	}
}
