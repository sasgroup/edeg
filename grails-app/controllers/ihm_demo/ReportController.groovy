package ihm_demo

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class ReportController {
		
	def show() {
		if (params.id) {
			println "a"
		} else {
			println "b"
		}
		
		render(contentType: "text/json") {
			resp = "ok"
			message = "successfully created"
		}
	}
}