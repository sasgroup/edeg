package ihm

import ihm_demo.*

class LoginTagLib {
	def securityService
	
	def setUser = {

		def name = request.getRemoteUser()
		if (name){
			def user = new User(login:name, password: "", role:securityService.getRole(name))
			session.user = user
			println "$user"
		}
	
		println "~~~~~~~~~$session.user.role"
	}
	
	def loginControl = {
		if(request.getSession(false) && session.user){
			out << "${session.user.login} "
			out << """[${link(action:"logout",
			controller:"user"){"Logout"}}]"""
		} else {
			out << """[${link(action:"login",
			controller:"user"){"Login"}}]"""
		}
	}
}
