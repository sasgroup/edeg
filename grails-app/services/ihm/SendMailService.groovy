package ihm

import grails.plugin.asyncmail.AsynchronousMailService

class SendMailService {
	
	def grailsApplication
	AsynchronousMailService asyncMailService
	
	private void sendMail(String sendTo, String subjectM, String bodyM){
		def recipients = sendTo 
		while (recipients.indexOf("  ")>=0)
			recipients = recipients.replaceAll("  ", " ")
		recipients = recipients.replaceAll(" ", ",")
		while (recipients.indexOf(",,")>=0)
			recipients = recipients.replaceAll(",,", ",")
		
		asyncMailService.sendMail {
		    to transferToList(recipients).toArray()
		    subject "$subjectM"
			html "$bodyM"
		}
	}
	
	
	private ArrayList transferToList(String emails){
		String trimEmail = emails.trim()
		String [] sendTo  = trimEmail?.tokenize(",").toArray()
		ArrayList st = Arrays.asList(sendTo);
		st.add(grailsApplication.mainContext.servletContext.getInitParameter("adminEmail"))
		return st
	}
	
	def assignProductToHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		sendMail ( sendTo, "New Product", "The <b>$productName</b> product has been assigned to <b>$hospitalName</b> on <b>$updateDate</b>.")
    }
	
	def deAssignProductFromHospital(String sendTo, String hospitalName, String productName, Date updateDate) {
		sendMail (sendTo, "Product Has Been Removed", "The <b>$productName</b> product has been removed from <b>$hospitalName</b> on <b>$updateDate</b>.")
	}	
	
	def updateHospitalConfig (String sendTo, String hospitalName, Date updateDate) {
		sendMail(sendTo,"Hospital Configuration Has Been Updated","The hospital configuration for <b>$hospitalName</b> has been updated on <b>$updateDate</b>")
	}
	
	def includeMeasureIntoHospitalProduct (String sendTo, String hospitalName, String productName, String measureName, Date updateDate) {
		sendMail (sendTo, "Measure added", "The <b>$measureName</b> measure has been added to the <b>$productName</b> product for  <b>$hospitalName</b> on <b>$updateDate</b>.")
	}
	
	def markMeasureAsComplete (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Completed", "The <b>$measureName</b> measure has been marked complete in the <b>$productName</b> product for <b>$hospitalName</b> on <b>$updateDate</b> by <b>$userName</b>.")
	}
	
	def asseptMeasureThatCompleted (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Accepted", "The <b>$measureName</b> measure has been marked accepted in the <b>$productName</b> product for <b>$hospitalName</b> on <b>$updateDate</b> by <b>$userName</b>.<br /><br />Once the data has been verified you will receive additional notification.")
	}
	
	def omissionUserIdentifie (String sendTo, String hospitalName, String productName, String measureName) {
		sendMail (sendTo, "Measure Needs Review", "IHM has determined that the <b>$measureName</b> measure in the <b>$productName</b> product for <b>$hospitalName</b> needs additional review.<br /><br />Please review and update the measure based on IHM comments.")
	}
	
	def verifieMeasure (String sendTo, String hospitalName, String productName, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Measure Verified", "The <b>$measureName</b> measure has been marked verified in the <b>$productName</b> product for <b>$hospitalName</b> on <b>$updateDate</b> by <b>$userName</b>.")
	}
	
	def updateDataElement (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "Data Element Has Been Updated", "<b>$userName</b> has updated the <b>$dataElement</b> data element within <b>$measureName</b> for <b>$hospitalName</b> on <b>$updateDate</b>.")
	}
	
	def attachFile (String sendTo, String hospitalName, String dataElement, String measureName, Date updateDate, String userName) {
		sendMail (sendTo, "File Attached to Data Element", "<b>$userName</b> has attached a file for the <b>$dataElement</b> data element within <b>$measureName</b> for <b>$hospitalName</b> on <b>$updateDate</b>.")
	}
	
	def hospitalProductNotesUpdated (String sendTo, String hospitalName, productName, Date updateDate, String userName) {
		sendMail (sendTo, "New Product Comments", "<b>$userName</b> has commented on the <b>$productName</b> product for hospital <b>$hospitalName</b> on <b>$updateDate</b>.")
	}
	def hospitalMeasureNotesUpdated (String sendTo, String hospitalName, measureName, Date updateDate, String userName) {
		sendMail (sendTo, "New Measure Comments", "<b>$userName</b> has commented on the <b>$measureName</b> measure for hospital <b>$hospitalName</b> on <b>$updateDate</b>.")
	}
	def hospitalOptionsCloned (String sendTo, String hospitalName, String srcHospitalName, Date updateDate, String userName) {
		sendMail (sendTo, "Hospital Configuration Cloned", "Configuration for <b>$hospitalName</b> has been copied from <b>$srcHospitalName</b> on <b>$updateDate</b> by <b>$userName</b>.")
		
	}
	
	
}
