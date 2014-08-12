package cucumber.util

import cucumber.api.scala.ScalaDsl

trait GlobalCucumberHooks extends ScalaDsl {

  def BeforeAll(f: => Unit): Unit = f

  def AfterAll(f: => Unit): Unit = sys.addShutdownHook(f)
}
