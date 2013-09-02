package ihm

import ihm_demo.*

class LoginTagLib {
	def securityService
	
	def setUser = {

		def name = request.getRemoteUser()
		if (name){
			def user = new User(login:name, password: "", role:securityService.getRole(name))
			session.user = user
			session.curHospital = securityService.getCurrentHospital(name)
		}
	}
	
	def loginControl = {
		if(request.getSession(false) && session.user){
			out << "${session.user.login} "
			out << """<div class="btn">
					${link(action:"logout",
			controller:"user"){"Sign Out"}}
			</div>"""
		} else {
			out << """<div class="btn">
					  ${link(action:"login",
			controller:"user"){"Login"}}
		   </div>"""
		}
	}
}
