package ihm_demo

class HospitalMeasure {
	
	Boolean accepted
	Boolean completed
	Boolean confirmed
	Boolean included
	Boolean verified
	
	static hasMany = [hospitals : Hospital,
					  measures : Measure,
					  hospitalElement : HospitalElement]
	
	static belongsTo = HospitalElement
	
    static constraints = {
		accepted()
		completed()
		confirmed()
		included()
    }
}
