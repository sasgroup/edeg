package ihm_demo

class HospitalProduct {
	static auditable = [ignore:['notifyUser','notifyAdmin','hospitalProductMeasures']]
	Hospital hospital
	Product product
	String qa
	Boolean notifyAdmin
	Boolean notifyUser
	
	static hasMany = [hospitalProductMeasures : HospitalProductMeasure]
	
	static mapping = {
		qa defaultValue: "''"
	}
	
	static constraints = {		
		qa(nullable:true,maxSize:4000)
	}
	
	String toString(){
		"$hospital?.name, $product?.code"
	}
}
