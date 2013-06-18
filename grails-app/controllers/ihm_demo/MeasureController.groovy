package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class MeasureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [measureInstanceList: Measure.list(params), measureInstanceTotal: Measure.count()]
    }

    def create() {
        [measureInstance: new Measure(params)]
    }

    def save() {
        def measureInstance = new Measure(params)
        if (!measureInstance.save(flush: true)) {
            render(view: "create", model: [measureInstance: measureInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'measure.label', default: 'Measure'), measureInstance.id])
        redirect(action: "show", id: measureInstance.id)
    }

    def show(Long id) {
        def measureInstance = Measure.get(id)
        if (!measureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "list")
            return
        }

        [measureInstance: measureInstance]
    }

    def edit(Long id) {
        def measureInstance = Measure.get(id)
        if (!measureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "list")
            return
        }

        [measureInstance: measureInstance]
    }

    def update(Long id, Long version) {
        def measureInstance = Measure.get(id)
        if (!measureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (measureInstance.version > version) {
                measureInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'measure.label', default: 'Measure')] as Object[],
                          "Another user has updated this Measure while you were editing")
                render(view: "edit", model: [measureInstance: measureInstance])
                return
            }
        }

        measureInstance.properties = params

        if (!measureInstance.save(flush: true)) {
            render(view: "edit", model: [measureInstance: measureInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'measure.label', default: 'Measure'), measureInstance.id])
        redirect(action: "show", id: measureInstance.id)
    }

    def delete(Long id) {
        def measureInstance = Measure.get(id)
        if (!measureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "list")
            return
        }

        try {
            measureInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'measure.label', default: 'Measure'), id])
            redirect(action: "show", id: id)
        }
    }
}
