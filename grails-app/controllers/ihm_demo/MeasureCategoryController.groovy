package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class MeasureCategoryController {
	
	def show() {
		println "show"
		if (params.id && MeasureCategory.exists(params.id)) {
			def  result = MeasureCategory.get(params.id)
									
			render(contentType: "text/json") {
				name         = result.name
				description  = result.description
				categoryType = result.categoryType
				id           = result.id			
			}
		}
		else {
			def results = MeasureCategory.list()			
			render(contentType: "text/json") {
				measureCategories = array {
					for (p in results) {
						measureCategory name: p.name, description: p.description, categoryType: p.categoryType, id: p.id
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
        [measureCategoryInstanceList: MeasureCategory.list(params), measureCategoryInstanceTotal: MeasureCategory.count()]
    }

    def create() {
        [measureCategoryInstance: new MeasureCategory(params)]
    }

    def save() {
        def measureCategoryInstance = new MeasureCategory(params)
        if (!measureCategoryInstance.save(flush: true)) {
            render(view: "create", model: [measureCategoryInstance: measureCategoryInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), measureCategoryInstance.id])
        redirect(action: "show", id: measureCategoryInstance.id)
    }

    def show(Long id) {
        def measureCategoryInstance = MeasureCategory.get(id)
        if (!measureCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "list")
            return
        }

        [measureCategoryInstance: measureCategoryInstance]
    }

    def edit(Long id) {
        def measureCategoryInstance = MeasureCategory.get(id)
        if (!measureCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "list")
            return
        }

        [measureCategoryInstance: measureCategoryInstance]
    }

    def update(Long id, Long version) {
        def measureCategoryInstance = MeasureCategory.get(id)
        if (!measureCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (measureCategoryInstance.version > version) {
                measureCategoryInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'measureCategory.label', default: 'MeasureCategory')] as Object[],
                          "Another user has updated this MeasureCategory while you were editing")
                render(view: "edit", model: [measureCategoryInstance: measureCategoryInstance])
                return
            }
        }

        measureCategoryInstance.properties = params

        if (!measureCategoryInstance.save(flush: true)) {
            render(view: "edit", model: [measureCategoryInstance: measureCategoryInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), measureCategoryInstance.id])
        redirect(action: "show", id: measureCategoryInstance.id)
    }

    def delete(Long id) {
        def measureCategoryInstance = MeasureCategory.get(id)
        if (!measureCategoryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "list")
            return
        }

        try {
            measureCategoryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'measureCategory.label', default: 'MeasureCategory'), id])
            redirect(action: "show", id: id)
        }
    }*/
}
