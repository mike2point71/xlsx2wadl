@Grapes([
    @Grab("org.gebish:geb-core:0.9.0"),
    @Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.32.0"),
    @Grab("org.seleniumhq.selenium:selenium-support:2.32.0")
])
import geb.*

absPath = new File("").absolutePath
println absPath
String urlString = "file://" +  absPath + "/"
println urlString

class TestingPage extends Page {
  static url = "mk-xlsx1.html"
  static content = {
      tbody(required: false) { $("table", title: "Sheet1").find("tbody") }
  }
  def uploadFile(passPath, fileName) {
    $("form").fileUpload = passPath + "/testing_resources/" + fileName
    waitFor {tbody.present}
  }
  def checkColumn(colNumber, columnValues){
    tbody.children().eachWithIndex{tr, ndx->
      assert tr.children().filter("td")[colNumber].text() == columnValues[ndx]
    }
  }
}

Browser.drive {

  setBaseUrl urlString
  
  //TEST #001:  Load a sheet with 4 things in the first column.  Make sure they are there.
  to TestingPage
  uploadFile(absPath, "xlsxtest001.xlsx")
  answers = ['A','a','s','d','df']
  checkColumn(1, answers)

  //TEST #002:  Load a sheet with something out in the middle of nowhere.  Make sure there arent missing cells.
  to TestingPage
  uploadFile(absPath, "xlsxtest002.xlsx")
  answers = ['C','','','','nachos']
  checkColumn(3, answers)
  
  println "ALL ASSERTIONS PASSED!!!"

}.quit()