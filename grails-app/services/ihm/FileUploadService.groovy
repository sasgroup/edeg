package ihm

import org.springframework.web.multipart.MultipartFile
import org.codehaus.groovy.grails.web.context.*

class FileUploadService {
	
	def String uploadFile(MultipartFile file, String name, String destinationDirectory,boolean isDelet) {
		def servletContext = ServletContextHolder.servletContext
		def storagePath = servletContext.getRealPath(destinationDirectory)
		//Create storage path directory if it does not exist
		def storagePathDirectory = new File(storagePath)
		if (!storagePathDirectory.exists()) {
			println "Creating DIRECTORY ${storagePath}:"
			if (storagePathDirectory.mkdirs()) {
				println "SUCCESS"
			} else {
				println "FAILED"
			}
		}	
		
		def storedFile = new File("${storagePath}/${name}")
		
		if (!isDelet) {
			//Store file
			if(file && !file.isEmpty()) {
				
				
				if (!storedFile.exists()) {
					file.transferTo(new File("${storagePath}/${name}"))
					println "SAved file: ${storagePath}/${name}"
					return "${storagePath}/${name}"
				} else {
					println "File Exist"
					return null
				}	
			} else {
				println "File ${file.inspect()} was empty"
				return null
			}
		} else {
			if (storedFile.exists() && storedFile.delete()) {
				return "deleted" 
			} else {
				return null
			}
		}	
    }
}
