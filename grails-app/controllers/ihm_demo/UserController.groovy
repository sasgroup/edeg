package ihm_demo

import org.springframework.dao.DataIntegrityViolationException


class UserController {
	//need security part
    def scaffold = User
	
	def login = {}
	
	def error = {}
	
	def list = {}
	
	def logout = {
		flash.message = "Goodbye ${session.user?.login}"
		session.invalidate()
		redirect(uri:'/')
	}
		
}
