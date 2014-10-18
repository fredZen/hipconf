package bootstrap.liftweb

import javax.mail.{Authenticator, PasswordAuthentication}

import net.liftweb.util.{Mailer, Props}

object MailerConfiguration extends Configuration {
  def setup(): Unit = {
    Mailer.authenticator = for {
      user <- Props.get("mail.user")
      pass <- Props.get("mail.password")
    } yield new Authenticator {
        override def getPasswordAuthentication =
          new PasswordAuthentication(user, pass)
      }
  }
}
