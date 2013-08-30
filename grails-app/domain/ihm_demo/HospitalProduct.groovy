package ihm_demo

class HospitalProduct {

	Hospital hospital
	Product product

	//static hasMany = [hospitalMeasures : HospitalMeasure]
	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure]

	String toString(){
		"$hospital.name, $product.name"
	}
}
