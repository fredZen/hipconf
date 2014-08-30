package org.merizen.hipconf.user

import net.liftweb.http.{S, SHtml}
import net.liftweb.proto.{ProtoUser => UnderlyingProtoUser}
import net.liftweb.record.field.PasswordField
import net.liftweb.util.BaseField
import net.liftweb.util.Helpers._

import scala.xml.{Node, NodeSeq}

trait ProtoUser extends UnderlyingProtoUser {
  protected override def localForm(user: TheUserType, ignorePassword: Boolean, fields: List[FieldPointerType]): NodeSeq =
    for {
      pointer <- fields
      field <- computeFieldFromPointer(user, pointer).toList
      if field.show_? && (!ignorePassword || !pointer.isPasswordField_?)
      finalField <- toInputLines(field)
    } yield finalField

  private def toInputLines(field: BaseField): NodeSeq = field match {
    case field: PasswordField[_] =>
      val pwd = passwordInput(field)
      inputLine(field.displayName, pwd ++ messageForField(field)) ++ inputLine(S.?("repeat"), pwd)
    case _ =>
      for (form <- field.toForm.toList) yield inputLine(field.displayName, form ++ messageForField(field))
  }

  private def passwordInput(field: PasswordField[_]): Node = {
    SHtml.password_*(
      field.valueBox openOr "",
      (p: List[String]) => field.setFromAny(p),
      "tabindex" -> field.tabIndex.toString
    )
  }

  private def inputLine(displayName: String, form: NodeSeq): Node =
    <tr><td>{displayName}</td><td>{form}</td></tr>

  private def messageForField(field: BaseField): NodeSeq = (
    for {
      idField <- field.uniqueFieldId
    } yield <lift:Msg id={idField}>message</lift:Msg>).getOrElse(Nil)
}
