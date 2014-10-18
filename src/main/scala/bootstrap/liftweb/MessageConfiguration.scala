package bootstrap.liftweb

import net.liftweb.common.{Empty, Full}
import net.liftweb.http.NoticeType.Value
import net.liftweb.http.{LiftRules, NoticeType}
import net.liftweb.util.Helpers._
import net.liftweb.util.Vendor._

object MessageConfiguration extends Configuration {
  override def setup(): Unit = {
    LiftRules.noticesAutoFadeOut.default.set((_: Value) match {
      case NoticeType.Notice => Full((2.seconds, 2.seconds))
      case _ => Empty
    })
  }
}
