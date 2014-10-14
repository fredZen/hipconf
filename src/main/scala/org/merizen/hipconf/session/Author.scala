package org.merizen.hipconf.session

import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.record.field.LongField
import org.squeryl.KeyedEntity
import org.squeryl.annotations._
import org.squeryl.dsl.CompositeKey2
import net.liftweb.squerylrecord.RecordTypeMode._

class Author extends Record[Author] with KeyedEntity[CompositeKey2[LongField[Author], LongField[Author]]] {
  override def meta: MetaRecord[Author] = Author

  override def id = compositeKey(sessionId, userId)
  @Column(name = "SESSION_ID")
  val sessionId = new LongField(this)
  @Column(name = "USER_ID")
  val userId = new LongField(this)
}

object Author extends Author with MetaRecord[Author]
