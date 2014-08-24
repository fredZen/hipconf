package org.merizen.protouser

import net.liftweb.common.Full
import net.liftweb.http.{S, SHtml}
import net.liftweb.proto.{ProtoUser => UnderlyingProtoUser}
import net.liftweb.util.Helpers._

trait ProtoUser extends UnderlyingProtoUser {
  override def signupXhtml(user: TheUserType) = {
    <form method="post" action={S.uri}><table><tr><td
    colspan="2">{ S.?("sign.up") }</td></tr>
      {localForm(user, ignorePassword = true, signupFields)}
      <tr><td>{ S.?("password") }</td><td><user:pwd/></td></tr>
      <tr><td>{ S.?("repeat") }</td><td><user:pwd/></td></tr>
      <tr><td>&nbsp;</td><td><user:submit/></td></tr>
    </table></form>
  }

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
