package bootstrap.liftweb

import java.sql.DriverManager
import javax.mail.{Authenticator, PasswordAuthentication}

import net.liftmodules.JQueryModule
import net.liftweb.http.{Html5Properties, LiftRules, Req, S}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.squerylrecord.SquerylRecord
import net.liftweb.util.{Props, Mailer, LiftFlowOfControlException, LoanWrapper}
import org.merizen.hipconf.persistance.HipConfRepository
import org.merizen.hipconf.user.User
import org.squeryl.Session
import org.squeryl.internals.DatabaseAdapter

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot(): Unit = {
    LiftRules.addToPackages("org.merizen.hipconf.snippet")
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
    setupMailer()
    setupDatabase()
    LiftRules.setSiteMap(siteMap)
    provideJQuery()
  }

  private def siteMap: SiteMap = {
    def siteMapMutator = User.sitemapMutator

    def siteMapTemplate: SiteMap =
      SiteMap(
        Menu.i("Home") / "index" >> User.AddUserMenusAfter
      )

    siteMapMutator(siteMapTemplate)
  }

  private def setupDatabase(): Unit = {
    connectToDatabase()
    wrapAllRequestsInTransaction()
    inTransaction{
      HipConfRepository.drop
      HipConfRepository.create
    }
    
    def connectToDatabase(): Unit = {
      for {
        driver <- Props.get("db.driver")
        url <- Props.get("db.url")
        user <- Props.get("db.user")
        pass <- Props.get("db.password")
        adapterClass <- Props.get("db.adapter")
      } {
        Class.forName(driver)
        val adapter: DatabaseAdapter = Class.forName(adapterClass).newInstance().asInstanceOf[DatabaseAdapter]
        def connection = DriverManager.getConnection(url, user, pass)
        SquerylRecord.initWithSquerylSession(Session.create(connection, adapter))
      }
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

  private def setupMailer(): Unit = {
    Mailer.authenticator = for {
      user <- Props.get("mail.user")
      pass <- Props.get("mail.password")
    } yield new Authenticator {
        override def getPasswordAuthentication =
          new PasswordAuthentication(user, pass)
      }
  }
}
