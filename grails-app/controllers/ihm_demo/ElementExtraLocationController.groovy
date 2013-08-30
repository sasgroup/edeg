package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class ElementExtraLocationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [elementExtraLocationInstanceList: ElementExtraLocation.list(params), elementExtraLocationInstanceTotal: ElementExtraLocation.count()]
    }

    def create() {
        [elementExtraLocationInstance: new ElementExtraLocation(params)]
    }

    def save() {
        def elementExtraLocationInstance = new ElementExtraLocation(params)
        if (!elementExtraLocationInstance.save(flush: true)) {
            render(view: "create", model: [elementExtraLocationInstance: elementExtraLocationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), elementExtraLocationInstance.id])
        redirect(action: "show", id: elementExtraLocationInstance.id)
    }

    def show(Long id) {
        def elementExtraLocationInstance = ElementExtraLocation.get(id)
        if (!elementExtraLocationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "list")
            return
        }

        [elementExtraLocationInstance: elementExtraLocationInstance]
    }

    def edit(Long id) {
        def elementExtraLocationInstance = ElementExtraLocation.get(id)
        if (!elementExtraLocationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "list")
            return
        }

        [elementExtraLocationInstance: elementExtraLocationInstance]
    }

    def update(Long id, Long version) {
        def elementExtraLocationInstance = ElementExtraLocation.get(id)
        if (!elementExtraLocationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (elementExtraLocationInstance.version > version) {
                elementExtraLocationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation')] as Object[],
                          "Another user has updated this ElementExtraLocation while you were editing")
                render(view: "edit", model: [elementExtraLocationInstance: elementExtraLocationInstance])
                return
            }
        }

        elementExtraLocationInstance.properties = params

        if (!elementExtraLocationInstance.save(flush: true)) {
            render(view: "edit", model: [elementExtraLocationInstance: elementExtraLocationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), elementExtraLocationInstance.id])
        redirect(action: "show", id: elementExtraLocationInstance.id)
    }

    def delete(Long id) {
        def elementExtraLocationInstance = ElementExtraLocation.get(id)
        if (!elementExtraLocationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "list")
            return
        }

        try {
            elementExtraLocationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'elementExtraLocation.label', default: 'ElementExtraLocation'), id])
            redirect(action: "show", id: id)
        }
    }
}
