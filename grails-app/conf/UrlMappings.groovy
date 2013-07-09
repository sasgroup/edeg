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
		
		"/api/measure/$id?"(resource: "measure") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/element/$id?"(resource: "dataElement") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}
		
		"/api/hospital/$id?"(resource: "hospital") {
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "apply"]
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

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
