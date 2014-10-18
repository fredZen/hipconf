package bootstrap.liftweb.database

import java.sql.DriverManager

import bootstrap.liftweb.Configuration
import net.liftweb.http.S
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.squerylrecord.SquerylRecord
import net.liftweb.util.{LiftFlowOfControlException, LoanWrapper, Props}
import org.squeryl.Session
import org.squeryl.internals.DatabaseAdapter

object DatabaseConfiguration extends Configuration {
  def setup(): Unit = {
    connect()
    setupTransactionRequestWrapper()
  }

  private def connect(): Unit = {
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
      val initializer = new LiquibaseDatabaseInitializer(connection)
      initializer.setupDatabaseStructure()
      SquerylRecord.initWithSquerylSession(Session.create(connection, adapter))
    }
  }

  private def setupTransactionRequestWrapper(): Unit = {
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
