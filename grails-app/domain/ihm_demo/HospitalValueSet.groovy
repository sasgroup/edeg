package ihm_demo

class HospitalValueSet {
	String code
	String mnemonic
	CodeType codeType
	HospitalElement hospitalElement
	 
    static constraints = {
		code()
		mnemonic()
		codeType()
		hospitalElement()
    }
	String toString() {
		"$code, $mnemonic"
	}
}
