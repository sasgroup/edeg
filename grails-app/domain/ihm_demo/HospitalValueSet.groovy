package ihm_demo

class HospitalValueSet {
	String code
	String mnemonic
	HospitalElement hospitalElement
	 
    static constraints = {
		code()
		mnemonic(maxSize:1000)
		hospitalElement()
    }
	String toString() {
		"$code, $mnemonic"
	}
}