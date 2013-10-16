package ihm_demo

class ValuesTypeController {

   private ValuesType saveInstance (ValuesType instance, def param) {
	   if (instance.name != "NotApplicable"){
		   instance.name = param.name
		   instance.description = isNULL(param.description,"")
		   instance.save(flush :true)
	   }
	   return instance
	}
	
	def save() {
		println params
		def valuesType = saveInstance (new ValuesType(), params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "ValuesType ${valuesType} has been successfully created"
			id= valuesType.id
		}
	}
   
	def show() {
		if (params.id && ValuesType.exists(params.id)) {
			def result = ValuesType.get(params.id)

			render(contentType: "text/json") {
				version = result.version
				name =result.name
				description=isNULL(result.description,"")
				id   = result.id	
			}
		}
		else {
			def results = ValuesType.list()
			
			render(contentType: "text/json") {
				valuesType = array {
					for (v in results) {
						valuesType 	name: v.name, 
									description: isNULL(v.description,""), 
									id: v.id
					}
				}
			}
		}
	}

	
	def update(Long id, Long version) {
		def valuesType = ValuesType.get(id)

		if  (!valuesType) {
			render(contentType: "text/json") {
				resp = "error"
				message = "Id exceptions"
			}
		}

		 if (params.version != null) {
            if (ehr.version > params.version) {
				return render(contentType: "text/json") {
					resp = "error"
					message = "Another user edited this record and saved the changes before you attempted to save your changes. Re-edit the record ${valuesType}."
				}
			} 
		 }	
		
		valuesType  = saveInstance(valuesType, params)
		render(contentType: "text/json") {
			resp = "ok"
			message = "ValuesType ${valuesType} has been successfully updated"
			id= valuesType.id			
		}
	}
	

	def delete(Long id) {
		def valuesType = ValuesType.findById(id)
		if (valuesType.name != "NotApplicable"){
			valuesType?.delete(flush: true)
			render(contentType: "text/json") {
				resp = "success"
				message = "ValuesType ${valuesType} has been successfully deleted"
			}
		}
		else{
			render(contentType: "text/json") {
				resp = "error"
				message = "Can't delete [NotApplicable] value type."
			}
		}
	}

	private String isNULL(String str, String dfl){
		return (null!=str)?str:dfl
	}
}

