package ihm_demo

class SecurityController {
	def securityService

	def show() {
		String curHospitalName = securityService.getCurrentHospital(request.getRemoteUser())
		String id = "-1"
		def result = Hospital.findByName(curHospitalName)
		if(result)
	  		id = result.id
			  
		println	  securityService.getHospitalNameMapCurrentId(request.getRemoteUser())
	  	render(contentType: "text/json") {
			curHospitalId =  id
			curHospital =  curHospitalName
			availableHospitals       = securityService.getHospitalNameMapCurrentId(request.getRemoteUser())
			curUser =  request.getRemoteUser()
			curRole = securityService.getRole(request.getRemoteUser())
		}
	}

	def update(Long id, Long version) {
		if (params.curHospital) {
			setCurrentHospital.setCurrentHospital(request.getRemoteUser(), params.curHospital)
		}
		
		
		render(contentType: "text/json") {
			curHospital = securityService.getCurrentHospital(request.getRemoteUser())
			availableHospitals       = securityService.getHospitalNameMap(request.getRemoteUser())
		}
	}
}