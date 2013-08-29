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
			def file = grailsAttributes.getApplicationContext().getResource("uploadFiles/${fileName}").getFile()
			
			if (file.exists()) {
				def os = response.outputStream
				response.setHeader("Content-Type", "application/zip")
				response.setHeader("Content-disposition", "attachment;filename=${file.name}")
		
				def bytes = file.text.bytes
				for(b in bytes) {
				   os.write(b)
				}
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
			
			if (!multiRequest.isEmpty()) {
				def hospitalElement = HospitalElement.get(params.currentHospitalElement)
				def hospitalElementId = params?.currentHospitalElement
				hospitalElement.valueSetFile = "${hospitalElementId}_${multiRequest?.fileItem?.name}"
				hospitalElement.save(flush:true)
				
				def path = fileUploadService.uploadFile(multiRequest, "${hospitalElementId}_${multiRequest?.fileItem?.name}", "uploadFiles",false)
				if (path) {
					if (params?.currentHospitalElement && HospitalMeasure.exists(params.currentHospitalElement)) {
						hospitalElement = HospitalElement.get(params.currentHospitalElement)
						hospitalElement.valueSetFile = "${hospitalElementId}_${multiRequest?.fileItem?.name}"
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


