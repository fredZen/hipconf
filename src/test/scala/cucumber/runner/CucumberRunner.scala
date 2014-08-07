package cucumber.runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(features = Array("src/test/resources/cucumber"), glue = Array("cucumber"))
class CucumberRunner
