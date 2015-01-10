package cucumber.util

import org.junit.AfterClass

trait RunCucumberHooks {
  @AfterClass
  def bu(): Unit = {
    GlobalCucumberHooks.runShutdownHooks()
  }
}