package org.merizen.hipconf.session

import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.record.field.LongField
import org.squeryl.KeyedEntity
import org.squeryl.annotations._
import org.squeryl.dsl.CompositeKey2
import net.liftweb.squerylrecord.RecordTypeMode._

class SessionAuthor private() extends Record[SessionAuthor] with KeyedEntity[CompositeKey2[LongField[SessionAuthor], LongField[SessionAuthor]]] {
  override def meta: MetaRecord[SessionAuthor] = SessionAuthor

  override def id = compositeKey(sessionId, userId)
  @Column(name = "SESSION_ID")
  val sessionId = new LongField(this)
  @Column(name = "USER_ID")
  val userId = new LongField(this)
}

object SessionAuthor extends SessionAuthor with MetaRecord[SessionAuthor]
