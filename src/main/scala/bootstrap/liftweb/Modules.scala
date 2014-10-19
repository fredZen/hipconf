package bootstrap.liftweb

import org.merizen.hipconf._
import org.merizen.hipconf.util.HipConfModule

trait Modules {
  val modules: Seq[HipConfModule] = Seq(user.Module)
}
