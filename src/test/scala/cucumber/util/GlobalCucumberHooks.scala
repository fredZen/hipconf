package cucumber.util

import cucumber.api.scala.ScalaDsl

import scala.collection.mutable.ListBuffer

trait GlobalCucumberHooks extends ScalaDsl {

  def BeforeAll(f: => Unit): Unit = f

  def AfterAll(f: => Unit): Unit = GlobalCucumberHooks.addShutdownHook(f)
}

object GlobalCucumberHooks {
  val shutdownHooks = new ListBuffer[() => Unit]

  def addShutdownHook(f: => Unit): Unit = {
      shutdownHooks += (() => f)
  }

  def runShutdownHooks(): Unit = {
    shutdownHooks.foreach(_())
  }
}
