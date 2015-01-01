package cucumber

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:cucumber"),
  glue = Array("cucumber.integration"),
  plugin = Array("html:target/cucumber"),
  tags = Array("@integration")
)
class RunIntegration
