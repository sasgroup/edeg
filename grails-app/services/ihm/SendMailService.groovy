package ihm

class SendMailService {
	
	def mailService
	
	static String reciverMail = "ihm.edeg.hospital@gmail.com"    	
	private ArrayList transferToList(String emails){
		String trimEmail = emails.trim()
		String [] sendTo  = trimEmail?.tokenize(";").toArray()
		ArrayList st = Arrays.asList(sendTo);
		st.add(reciverMail)
		return st
	}
	
	def assignProductToHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "New Product"
			body "The $productName product has been assigned to $hospitalName on $updateDate."
		}
    }
	
	def deAssignProductFromHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Product Has Been Removed"
			body "The $productName product has been removed from $hospitalName on $updateDate."
		}
	}	
	
	def updateHospitalConfig (String sendTo, String hospitalName, Date updateDate) {
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Hospital Configuration Has Been Updated"
			body "The hospital configuration for $hospitalName has been updated  on $updateDate"
		}
	}
	
	def includeMeasureIntoHospitalProduct (String sendTo, String hospitalName, String productName, String measureName, Date updateDate) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Measure added"
			body "The $measureName measure has been added to the $productName product for  $hospitalName on $updateDate."
		}
	}
	
	def markMeasureAsComplete (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Measure Completed"
			body "The $measureName measure has been marked complete in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def asseptMeasureThatCompleted (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Measure Accepted"
			body "The $measureName measure has been marked accepted in the $productName product for $hospitalName on $updateDate by $userName.  Once the data has been verified you will receive additional notification."
		}
	}
	
	def omissionUserIdentifie (String sendTo, String hospitalName, String productName, String measureName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Measure Needs Review"
			body "IHM has determined that the $measureName measure in the $productName product for $hospitalName needs additional review.  Please review and update the measure based on IHM comments."
		}
	}
	
	def verifieMeasure (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Measure Verified"
			body "The $measureName measure has been marked verified in the $productName product for $hospitalName on $updateDate by $userName."
		}
	}
	
	def updateDataElement (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray()
			subject "Data Element Has Been Updated"
			body "$userName has updated the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
	def attachFile (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendTo.add(reciverMail)
		mailService.sendMail {
			to transferToList(sendTo).toArray() 
			subject "File Attached to Data Element"
			body "$userName has attached a file for the $dataElement data element within $measureName for $hospitalName on $updateDate."
		}
	}
	
}
