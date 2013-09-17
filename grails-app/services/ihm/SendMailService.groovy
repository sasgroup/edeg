package ihm

class SendMailService {
	
	def mailService
	
	static String reciverMail = "ihm.edeg.hospital@gmail.com"    	

	def assignProductToHospital(ArrayList sendTo, String hospitalName, String productName, Date updateDate) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo, 
			subject "New Product"
			body "The $productName product has been assigned to $hospitalName on $updateDate."
		}
    }
	
	def deAssignProductFromHospital(ArrayList sendTo, String hospitalName, String productName, Date updateDate) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Product Has Been Removed"
			body "The $productName product has been removed from $hospitalName on $updateDate."
		}
	}	
	
	def updateHospitalConfig (ArrayList sendTo, String hospitalName, Date updateDate) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to sendTo.toArray()
			subject "Hospital Configuration Has Been Updated"
			body "The hospital configuration for $hospitalName has been updated  on $updateDate"
		}
	}
	
	def includeMeasureIntoHospitalProduct (ArrayList sendTo, String hospitalName, String productName, String measureName, Date updateDate) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Measure added"
			body "The $measureName measure has been added to the $productName product for  $hospitalName on $updateDate."
		}
	}
	
	def markMeasureAsComplete (ArrayList sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Measure Completed"
			body "The $measureName measure has been marked complete in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def asseptMeasureThatCompleted (ArrayList sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Measure Accepted"
			body "The $measureName measure has been marked accepted in the $productName product for $hospitalName on $updateDate by $userName.  Once the data has been verified you will receive additional notification."
		}
	}
	
	def omissionUserIdentifie (ArrayList sendTo, String hospitalName, String productName, String measureName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Measure Needs Review"
			body "IHM has determined that the $measureName measure in the $productName product for $hospitalName needs additional review.  Please review and update the measure based on IHM comments."
		}
	}
	
	def verifieMeasure (ArrayList sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Measure Verified"
			body "The $measureName measure has been marked verified in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def updateDataElement (ArrayList sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo
			subject "Data Element Has Been Updated"
			body "$userName has updated the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
	def attachFile (ArrayList sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to reciverMail, sendTo 
			subject "File Attached to Data Element"
			body "$userName has attached a file for the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
}
