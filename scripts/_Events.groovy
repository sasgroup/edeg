import java.text.SimpleDateFormat

eventCompileStart = { kind ->
	def buildNumber = metadata.'app.buildNumber'
 
	if (!buildNumber)
		buildNumber = 1
	else
		buildNumber = Integer.valueOf(buildNumber) + 1
 
	metadata.'app.buildNumber' = buildNumber.toString()
 
	def formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm")
	def buildDate = formatter.format(new Date(System.currentTimeMillis()))
	metadata.'app.buildDate' = buildDate
	metadata.'app.buildProfile' = grailsEnv
	
	
	metadata.persist()
	
	
	eventConfigureTomcat = {tomcat ->
		tomcat.addUser("admin", "admin")
		tomcat.addRole("admin", "onboarding")
		tomcat.addUser("user", "user")
		tomcat.addRole("user", "onboarding")
		tomcat.addUser("test", "test")
		tomcat.addRole("test", "onboarding")
		
		tomcat.addUser("testadmin", "testadmin")
		tomcat.addRole("testadmin", "onboarding")
		tomcat.addUser("testuser1", "testuser1")
		tomcat.addRole("testuser1", "onboarding")
		tomcat.addUser("testuser2", "testuser2")
		tomcat.addRole("testuser2", "onboarding")
		tomcat.addUser("testuser3", "testuser3")
		tomcat.addRole("testuser3", "onboarding")
	}
	println "**** Compile Starting on Build #${buildNumber}"
}
