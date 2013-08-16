package ihm_demo

import org.springframework.dao.DataIntegrityViolationException


class UserController {

    def scaffold = User
	
	def login = {}
	
	def logout = {
		flash.message = "Goodbye ${session.user?.login}"
		session.invalidate()
		redirect(uri:'/')
	}
		
}
