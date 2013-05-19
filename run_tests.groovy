@Grapes([
    @Grab("org.gebish:geb-core:0.9.0"),
    @Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.32.0"),
    @Grab("org.seleniumhq.selenium:selenium-support:2.32.0")
])
import geb.Browser
absPath = new File("").absolutePath
println absPath
Browser.drive {
  go "file://" +  absPath + "/mk-xlsx1.html"
  $("form").fileUpload = absPath + "/testing_resources/xlsxtest1.xlsx"

}