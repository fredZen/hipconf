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
        inputLine(field.displayName, <user:pwd/> ++ messageForField(field)) ++
        inputLine(S.?("repeat"), <user:pwd/>)
      } else inputLine(field.displayName, form ++ messageForField(field))
    } yield finalField

  private def inputLine(displayName: String, form: NodeSeq): Node =
    <tr><td>{displayName}</td><td>{form}</td></tr>

  private def messageForField(field: BaseField): NodeSeq = (
    for {
      idField <- field.uniqueFieldId
    } yield <lift:Msg id={idField}>message</lift:Msg>).getOrElse(Nil)

  override def signup = {
    val theUser: TheUserType = mutateUserOnSignup(createNewUserInstance())

    def testSignup() {
      validateSignup(theUser) match {
        case Nil =>
          actionsAfterSignup(theUser, () => S.redirectTo(homePage))

        case xs => S.error(xs) ; signupFunc(Full(innerSignup _))
      }
    }

    def innerSignup = bind("user",
      signupXhtml(theUser),
      "pwd" -> SHtml.password_*("",(p: List[String]) =>
        theUser.setPasswordFromListString(p),
        "tabindex" -> "1"
      ),
      "submit" -> signupSubmitButton(S.?("sign.up"), testSignup))

    innerSignup
  }
}
