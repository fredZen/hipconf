package cucumber.steps

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

object StartStopSeleniumHooks extends GlobalCucumberHooks {
  var selenium: WebDriver = _

  BeforeAll {
    selenium = new HtmlUnitDriver()
    selenium.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
  }

  AfterAll {
    selenium.close()
  }
}
