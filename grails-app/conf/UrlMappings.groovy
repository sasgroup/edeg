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
			action = [GET: "show", PUT: "update", DELETE: "delete", POST: "save"]
		}

		"/"(view:"/index")
		"500"(view:'/error')
	}
}
