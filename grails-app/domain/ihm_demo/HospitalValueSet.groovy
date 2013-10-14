package ihm_demo

class HospitalValueSet {
	String code
	String mnemonic
	HospitalElement hospitalElement
	 
    static constraints = {
		code(nullable:true)
		mnemonic(nullable:true,maxSize:4000)
		hospitalElement()
    }
	String toString() {
		"$code, $mnemonic"
	}
}