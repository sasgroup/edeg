package ihm_demo

class Users {
	
	String username
	String password
	String role
	Hospital hospital
    
	static constraints = {
		username()
		password()
		role(inList:["SystemUser" , "HospitalUser"])
		hospital()
    }
	
	String toString(){
		"$username, $password"
	}
}
