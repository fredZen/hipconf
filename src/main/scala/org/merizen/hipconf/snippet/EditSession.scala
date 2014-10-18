package org.merizen.hipconf.snippet

import net.liftweb.http.{S, LiftScreen}

class EditSession extends LiftScreen {
  val flavor = field(S ? "What's your favorite Ice cream flavor", "",
    trim,
    valMinLen(2, "Name too short"),
    valMaxLen(40, "That's a long name"))
  override def finish() {
    S.notice("I like "+flavor.is+" too!")
  }
}
