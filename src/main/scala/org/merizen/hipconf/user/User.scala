package org.merizen.hipconf.user

import net.liftweb.common.{Box, Full}
import net.liftweb.record.field.LongField
import net.liftweb.record.{MegaProtoUser, MetaMegaProtoUser}
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.util.FieldError
import net.liftweb.util.Helpers._
import org.merizen.hipconf.persistance.HipConfSchema.{sessionAuthors, users}
import org.squeryl.{KeyedEntity, Query}

import scala.xml.Node

class User extends MegaProtoUser[User] with KeyedEntity[LongField[User]]{
  override def meta = User

  lazy val sessions = sessionAuthors.right(this)

  override protected def valUnique(errorMsg: => String)(email: String): List[FieldError] =
    if (User.byEmail(email).isEmpty)
      Nil
    else
      List(FieldError(this.email, errorMsg))

  override def saveTheRecord(): Box[User] = Full(users.insertOrUpdate(this))
}

object User extends User with MetaMegaProtoUser[User] with ProtoUser {
  def byEmail(email: String): Query[User] =
    users.where(_.email === email)

  def byId(id: Long): Query[User] =
    users.where(_.id === id)

  override protected def findUserByUserName(email: String): Box[User] =
    byEmail(email).headOption

  override protected def userFromStringId(id: String): Box[User] = tryo {
    byId(id.toLong).single
  }

  override protected def findUserByUniqueId(uniqueId: String): Box[User] =
      users.where(_.uniqueId === uniqueId).headOption

  override def screenWrap: Box[Node] = Full(
    <lift:surround with="default" at="contents">
      <lift:bind/>
    </lift:surround>
  )
}
