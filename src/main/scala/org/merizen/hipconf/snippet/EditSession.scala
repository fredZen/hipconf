package org.merizen.hipconf.snippet

import net.liftweb.http.LiftScreen
import org.merizen.hipconf.session.Session

class EditSession extends LiftScreen {
  addFields(Session.createRecord _)
  def finish() = ???
}
