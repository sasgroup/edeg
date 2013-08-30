package ihm_demo

class SecurityController {
	def securityService
	
    def show() { 
		render(contentType: "text/json") {
			curHospital = securityService.getCurrentHospital(request.getRemoteUser())
			availableHospitals       = securityService.getHospitalNameMap(request.getRemoteUser())
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
