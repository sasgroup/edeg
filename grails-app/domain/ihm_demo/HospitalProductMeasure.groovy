package ihm_demo

class HospitalProductMeasure {
	static auditable = true
	Boolean included

	static belongsTo = [hospitalProduct: HospitalProduct, hospitalMeasure: HospitalMeasure]

	String toString(){
		"$hospitalProduct?.product?.code, $hospitalMeasure?.measure?.code"
	}

}
