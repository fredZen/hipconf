package bootstrap.liftweb

import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.util.Helpers._
import net.liftweb.util.Vendor._
import net.liftweb.util._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot(): Unit = {
    LiftRules.addToPackages("org.merizen.hipconf.snippet")
    LiftRules.htmlProperties.default.set((r: Req) => new Html5Properties(r.userAgent))
    autoFadeOutNotices()
    setupMailer()
    setupDatabase()
    LiftRules.setSiteMap(siteMap)
    provideJQuery()
  }

  private def autoFadeOutNotices(): Unit= {
    import net.liftweb.http.NoticeType.Value

    LiftRules.noticesAutoFadeOut.default.set((_: Value) match {
      case NoticeType.Notice => Full((2.seconds, 2.seconds))
      case _ => Empty
    })
  }

  private def setupMailer(): Unit = {
    import javax.mail.{Authenticator, PasswordAuthentication}

    Mailer.authenticator = for {
      user <- Props.get("mail.user")
      pass <- Props.get("mail.password")
    } yield new Authenticator {
        override def getPasswordAuthentication =
          new PasswordAuthentication(user, pass)
      }
  }

  private def setupDatabase(): Unit = {
    import java.sql.DriverManager

import net.liftweb.squerylrecord.RecordTypeMode._
    import net.liftweb.squerylrecord.SquerylRecord
    import org.merizen.hipconf.persistance.HipConfRepository
    import org.squeryl.Session
    import org.squeryl.internals.DatabaseAdapter

    connectToDatabase()
    wrapAllRequestsInTransaction()
    inTransaction {
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

  private def siteMap: SiteMap = {
    import org.merizen.hipconf.user.User

    def siteMapMutator = User.sitemapMutator

    val siteMapTemplate =
      SiteMap(
        Menu.i("Home") / "index" >> User.AddUserMenusAfter
      )

    siteMapMutator(siteMapTemplate)
  }

  private def provideJQuery(): Unit = {
    import net.liftmodules.JQueryModule

    JQueryModule.InitParam.JQuery = JQueryModule.JQuery111Z
    JQueryModule.init()
  }
}
