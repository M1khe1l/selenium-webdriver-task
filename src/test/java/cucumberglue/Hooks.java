package cucumberglue;

import core.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setup() {
        log.info("setup() running on thread: {}", Thread.currentThread().getName());
        DriverFactory.getDriver();
    }

    @After
    public void teardown() {
        WebDriver driver = DriverFactory.getDriver();
        DriverFactory.quitDriver(driver);
    }
}
