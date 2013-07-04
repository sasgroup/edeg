package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalValueSetController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [hospitalValueSetInstanceList: HospitalValueSet.list(params), hospitalValueSetInstanceTotal: HospitalValueSet.count()]
    }

    def create() {
        [hospitalValueSetInstance: new HospitalValueSet(params)]
    }

    def save() {
        def hospitalValueSetInstance = new HospitalValueSet(params)
        if (!hospitalValueSetInstance.save(flush: true)) {
            render(view: "create", model: [hospitalValueSetInstance: hospitalValueSetInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), hospitalValueSetInstance.id])
        redirect(action: "show", id: hospitalValueSetInstance.id)
    }

    def show(Long id) {
        def hospitalValueSetInstance = HospitalValueSet.get(id)
        if (!hospitalValueSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "list")
            return
        }

        [hospitalValueSetInstance: hospitalValueSetInstance]
    }

    def edit(Long id) {
        def hospitalValueSetInstance = HospitalValueSet.get(id)
        if (!hospitalValueSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "list")
            return
        }

        [hospitalValueSetInstance: hospitalValueSetInstance]
    }

    def update(Long id, Long version) {
        def hospitalValueSetInstance = HospitalValueSet.get(id)
        if (!hospitalValueSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hospitalValueSetInstance.version > version) {
                hospitalValueSetInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet')] as Object[],
                          "Another user has updated this HospitalValueSet while you were editing")
                render(view: "edit", model: [hospitalValueSetInstance: hospitalValueSetInstance])
                return
            }
        }

        hospitalValueSetInstance.properties = params

        if (!hospitalValueSetInstance.save(flush: true)) {
            render(view: "edit", model: [hospitalValueSetInstance: hospitalValueSetInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), hospitalValueSetInstance.id])
        redirect(action: "show", id: hospitalValueSetInstance.id)
    }

    def delete(Long id) {
        def hospitalValueSetInstance = HospitalValueSet.get(id)
        if (!hospitalValueSetInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "list")
            return
        }

        try {
            hospitalValueSetInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hospitalValueSet.label', default: 'HospitalValueSet'), id])
            redirect(action: "show", id: id)
        }
    }
}
