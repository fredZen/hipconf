package cucumber.stepdefinitions

import cucumber.api.scala.EN
import cucumber.selenium.SharedWebDriver
import org.scalatest.ShouldMatchers

class HelloLiftDefinitions extends SharedWebDriver with EN with ShouldMatchers {
  Given("""^I am on the homepage$"""){ () =>
    go to "http://localhost:8080"
  }

  Then("""^The header should contain "(.*?)"$"""){ (arg0:String) =>
    cssSelector("h1").element.text should include(arg0)
  }
}
