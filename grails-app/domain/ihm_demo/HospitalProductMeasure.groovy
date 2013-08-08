package ihm_demo

class HospitalProductMeasure {

	Boolean included

	static belongsTo = [hospitalProduct: HospitalProduct, hospitalMeasure: HospitalMeasure]

	String toString(){
		"$hospitalProduct.product.name, $hospitalMeasure.measure.name"
	}

}
