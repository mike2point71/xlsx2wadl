@Grapes([
    @Grab("org.gebish:geb-core:0.9.0"),
    @Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.32.0"),
    @Grab("org.seleniumhq.selenium:selenium-support:2.32.0")
])
import geb.Browser
absPath = new File("").absolutePath
println absPath

Browser.drive {
  //TEST #001:  Load a sheet with 4 things in the first column.  Make sure they are there.
  go "file://" +  absPath + "/mk-xlsx1.html"
  $("form").fileUpload = absPath + "/testing_resources/xlsxtest001.xlsx"
  waitFor{ $("table", title: "Sheet1") }
  tbody = $("table", title: "Sheet1").find("tbody")
  answers = ['A','a','s','d','df']
  tbody.children().eachWithIndex{tr, ndx->
    assert tr.find("td").next("td").text() == answers[ndx]
  }

  //TEST #002:  Load a sheet with something out in the middle of nowhere.  Make sure there arent missing cells.
  go "file://" +  absPath + "/mk-xlsx1.html"
  $("form").fileUpload = absPath + "/testing_resources/xlsxtest002.xlsx"
  waitFor{ $("table", title: "Sheet1") }
  tbody = $("table", title: "Sheet1").find("tbody")
  answers = ['C','','','','nachos']
  tbody.children().eachWithIndex{tr, ndx->
    assert tr.find("td").next("td").next("td").next("td").text() == answers[ndx]
  }
  println "ALL ASSERTIONS PASSED!!!"
}.quit()