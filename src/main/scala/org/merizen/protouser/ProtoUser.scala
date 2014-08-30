package org.merizen.protouser

import net.liftweb.common.Full
import net.liftweb.http.{S, SHtml}
import net.liftweb.proto.{ProtoUser => UnderlyingProtoUser}
import net.liftweb.util.BaseField
import net.liftweb.util.Helpers._

import scala.xml.{Node, NodeSeq}

trait ProtoUser extends UnderlyingProtoUser {

  protected override def localForm(user: TheUserType, ignorePassword: Boolean, fields: List[FieldPointerType]): NodeSeq =
    for {
      pointer <- fields
      field <- computeFieldFromPointer(user, pointer).toList
      if field.show_? && (!ignorePassword || !pointer.isPasswordField_?)
      form <- field.toForm.toList
      finalField <- if(pointer.isPasswordField_?) {
        val pwd = SHtml.password_*("",(p: List[String]) =>
          user.setPasswordFromListString(p),
          "tabindex" -> "1"
        )

        inputLine(field.displayName, pwd ++ messageForField(field)) ++
        inputLine(S.?("repeat"), pwd)
      } else inputLine(field.displayName, form ++ messageForField(field))
    } yield finalField

  private def inputLine(displayName: String, form: NodeSeq): Node =
    <tr><td>{displayName}</td><td>{form}</td></tr>

  private def messageForField(field: BaseField): NodeSeq = (
    for {
      idField <- field.uniqueFieldId
    } yield <lift:Msg id={idField}>message</lift:Msg>).getOrElse(Nil)
}
