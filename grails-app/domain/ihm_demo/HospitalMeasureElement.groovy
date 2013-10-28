package ihm_demo

class HospitalMeasureElement {

	static belongsTo = [hospitalElement: HospitalElement, hospitalMeasure: HospitalMeasure]


	String toString(){
		"$hospitalMeasure, $hospitalElement"
	}
}
