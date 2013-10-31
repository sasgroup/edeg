package ihm_demo

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.commons.io.FileUtils;

class FileController {
	def sendMailService
	def fileUploadService

	def show= {
		def file
		if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {//check if HospitalElement available 
			//find hospitalElement and get file name
			def hospitalElement = HospitalElement.get(params.currentHospitalElement)
			def fileName = hospitalElement.valueSetFile
			file = grailsAttributes.getApplicationContext().getResource("uploadFiles/${fileName}").getFile()
		}

		if (params?.fileName) {//load file from folder
			file = grailsAttributes.getApplicationContext().getResource("uploadFiles/${params.fileName}").getFile()
		}

		if (file && file.exists()) {//send file in response
			def os = response.outputStream
			response.setHeader("Content-disposition", "attachment;filename=${file.name}")
			def bytes = file.bytes
			os << bytes
			os.flush()
			org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes().renderView = false

		} else {
			render "file not found"
		}
	}

	def save= {
		if (!(request instanceof MultipartHttpServletRequest)) {//check if correct request
			println("no multipart")
		}
		if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {//check if HospitalElement available
			def multiRequest = request.getFile("fileToUpload")
			//check file param
			if (!multiRequest.isEmpty()) {
				String fileName="${multiRequest?.fileItem?.name}"
				//replace all "bad" char
				fileName = fileName.replaceAll(" ", "_")
				fileName = fileName.replaceAll("-", "_")
				if (fileName.contains("\\")  ||  fileName.contains("/") ) {
					def firstIndex = fileName.findLastIndexOf { it == "\\" }
					fileName = fileName.substring(firstIndex+1)

				}
				def hospitalElement = HospitalElement.get(params.currentHospitalElement)
				def hospitalElementId = params?.currentHospitalElement
				//upload FIle to system
				def path = fileUploadService.uploadFile(multiRequest, "${hospitalElementId}_${fileName}", "uploadFiles",false)
				if (path) {
					if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {
						hospitalElement = HospitalElement.get(params.currentHospitalElement)
						hospitalElement.valueSetFile = "${hospitalElementId}_${fileName}"
						hospitalElement.save(flush :true)
					}
				}
				sendMailService.attachFile(hospitalElement?.hospital.email, hospitalElement?.hospital.name, hospitalElement?.dataElement.name, HospitalMeasure.get(params?.currentMeasureId)?.measure.name, new Date(), session?.user.login)
				//for IE7(but need render as JSON)
				render "ok;$path;$hospitalElement.version"
			} else {
				render "error;empty;"
			}
		}
	}

	def delete= {
		if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {
			def hospitalElement = HospitalElement.get(params.currentHospitalElement)
			// find hospitalElement and get file name
			def fileName = hospitalElement.valueSetFile
			hospitalElement.valueSetFile = ""
			hospitalElement.save(flush:true)

			def res = fileUploadService.uploadFile(null, "${fileName}", "uploadFiles",true)
			//for IE7(but need render as JSON)
			render "ok;$res;$hospitalElement.version"
		} else {
			render "error;some error;"
		}
	}
}