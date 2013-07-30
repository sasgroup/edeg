package ihm_demo

import org.springframework.dao.DataIntegrityViolationException


class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
       // redirect(action: "list", params: params)
		[usersInstanceList: User.list(params), usersInstanceTotal: User.count()]
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [usersInstanceList: User.list(params), usersInstanceTotal: User.count()]
		/*def results = User.list()
		
		render(contentType: "text/json") {
			resp = "ok"
			message = "List of Users"
		}*/
    }

    def create() {
        def userInstance = new User()
		userInstance.properties = params
		return [userInstance: userInstance]
    }

    def save() { }

    def show(Long id) { }

    def edit(Long id) { }

    def update(Long id, Long version) { }

    def delete(Long id) { }
}
