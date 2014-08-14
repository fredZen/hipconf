package org.merizen.hipconf.session

import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.record.field.{StringField, LongField}
import net.liftweb.squerylrecord.KeyedRecord
import org.merizen.hipconf.persistance.HipConfRepository

class Session extends Record[Session] with KeyedRecord[Long] {
  override def meta: MetaRecord[Session] = Session

  override val idField = new LongField(this)
  val title = new StringField(this, 100)
  lazy val authors = HipConfRepository.sessionAuthors.left(this)
}

object Session extends Session with MetaRecord[Session]
