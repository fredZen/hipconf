package bootstrap.liftweb.database

import net.liftweb.squerylrecord.RecordTypeMode._
import org.merizen.hipconf.persistance.HipConfSchema

class DropCreateDatabaseInitializer extends DatabaseInitializer {
  override def setupDatabaseStructure(): Unit = {
    inTransaction {
      HipConfSchema.drop
      HipConfSchema.create
    }
  }
}
