package bootstrap.liftweb.database

import java.sql.{Connection, SQLException}

import liquibase.database.jvm.JdbcConnection
import liquibase.database.{Database, DatabaseFactory}
import liquibase.exception.DatabaseException
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, Liquibase}

class LiquibaseDatabaseInitializer(connection: Connection) extends DatabaseInitializer {
  override def setupDatabaseStructure(): Unit = {
    try {
      val contexts: Contexts = null
      liquibase(connection).update(contexts)
    } catch {
      case e: SQLException => {
        connection.rollback()
        throw new DatabaseException(e)
      }
    }
  }

  private def liquibase(connection: Connection): Liquibase = {
    val resourceAccessor = new ClassLoaderResourceAccessor()
    val changeLogFile = "database/changelog/db.changelog-master.yaml"
    new Liquibase(changeLogFile, resourceAccessor, database(connection))
  }

  private def database(connection: Connection): Database = {
    val jdbcConnection = new JdbcConnection(connection)
    val databaseFactory = DatabaseFactory.getInstance
    databaseFactory.findCorrectDatabaseImplementation(jdbcConnection)
  }
}
