package cucumber.integration

import cucumber.stepprototypes.AuthenticationStepPrototypes
import org.merizen.hipconf.user.User
import net.liftweb.squerylrecord.RecordTypeMode._
import org.merizen.hipconf.persistance.HipConfRepository._

object AuthenticationStepDefinitions extends AuthenticationStepPrototypes {
  var currentUser: Option[User] = None

  override def userIsLoggedIn(userName: String): Unit = inTransaction {
    val user = new User
    user.firstName(userName)
    user.save
    currentUser = Some(user)
  }
}
