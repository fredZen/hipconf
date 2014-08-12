package cucumber.session

import cucumber.api.scala.EN
import cucumber.authentication.AuthenticationState
import cucumber.selenium.WebStepDefinitions
import org.scalatest.ShouldMatchers

class SessionStepDefinitions extends WebStepDefinitions with EN with ShouldMatchers{
  val sessions = List[Session]()

  When("""^I create a new session titled "(.*)"$"""){ (sessionTitle:String) =>
    SessionState.editing(Session(sessionTitle, AuthenticationState.currentUser.get))
  }

  When("""^I publish the session$"""){ () =>
    SessionState.publishCurrentSession()
  }

  Then("""^the session "(.*)" by (.*) is visible in the list of sessions$"""){ (sessionTitle:String, author: String) =>
    SessionState.publishedSessions should contain(Session(sessionTitle, author))
  }
}

case class Session(title: String, author: String)
