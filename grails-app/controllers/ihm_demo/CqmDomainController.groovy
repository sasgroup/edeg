package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class CqmDomainController {

	def show() {
		println "show"
		if (params.id && CqmDomain.exists(params.id)) {
			def  result = CqmDomain.get(params.id)
									
			render(contentType: "text/json") {
				name         = result.name	
				notes        = result.notes
				id           = result.id
			}
		}
		else {
			def results = CqmDomain.list()
			render(contentType: "text/json") {
				cqmDomains = array {
					for (p in results) {
						cqmDomain name: p.name, notes: p.notes, id: p.id
					}
				}
			}
		}
	}
	
	/*static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [cqmDomainInstanceList: CqmDomain.list(params), cqmDomainInstanceTotal: CqmDomain.count()]
    }

    def create() {
        [cqmDomainInstance: new CqmDomain(params)]
    }

    def save() {
        def cqmDomainInstance = new CqmDomain(params)
        if (!cqmDomainInstance.save(flush: true)) {
            render(view: "create", model: [cqmDomainInstance: cqmDomainInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), cqmDomainInstance.id])
        redirect(action: "show", id: cqmDomainInstance.id)
    }

    def show(Long id) {
        def cqmDomainInstance = CqmDomain.get(id)
        if (!cqmDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "list")
            return
        }

        [cqmDomainInstance: cqmDomainInstance]
    }

    def edit(Long id) {
        def cqmDomainInstance = CqmDomain.get(id)
        if (!cqmDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "list")
            return
        }

        [cqmDomainInstance: cqmDomainInstance]
    }

    def update(Long id, Long version) {
        def cqmDomainInstance = CqmDomain.get(id)
        if (!cqmDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (cqmDomainInstance.version > version) {
                cqmDomainInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'cqmDomain.label', default: 'CqmDomain')] as Object[],
                          "Another user has updated this CqmDomain while you were editing")
                render(view: "edit", model: [cqmDomainInstance: cqmDomainInstance])
                return
            }
        }

        cqmDomainInstance.properties = params

        if (!cqmDomainInstance.save(flush: true)) {
            render(view: "edit", model: [cqmDomainInstance: cqmDomainInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), cqmDomainInstance.id])
        redirect(action: "show", id: cqmDomainInstance.id)
    }

    def delete(Long id) {
        def cqmDomainInstance = CqmDomain.get(id)
        if (!cqmDomainInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "list")
            return
        }

        try {
            cqmDomainInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'cqmDomain.label', default: 'CqmDomain'), id])
            redirect(action: "show", id: id)
        }
    }*/
}
