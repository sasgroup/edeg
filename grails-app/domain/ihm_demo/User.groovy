package ihm_demo

class User {
	
	String login
	String password
	String role = "SystemUser"
	//Hospital hospital
    
	static constraints = {
		login(blank:false, nullable:false, unique:true)
		password(blank:false, password:true)
		role(inList:["user" , "admin"])
		//hospital()
    }
	
	String toString(){
		login
	}
}
