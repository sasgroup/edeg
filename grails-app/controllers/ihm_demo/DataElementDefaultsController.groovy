package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class DataElementDefaultsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [dataElementDefaultsInstanceList: DataElementDefaults.list(params), dataElementDefaultsInstanceTotal: DataElementDefaults.count()]
    }

    def create() {
        [dataElementDefaultsInstance: new DataElementDefaults(params)]
    }

    def save() {
        def dataElementDefaultsInstance = new DataElementDefaults(params)
        if (!dataElementDefaultsInstance.save(flush: true)) {
            render(view: "create", model: [dataElementDefaultsInstance: dataElementDefaultsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), dataElementDefaultsInstance.id])
        redirect(action: "show", id: dataElementDefaultsInstance.id)
    }

    def show(Long id) {
        def dataElementDefaultsInstance = DataElementDefaults.get(id)
        if (!dataElementDefaultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "list")
            return
        }

        [dataElementDefaultsInstance: dataElementDefaultsInstance]
    }

    def edit(Long id) {
        def dataElementDefaultsInstance = DataElementDefaults.get(id)
        if (!dataElementDefaultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "list")
            return
        }

        [dataElementDefaultsInstance: dataElementDefaultsInstance]
    }

    def update(Long id, Long version) {
        def dataElementDefaultsInstance = DataElementDefaults.get(id)
        if (!dataElementDefaultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (dataElementDefaultsInstance.version > version) {
                dataElementDefaultsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults')] as Object[],
                          "Another user has updated this DataElementDefaults while you were editing")
                render(view: "edit", model: [dataElementDefaultsInstance: dataElementDefaultsInstance])
                return
            }
        }

        dataElementDefaultsInstance.properties = params

        if (!dataElementDefaultsInstance.save(flush: true)) {
            render(view: "edit", model: [dataElementDefaultsInstance: dataElementDefaultsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), dataElementDefaultsInstance.id])
        redirect(action: "show", id: dataElementDefaultsInstance.id)
    }

    def delete(Long id) {
        def dataElementDefaultsInstance = DataElementDefaults.get(id)
        if (!dataElementDefaultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "list")
            return
        }

        try {
            dataElementDefaultsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'dataElementDefaults.label', default: 'DataElementDefaults'), id])
            redirect(action: "show", id: id)
        }
    }
}
