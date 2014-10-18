package bootstrap.liftweb

import bootstrap.liftweb.database.DatabaseConfiguration
import net.liftweb.http._
import net.liftweb.util.Vendor._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot(): Unit = {
    LiftRules.addToPackages("org.merizen.hipconf")
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
    for(configuration <- List(MessageConfiguration, MailerConfiguration, DatabaseConfiguration, SiteMapConfiguration)) {
      configuration.setup()
    }
  }
}
