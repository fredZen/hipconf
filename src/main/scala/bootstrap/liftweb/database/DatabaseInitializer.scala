package bootstrap.liftweb.database

trait DatabaseInitializer {
  def setupDatabaseStructure(): Unit
}
