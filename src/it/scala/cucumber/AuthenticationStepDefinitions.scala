package cucumber

import cucumber.stepprototypes.AuthenticationStepPrototypes
import net.liftweb.squerylrecord.RecordTypeMode._
import org.merizen.hipconf.persistance.HipConfSchema._
import org.merizen.hipconf.user.User

object AuthenticationStepDefinitions extends AuthenticationStepPrototypes {
  var currentUser: Option[User] = None

  override def userIsLoggedIn(userName: String): Unit = inTransaction {
    val user = User.createRecord
    user.firstName(userName)
    user.save
    currentUser = Some(user)
  }
}
