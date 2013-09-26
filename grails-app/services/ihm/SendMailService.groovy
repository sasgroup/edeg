package ihm

import grails.plugin.asyncmail.AsynchronousMailService

class SendMailService {
	
	def grailsApplication
	AsynchronousMailService asyncMailService
	
	private void sendMail(String sendTo, String subjectM, String bodyM){
		asyncMailService.sendMail {
		    to transferToList(sendTo).toArray()
		    subject "$subjectM"
			body "$bodyM"
		}
	}
	
	
	private ArrayList transferToList(String emails){
		String trimEmail = emails.trim()
		String [] sendTo  = trimEmail?.tokenize(";").toArray()
		ArrayList st = Arrays.asList(sendTo);
		st.add(grailsApplication.mainContext.servletContext.getInitParameter("adminEmail"))
		return st
	}
	
	def assignProductToHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		sendMail ( sendTo, "New Product", "The $productName product has been assigned to $hospitalName on $updateDate.")
    }
	
	def deAssignProductFromHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		sendMail (sendTo, "Product Has Been Removed", "The $productName product has been removed from $hospitalName on $updateDate.")
	}	
	
	def updateHospitalConfig (String sendTo, String hospitalName, Date updateDate) {
		sendMail(sendTo,"Hospital Configuration Has Been Updated","The hospital configuration for $hospitalName has been updated  on $updateDate")
	}
	
	def includeMeasureIntoHospitalProduct (String sendTo, String hospitalName, String productName, String measureName, Date updateDate) {
		sendMail (sendTo, "Measure added", "The $measureName measure has been added to the $productName product for  $hospitalName on $updateDate.")
	}
	
	def markMeasureAsComplete (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Completed", "The $measureName measure has been marked complete in the $productName product for $hospitalName on $updateDate by $userName.")
	}
	
	def asseptMeasureThatCompleted (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Accepted", "The $measureName measure has been marked accepted in the $productName product for $hospitalName on $updateDate by $userName.  Once the data has been verified you will receive additional notification.")
	}
	
	def omissionUserIdentifie (String sendTo, String hospitalName, String productName, String measureName) {
		sendMail (sendTo, "Measure Needs Review", "IHM has determined that the $measureName measure in the $productName product for $hospitalName needs additional review.  Please review and update the measure based on IHM comments.")
	}
	
	def verifieMeasure (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Verified", "The $measureName measure has been marked verified in the $productName product for $hospitalName on $updateDate by $userName.")
	}
	
	def updateDataElement (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Data Element Has Been Updated", "$userName has updated the $dataElement data element within $measureName for $hospitalName on $updateDate.")
	}
	
	def attachFile (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "File Attached to Data Element", "$userName has attached a file for the $dataElement data element within $measureName for $hospitalName on $updateDate.")
	}
	
}
