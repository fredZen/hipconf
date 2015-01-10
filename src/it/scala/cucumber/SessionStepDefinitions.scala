package cucumber

import cucumber.stepprototypes.SessionStepPrototypes
import net.liftweb.squerylrecord.RecordTypeMode._
import org.merizen.hipconf.persistance.HipConfSchema._
import org.merizen.hipconf.session.Session
import org.scalatest.ShouldMatchers

object SessionStepDefinitions extends SessionStepPrototypes with ShouldMatchers {
  var sessionUnderEdition: Option[Session] = None

  override def createSessionTitled(sessionTitle: String): Unit = inTransaction {
    val session = Session.createRecord
    session.title(sessionTitle)
    sessionUnderEdition = Some(session)
  }

  override def publishSession(): Unit = inTransaction {
    for(session <- sessionUnderEdition) {
      session.save
      session.authors.associate(AuthenticationStepDefinitions.currentUser.get)
    }
  }

  override def assertSessionVisible(sessionTitle: String, authorName: String): Unit = inTransaction {
    from(sessions, sessionAuthors, users)((s, a, u) =>
      where(s.id === a.sessionId and a.userId === u.id
        and u.firstName === authorName
        and s.title === sessionTitle)
        compute count
    ).single.measures should equal (1)
  }
}
