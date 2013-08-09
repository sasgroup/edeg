package ihm_demo

import org.springframework.dao.DataIntegrityViolationException


class UserController {

    def scaffold = User
	
	def login = {}
	
	def logout = {
		flash.message = "Goodbye ${session.user?.login}"
		session.user = null
		redirect(action:"login")
	}
	
	def authenticate = {
		def user = User.findByLoginAndPassword(params.login, params.password)
		if(user){
		  session.user = user
		  flash.message = "${user.login}!"
		 // redirect(controller:"entry", action:"list")
		  redirect(uri:'/')
		  println session.user.role
		}else{
		  flash.message = "Sorry, ${params.login}. Please try again."
		  redirect(action:"login")
		}
	}
		
}
