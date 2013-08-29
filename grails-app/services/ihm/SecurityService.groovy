package ihm

import org.healthmetrics.security.*

class SecurityService {
	IhmSecurity ihmSecurity = new IhmSecurityImpl()
	
	def getRole(String user) {
		println "Service"
		
		if (ihmSecurity.hasRole("admin",user)) {
			return "admin"
		} else if (ihmSecurity.hasRole("user",user)){
			return "user"
		} else {
			return "undef"
		}
    }
	
	def getCurrentHospital(String user) {
		return ihmSecurity?.getCurrentHospital(user)
	}
	
	def setCurrentHospital(String user, String hospitalId) {
		return ihmSecurity.setCurrentHospital(user, hospitalId)
	}
	
	def getHospitalNameMap(String user) {
		return ihmSecurity?.getHospitalNameMap(user)
	}
	
}
