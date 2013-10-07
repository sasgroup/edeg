package ihm_demo

class HospitalMeasure {
	static auditable = true
	Boolean accepted
	Boolean completed
	Boolean confirmed
	Boolean verified
	Measure measure
	
	//new addition
	Hospital hospital

	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure,
					  hospitalMeasureElements : HospitalMeasureElement]
	
    static constraints = {
		accepted()
		completed()
		confirmed()
		//included()
    }

	String toString(){
		"$hospital.name, $measure.name"
	}
}
