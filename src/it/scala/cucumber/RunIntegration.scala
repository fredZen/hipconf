package cucumber

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import cucumber.util.RunCucumberHooks
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:."),
  glue = Array("cucumber"),
  plugin = Array("html:target/cucumber/integration", "cucumber.pro.CucumberProFormatter"),
  tags = Array("@integration")
)
class RunIntegration

object RunIntegration extends RunCucumberHooks
