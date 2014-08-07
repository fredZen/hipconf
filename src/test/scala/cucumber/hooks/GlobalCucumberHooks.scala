package cucumber.hooks

import cucumber.api.scala.ScalaDsl

trait GlobalCucumberHooks extends ScalaDsl {
  def BeforeAll(f: => Unit) { f }

  def AfterAll(f: => Unit) {
    sys.addShutdownHook(f)
  }
}
