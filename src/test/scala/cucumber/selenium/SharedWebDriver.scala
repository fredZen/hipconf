package cucumber.selenium

import java.util.concurrent.TimeUnit._

import cucumber.util.GlobalCucumberHooks
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.selenium.WebBrowser

object SharedWebDriver {
  private lazy val webDriver = new FirefoxDriver()
}

trait SharedWebDriver extends WebBrowser with GlobalCucumberHooks {
  implicit def webDriver = SharedWebDriver.webDriver

  BeforeAll {
    webDriver.manage.timeouts.implicitlyWait(10, SECONDS)
  }

  Before { _ =>
    webDriver.manage.deleteAllCookies()
  }

  AfterAll {
    webDriver.quit()
  }
}
