package org.merizen.hipconf.persistance

import org.merizen.hipconf.session.{Author, Session}
import org.merizen.hipconf.user.User
import org.squeryl.Schema
import net.liftweb.squerylrecord.RecordTypeMode._

object HipConfRepository extends Schema {
  val users = table[User]
  val sessions = table[Session]
  val sessionAuthors =
    manyToManyRelation(sessions, users).
    via[Author] { (session, user, author) =>
      (author.sessionId === session.id, author.userId === user.id)
    }
}