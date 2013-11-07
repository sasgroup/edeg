
/**
 * Running externalized configuration
 * Assuming the following configuration files
 * - in root of "C:" Config/[Config.groovy|DataSource.groovy]
 */
String separator = File.separator
println "looking files in: C:${separator}Config${separator}"
grails.config.locations = []
def defaultConfigFiles = ["C:${separator}Config${separator}Config.groovy",
  "C:${separator}Config${separator}DataSource.groovy"]
defaultConfigFiles.each { filePath ->
  def f = new File(filePath)
  if (f.exists()) {
	grails.config.locations << "file:${filePath}"
  } else {
  }
}
String bashSafeEnvAppName = appName.toUpperCase(Locale.ENGLISH).replaceAll(/-/, '_')

def externalConfig = System.getenv("${bashSafeEnvAppName}_CONFIG_LOCATION")
if (externalConfig) {
  grails.config.locations << "file:" + externalConfig
}
def externalDataSource = System.getenv("${bashSafeEnvAppName}_DATASOURCE_LOCATION")
if (externalDataSource) {
  grails.config.locations << "file:" + externalDataSource
}
grails.config.locations.each {
  println "[INFO] Including configuration file [${it}] in configuration building."
}

