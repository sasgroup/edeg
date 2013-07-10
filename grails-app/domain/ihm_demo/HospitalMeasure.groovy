package ihm_demo

class HospitalMeasure {
	
	Boolean accepted
	Boolean completed
	Boolean confirmed
	Boolean included
	Boolean verified
	Measure measure

	static hasMany = [hospitalProducts : HospitalProduct,
					  hospitalElement : HospitalElement]
	
	static belongsTo = HospitalElement
	
    static constraints = {
		accepted()
		completed()
		confirmed()
		included()
    }
	
	static HospitalMeasure findAllByHospitalProductsAndMeasure(HospitalProduct hospitalProduct, Measure m) {
		def c = HospitalMeasure.createCriteria()
		def result = c.get{
			and {
				eq("measure" , m)
				hospitalProducts {
					idEq(hospitalProduct.id)
				}
			}	
			
		}
		return result
	}
}
