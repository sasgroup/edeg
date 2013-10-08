package ihm_demo

class HospitalProduct {
	static auditable = true
	Hospital hospital
	Product product
	String qa
	
	//static hasMany = [hospitalMeasures : HospitalMeasure]
	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure]
	
	static mapping = {
		qa defaultValue: "''"
	}
	
	String toString(){
		"$hospital.name, $product.name"
	}
}
