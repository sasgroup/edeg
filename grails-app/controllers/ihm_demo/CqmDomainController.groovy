package ihm_demo

import org.springframework.dao.DataIntegrityViolationException

class CqmDomainController {

	def show() {
		if (params.id && CqmDomain.exists(params.id)) {//display only 1 CqmDomain by ID
			def  result = CqmDomain.get(params.id)

			render(contentType: "text/json") {
				name         = result.name	
				notes        = result.notes
				id           = result.id
			}
		}
		else {//display all CqmDomains
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
}
