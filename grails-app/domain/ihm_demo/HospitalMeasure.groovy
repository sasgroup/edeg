package ihm_demo

class HospitalMeasure {
	
	Boolean approved
	Boolean completed
	Boolean confirmed
	Boolean included
	
	static hasMany = [hospitals : Hospital,
					  measures : Measure,
					  hospitalElement : HospitalElement]
	
	static belongsTo = HospitalElement
	
    static constraints = {
		approved()
		completed()
		confirmed()
		included()
    }
}
