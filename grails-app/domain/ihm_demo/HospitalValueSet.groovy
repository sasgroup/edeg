package ihm_demo

class HospitalValueSet {
	static auditable = true
	String code
	String mnemonic
	HospitalElement hospitalElement
	 
    static constraints = {
		code(nullable:true)
		mnemonic(nullable:true,maxSize:4000)
		hospitalElement()
    }
	
	static mapping = {
		mnemonic defaultValue: "''"
	}
	
	String toString() {
		"$code, $mnemonic"
	}
}