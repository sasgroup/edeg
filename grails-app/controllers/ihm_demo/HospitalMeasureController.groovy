package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class HospitalMeasureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [hospitalMeasureInstanceList: HospitalMeasure.list(params), hospitalMeasureInstanceTotal: HospitalMeasure.count()]
    }

    def create() {
        [hospitalMeasureInstance: new HospitalMeasure(params)]
    }

    def save() {
        def hospitalMeasureInstance = new HospitalMeasure(params)
        if (!hospitalMeasureInstance.save(flush: true)) {
            render(view: "create", model: [hospitalMeasureInstance: hospitalMeasureInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), hospitalMeasureInstance.id])
        redirect(action: "show", id: hospitalMeasureInstance.id)
    }

    def show(Long id) {
        def hospitalMeasureInstance = HospitalMeasure.get(id)
        if (!hospitalMeasureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "list")
            return
        }

        [hospitalMeasureInstance: hospitalMeasureInstance]
    }

    def edit(Long id) {
        def hospitalMeasureInstance = HospitalMeasure.get(id)
        if (!hospitalMeasureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "list")
            return
        }

        [hospitalMeasureInstance: hospitalMeasureInstance]
    }

    def update(Long id, Long version) {
        def hospitalMeasureInstance = HospitalMeasure.get(id)
        if (!hospitalMeasureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (hospitalMeasureInstance.version > version) {
                hospitalMeasureInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure')] as Object[],
                          "Another user has updated this HospitalMeasure while you were editing")
                render(view: "edit", model: [hospitalMeasureInstance: hospitalMeasureInstance])
                return
            }
        }

        hospitalMeasureInstance.properties = params

        if (!hospitalMeasureInstance.save(flush: true)) {
            render(view: "edit", model: [hospitalMeasureInstance: hospitalMeasureInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), hospitalMeasureInstance.id])
        redirect(action: "show", id: hospitalMeasureInstance.id)
    }

    def delete(Long id) {
        def hospitalMeasureInstance = HospitalMeasure.get(id)
        if (!hospitalMeasureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "list")
            return
        }

        try {
            hospitalMeasureInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'hospitalMeasure.label', default: 'HospitalMeasure'), id])
            redirect(action: "show", id: id)
        }
    }
}
