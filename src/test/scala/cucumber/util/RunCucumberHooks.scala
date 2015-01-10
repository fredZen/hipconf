package cucumber.util

import org.junit.{AfterClass, BeforeClass}

trait RunCucumberHooks {
  @AfterClass
  def bu(): Unit = {
    GlobalCucumberHooks.runShutdownHooks()
  }
}