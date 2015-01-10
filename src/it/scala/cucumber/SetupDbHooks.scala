package cucumber

import java.sql.DriverManager

import cucumber.util.GlobalCucumberHooks
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.squerylrecord.SquerylRecord
import org.merizen.hipconf.persistance.HipConfSchema
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter

class SetupDbHooks extends GlobalCucumberHooks {
  BeforeAll {
    Class.forName("org.h2.Driver")
    def connection = DriverManager.getConnection(
      "jdbc:h2:mem:hipconf;DB_CLOSE_DELAY=-1",
      "sa", "")
    SquerylRecord.initWithSquerylSession(Session.create(connection, new H2Adapter))

    inTransaction {
      HipConfSchema.create
    }
  }
}
