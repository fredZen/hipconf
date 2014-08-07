package cucumber.stepdefinitions

import cucumber.api.scala.{EN, ScalaDsl}
import org.junit.Assert.assertEquals

class ComputationStepDefinitions extends ScalaDsl with EN {
  Given("""^(\d+) \+ (\d+) should equal (\d+)$"""){ (arg0:Int, arg1:Int, arg2:Int) =>
    assertEquals(arg2, arg0 + arg1)
  }
}
