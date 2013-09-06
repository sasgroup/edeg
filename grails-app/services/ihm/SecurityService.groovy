package ihm

import ihm_demo.Hospital
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
	
	def getHospitalNameMapCurrentId(String user) {

		Map map = getHospitalNameMap(user)
		Map avHospitals = new  HashMap()
		
		map.each { key, value  ->
			Hospital h = Hospital.findByName(value)
			if (h)
				avHospitals.put(h.id, value)
		}

		Map res = avHospitals.sort { a,b -> a.value <=> b.value }
		return res
	}
	
}