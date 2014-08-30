package org.merizen.hipconf.user

import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.record.MegaProtoUser
import net.liftweb.record.field.LongField
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.util.FieldError
import org.merizen.hipconf.persistance.HipConfRepository
import org.merizen.hipconf.persistance.HipConfRepository._
import org.merizen.protouser.MetaMegaProtoUser
import org.squeryl.{KeyedEntity, Query}
import net.liftweb.util.Helpers._

import scala.xml.Node

class User extends MegaProtoUser[User] with KeyedEntity[LongField[User]] {
  override def meta = User

  lazy val sessions = HipConfRepository.sessionAuthors.right(this)

  override protected def valUnique(errorMsg: => String)(email: String): List[FieldError] =
    if (User.byEmail(email).isEmpty)
      Nil
    else
      List(FieldError(this.email, errorMsg))

  override def saveTheRecord(): Box[User] = Full(users.insertOrUpdate(this))
}

object User extends User with MetaMegaProtoUser[User] {
  def byEmail(email: String): Query[User] =
    HipConfRepository.users.where(_.email === email)

  def byId(id: Long): Query[User] =
    HipConfRepository.users.where(_.id === id)

  override protected def findUserByUserName(email: String): Box[User] =
    byEmail(email).headOption

  override protected def userFromStringId(id: String): Box[User] = tryo {
    byId(id.toLong).single
  }

  override protected def findUserByUniqueId(uniqueId: String): Box[User] =
      HipConfRepository.users.where(_.uniqueId === uniqueId).headOption

  override def screenWrap: Box[Node] = Full(
    <lift:surround with="default" at="contents">
      <lift:bind/>
    </lift:surround>
  )
}
