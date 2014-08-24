package bootstrap.liftweb

import java.sql.DriverManager

import net.liftmodules.JQueryModule
import net.liftweb.http.{Html5Properties, LiftRules, Req, S}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.squerylrecord.SquerylRecord
import net.liftweb.util.{LiftFlowOfControlException, LoanWrapper}
import org.merizen.hipconf.persistance.HipConfRepository
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot(): Unit = {
    LiftRules.addToPackages("org.merizen.hipconf.snippet")
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
    setupDatabase()
    LiftRules.setSiteMap(siteMap)
    provideJQuery()
  }

  def siteMap(): SiteMap = SiteMap(
    Menu.i("Home") / "index"
  )

  private def setupDatabase(): Unit = {
    connectToDatabase()
    wrapAllRequestsInTransaction()
    inTransaction{
      HipConfRepository.create
    }
    
    def connectToDatabase(): Unit = {
      Class.forName("org.h2.Driver")
      def connection = DriverManager.getConnection(
        "jdbc:h2:mem:hipconf;DB_CLOSE_DELAY=-1",
        "sa", "")
      SquerylRecord.initWithSquerylSession(Session.create(connection, new H2Adapter))
    }

    def wrapAllRequestsInTransaction(): Unit = {
      S.addAround(new LoanWrapper {
        override def apply[T](f: => T): T = {
          val result = inTransaction {
            try {
              Right(f)
            } catch {
              case e: LiftFlowOfControlException => Left(e)
            }
          }

          result match {
            case Right(r) => r
            case Left(exception) => throw exception
          }
        }
      })
    }
  }

  private def provideJQuery(): Unit = {
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery111Z
    JQueryModule.init()
  }
}
