package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class EhrController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [ehrInstanceList: Ehr.list(params), ehrInstanceTotal: Ehr.count()]
    }

    def create() {
        [ehrInstance: new Ehr(params)]
    }

    def save() {
        def ehrInstance = new Ehr(params)
        if (!ehrInstance.save(flush: true)) {
            render(view: "create", model: [ehrInstance: ehrInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'ehr.label', default: 'Ehr'), ehrInstance.id])
        redirect(action: "show", id: ehrInstance.id)
    }

    def show(Long id) {
        def ehrInstance = Ehr.get(id)
        if (!ehrInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "list")
            return
        }

        [ehrInstance: ehrInstance]
    }

    def edit(Long id) {
        def ehrInstance = Ehr.get(id)
        if (!ehrInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "list")
            return
        }

        [ehrInstance: ehrInstance]
    }

    def update(Long id, Long version) {
        def ehrInstance = Ehr.get(id)
        if (!ehrInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (ehrInstance.version > version) {
                ehrInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ehr.label', default: 'Ehr')] as Object[],
                          "Another user has updated this Ehr while you were editing")
                render(view: "edit", model: [ehrInstance: ehrInstance])
                return
            }
        }

        ehrInstance.properties = params

        if (!ehrInstance.save(flush: true)) {
            render(view: "edit", model: [ehrInstance: ehrInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'ehr.label', default: 'Ehr'), ehrInstance.id])
        redirect(action: "show", id: ehrInstance.id)
    }

    def delete(Long id) {
        def ehrInstance = Ehr.get(id)
        if (!ehrInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "list")
            return
        }

        try {
            ehrInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ehr.label', default: 'Ehr'), id])
            redirect(action: "show", id: id)
        }
    }
}
