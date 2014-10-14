package bootstrap.liftweb

trait DatabaseInitializer {
  def setupDatabaseStructure(): Unit
}
