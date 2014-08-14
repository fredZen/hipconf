package cucumber.stepprototypes

import cucumber.api.scala.{EN, ScalaDsl}

trait SessionStepPrototypes extends ScalaDsl with EN {
  When("""^I create a new session titled "(.*)"$""")(createSessionTitled _)

  def createSessionTitled(sessionTitle:String): Unit

  When("""^I publish the session$""")(publishSession _)

  def publishSession(): Unit

  Then("""^the session "(.*)" by (.*) is visible in the list of sessions$""")(assertSessionVisible _)

  def assertSessionVisible(sessionTitle:String, authorName: String)
}
