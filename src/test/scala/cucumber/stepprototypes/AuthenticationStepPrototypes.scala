package cucumber.stepprototypes

import cucumber.api.scala.{ScalaDsl, EN}

trait AuthenticationStepPrototypes extends ScalaDsl with EN {
  Given("""^I am logged in as (.*)$""")(userIsLoggedIn _)

  def userIsLoggedIn(userName:String): Unit
}
