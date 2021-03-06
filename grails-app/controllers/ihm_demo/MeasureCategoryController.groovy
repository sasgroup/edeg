package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class MeasureCategoryController {
	
	def show() {
		if (params.id && MeasureCategory.exists(params.id)) {
			def  result = MeasureCategory.get(params.id)
									
			render(contentType: "text/json") {
				name         = result.name
				description  = result.description
				
				id           = result.id			
			}
		}
		else {
			def results = MeasureCategory.list()			
			render(contentType: "text/json") {
				measureCategories = array {
					for (p in results) {
						mCategory name: p.name, 
						description: p.description,  
						id: p.id
					}
				}
			}
		}
	}

}
