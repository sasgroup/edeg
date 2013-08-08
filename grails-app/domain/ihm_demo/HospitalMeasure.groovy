package ihm_demo

class HospitalMeasure {
	
	Boolean accepted
	Boolean completed
	Boolean confirmed
	//Boolean included
	Boolean verified
	Measure measure
	
	//new addition
	Hospital hospital

	//static hasMany = [hospitalProducts : HospitalProduct,
	//				  hospitalElements : HospitalElement]
	
	//static belongsTo = [HospitalProduct]
	
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
