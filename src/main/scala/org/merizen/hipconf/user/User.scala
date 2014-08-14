package org.merizen.hipconf.user

import net.liftweb.common.{Empty, Box}
import net.liftweb.record.field.LongField
import net.liftweb.record.{MegaProtoUser, MetaMegaProtoUser, MetaRecord}
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.util.FieldError
import org.merizen.hipconf.persistance.HipConfRepository
import org.squeryl.{KeyedEntity, Query}

class User extends MegaProtoUser[User] with KeyedEntity[LongField[User]] {
  override def meta: MetaRecord[User] = User

  lazy val sessions = HipConfRepository.sessionAuthors.right(this)

  override protected def valUnique(errorMsg: => String)(email: String): List[FieldError] =
    if(User.byEmail(email).isEmpty)
      Nil
    else
      List(FieldError(this.email, errorMsg))
}

object User extends User with MetaMegaProtoUser[User] {
  def byEmail(email: String): Query[User] =
    HipConfRepository.users.where(u => u.email === email)

  def byId(id: Long): Query[User] =
    HipConfRepository.users.where(u => u.id === id)

  override protected def findUserByUserName(email: String): Box[User] =
    byEmail(email).headOption

  override protected def userFromStringId(id: String): Box[User] = findUserByUniqueId(id)

  override protected def findUserByUniqueId(stringId: String): Box[User] =
    try {
      byId(stringId.toLong).headOption
    } catch {
      case _: NumberFormatException => Empty
    }
}
