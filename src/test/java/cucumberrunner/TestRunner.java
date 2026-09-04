package cucumberrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "cucumberglue",
        plugin = {"pretty"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
