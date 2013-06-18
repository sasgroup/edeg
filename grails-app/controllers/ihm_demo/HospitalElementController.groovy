package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalElementController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [hospitalElementInstanceList: HospitalElement.list(params), hospitalElementInstanceTotal: HospitalElement.count()]
    }

    def create() {
        [hospitalElementInstance: new HospitalElement(params)]
    }

    def save() {
        def hospitalElementInstance = new HospitalElement(params)
        if (!hospitalElementInstance.save(flush: true)) {
            render(view: "create", model: [hospitalElementInstance: hospitalElementInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), hospitalElementInstance.id])
        redirect(action: "show", id: hospitalElementInstance.id)
    }

    def show(Long id) {
        def hospitalElementInstance = HospitalElement.get(id)
        if (!hospitalElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "list")
            return
        }

        [hospitalElementInstance: hospitalElementInstance]
    }

    def edit(Long id) {
        def hospitalElementInstance = HospitalElement.get(id)
        if (!hospitalElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "list")
            return
        }

        [hospitalElementInstance: hospitalElementInstance]
    }

    def update(Long id, Long version) {
        def hospitalElementInstance = HospitalElement.get(id)
        if (!hospitalElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hospitalElementInstance.version > version) {
                hospitalElementInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hospitalElement.label', default: 'HospitalElement')] as Object[],
                          "Another user has updated this HospitalElement while you were editing")
                render(view: "edit", model: [hospitalElementInstance: hospitalElementInstance])
                return
            }
        }

        hospitalElementInstance.properties = params

        if (!hospitalElementInstance.save(flush: true)) {
            render(view: "edit", model: [hospitalElementInstance: hospitalElementInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), hospitalElementInstance.id])
        redirect(action: "show", id: hospitalElementInstance.id)
    }

    def delete(Long id) {
        def hospitalElementInstance = HospitalElement.get(id)
        if (!hospitalElementInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "list")
            return
        }

        try {
            hospitalElementInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hospitalElement.label', default: 'HospitalElement'), id])
            redirect(action: "show", id: id)
        }
    }
}
