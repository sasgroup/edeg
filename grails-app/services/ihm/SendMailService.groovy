package ihm

class SendMailService {
	
	def mailService
	
	static String senderMail = "ihm.edeg.hospital@gmail.com"    	

	def assignProductToHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		mailService.sendMail {
			to senderMail, 
			subject "New Product"
			body "The $productName product has been assigned to $hospitalName on $updateDate."
		}
    	}
	
	def deAssignProductFromHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		mailService.sendMail {
			to senderMail
			subject "Product Has Been Removed"
			body "The $productName product has been removed from $hospitalName on $updateDate."
		}
	}	
	
	def updateHospitalConfig (String sendTo, String hospitalName, Date updateDate) {
		mailService.sendMail {
			to senderMail
			subject "Hospital Configuration Has Been Updated"
			body "The hospital configuration for $hospitalName has been updated  on $updateDate"
		}
	}
	
	def includeMeasureIntoHospitalProduct (String sendTo, String hospitalName, String productName, String measureName, Date updateDate) {
		mailService.sendMail {
			to senderMail
			subject "Measure added"
			body "The $measureName measure has been added to the $productName product for  $hospitalName on $updateDate."
		}
	}
	
	def markMeasureAsComplete (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		mailService.sendMail {
			to senderMail
			subject "Measure Completed"
			body "The $measureName measure has been marked complete in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def asseptMeasureThatCompleted (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		mailService.sendMail {
			to senderMail
			subject "Measure Accepted"
			body "The $measureName measure has been marked accepted in the $productName product for $hospitalName on $updateDate by $userName.  Once the data has been verified you will receive additional notification."
		}
	}
	
	def omissionUserIdentifie (String sendTo, String hospitalName, String productName, String measureName) {
		mailService.sendMail {
			to senderMail
			subject "Measure Needs Review"
			body "IHM has determined that the $measureName measure in the $productName product for $hospitalName needs additional review.  Please review and update the measure based on IHM comments."
		}
	}
	
	def verifieMeasure (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		mailService.sendMail {
			to senderMail
			subject "Measure Verified"
			body "The $measureName measure has been marked verified in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def updateDataElement (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		mailService.sendMail {
			to senderMail
			subject "Data Element Has Been Updated"
			body "$userName has updated the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
	def attachFile (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		mailService.sendMail {
			to senderMail
			subject "File Attached to Data Element"
			body "$userName has attached a file for the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
}
