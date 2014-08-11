package cucumber.selenium

import java.util.concurrent.TimeUnit._

import cucumber.api.Scenario
import cucumber.util.GlobalCucumberHooks
import org.openqa.selenium.OutputType._
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.selenium.WebBrowser

object WebStepDefinitions {
  private lazy val webDriver = new FirefoxDriver()
}

trait WebStepDefinitions extends WebBrowser with GlobalCucumberHooks {
  implicit def webDriver = WebStepDefinitions.webDriver

  BeforeAll {
    webDriver.manage.timeouts.implicitlyWait(10, SECONDS)
  }

  Before { _ =>
    webDriver.manage.deleteAllCookies()
  }

  After { scenario: Scenario =>
    embedScreenShotInReport(scenario)
  }

  private def embedScreenShotInReport(scenario: Scenario) =
    try {
      val screenShot = webDriver.getScreenshotAs(BYTES)
      scenario.embed(screenShot, "image/png")
    } catch {
      case somePlatformsDontSupportScreenShots: WebDriverException =>
        System.err.println(somePlatformsDontSupportScreenShots.getMessage)
    }

  AfterAll {
    webDriver.quit()
  }
}
