class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		
		"/api/product/$id?"(resource: "product") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/report/$id?"(resource: "report") {
			action = [GET: "show"]
		}
		
		"/api/measure/$id?"(resource: "measure") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/element/$id?"(resource: "dataElement") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/hospital/$id?"(resource: "hospital") {
			action = [GET: "show", POST: "update", PUT:"update"]
		}
		"/api/hospital_measure/$id?"(resource: "hospitalMeasure") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/ehr/$id?"(resource: "ehr") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/measure_category/$id?"(resource: "measureCategory") {
			action = [GET: "show"]
		}
		
		"/api/cqm_domain/$id?"(resource: "cqmDomain") {
			action = [GET: "show"]
		}
		
		"/api/product_measure/$id?"(resource: "productMeasure") {
			action = [GET: "show"]
		}
		
		"/api/hospital_measure/$id?"(resource: "hospitalMeasure") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/hospital_element/$id?"(resource: "hospitalElement") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/security?"(resource: "security") {
			action = [GET: "show", POST: "update", PUT:"update"]
		}
		
		"/api/file"(controller: "file") {
			action = [ GET: "show", POST: "save", DELETE:"delete" ]
		}
		
		"/"(view:"/index")

		"500"(view:'/error')
	}
}
