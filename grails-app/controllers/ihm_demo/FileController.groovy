package ihm_demo

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.apache.commons.io.FileUtils;

class FileController {
	
	def fileUploadService
    
	def show= {
		def hospitalElementId = params?.currentHospitalElement
		//find hospitalElement and get file name
		def fileName = "test.txt"
		def file = grailsAttributes.getApplicationContext().getResource("uploadFiles/${hospitalElementId}_${fileName}").getFile()
		
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
	}
	def save= {  
		println("go")
	   if (!(request instanceof MultipartHttpServletRequest)) {
		   println("no multipart")
		 }
	    
	   def hospitalElementId = params?.currentHospitalElement
	   
		def multiRequest = request.getFile("fileToUpload")
		if (!multiRequest.isEmpty()) {
			def path =fileUploadService.uploadFile(multiRequest, "${hospitalElementId}_${multiRequest?.fileItem?.name}", "uploadFiles")
			render "$path"
		} else {
			render "empty"
		}
	}
	
	
}
