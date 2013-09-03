package ihm_demo

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.commons.io.FileUtils;

class FileController {

	def fileUploadService
	
	def show= {

		if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {
			def hospitalElement = HospitalElement.get(params.currentHospitalElement)
			//find hospitalElement and get file name
			def fileName = hospitalElement.valueSetFile
			println fileName
			def file = grailsAttributes.getApplicationContext().getResource("uploadFiles/${fileName}").getFile()

			if (file.exists()) {
				def os = response.outputStream
				response.setHeader("Content-disposition", "attachment;filename=${file.name}")
				def bytes = file.bytes
				os << bytes
				os.flush()
				org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes().renderView = false
			}
		} else {
			render "file not found"
		}
	}

	def save= {
		println("go")
	   if (!(request instanceof MultipartHttpServletRequest)) {
		   println("no multipart")
		 }

	   if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {
			def multiRequest = request.getFile("fileToUpload")
			println multiRequest.getClass()
			if (!multiRequest.isEmpty()) {
				String fileName="${multiRequest?.fileItem?.name}"

				if (fileName.contains("\\")  ||  fileName.contains("/") )
					fileName = fileName.find(~/\w*\.\w*/)

				def hospitalElement = HospitalElement.get(params.currentHospitalElement)
				def hospitalElementId = params?.currentHospitalElement

				def path = fileUploadService.uploadFile(multiRequest, "${hospitalElementId}_${fileName}", "uploadFiles",false)
				if (path) {
					if (params?.currentHospitalElement && HospitalMeasure.exists(params.currentHospitalElement)) {
						hospitalElement = HospitalElement.get(params.currentHospitalElement)
						hospitalElement.valueSetFile = "${hospitalElementId}_${fileName}"
						hospitalElement.save(flush :true)
					}
				}
				render "$path"
			} else {
				render "empty"
			}
	   }
	}

	def delete= {
		println "delete"
		if (params?.currentHospitalElement && HospitalElement.exists(params.currentHospitalElement)) {
			def hospitalElement = HospitalElement.get(params.currentHospitalElement)
			//	find hospitalElement and get file name
			def fileName = hospitalElement.valueSetFile
			hospitalElement.valueSetFile = ""
			hospitalElement.save(flush:true)

			def res = fileUploadService.uploadFile(null, "${fileName}", "uploadFiles",true)
			render "$res"
		}	else {
			render "some errror"
		}
	}
}
