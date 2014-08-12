package cucumber.authentication

import cucumber.api.scala.EN
import cucumber.selenium.WebStepDefinitions

class AuthenticationStepDefinitions extends WebStepDefinitions with EN {
  Given("""^I am logged in as (.*)$"""){ (userName: String) =>
    AuthenticationState.login(userName)
  }
}
