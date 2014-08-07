package cucumber.stepdefinitions

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.junit.Assert.assertThat
import org.hamcrest.CoreMatchers.containsString

class HelloLiftDefinitions extends ScalaDsl with EN {
  val webdriver = new HtmlUnitDriver()

  Given("""^I am on the homepage$"""){ () =>
    webdriver.navigate.to("http://localhost:8080")
  }

  Then("""^The header should contain "(.*?)"$"""){ (arg0:String) =>
    assertThat(webdriver.findElementByCssSelector("h1").getText, containsString(arg0))
  }
}
