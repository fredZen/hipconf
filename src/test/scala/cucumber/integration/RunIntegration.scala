package cucumber.integration

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("src/test/resources/cucumber"),
  glue = Array("cucumber.integration"),
  dotcucumber = ".cucumber",
  format = Array("html:target/cucumber")
)
class RunIntegration
