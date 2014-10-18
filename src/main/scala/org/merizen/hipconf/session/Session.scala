package org.merizen.hipconf.session

import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.record.field.{StringField, LongField}
import net.liftweb.squerylrecord.KeyedRecord
import org.merizen.hipconf.persistance.HipConfSchema
import org.squeryl.annotations.Column

class Session private() extends Record[Session] with KeyedRecord[Long] {
  override def meta: MetaRecord[Session] = Session

  @Column(name = "ID")
  override val idField = new LongField(this) {
    override def shouldDisplay_? : Boolean = false
  }
  val title = new StringField(this, 100)
  lazy val authors = HipConfSchema.sessionAuthors.left(this)
}

object Session extends Session with MetaRecord[Session]
