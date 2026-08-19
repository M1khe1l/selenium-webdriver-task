package base;

import core.driver.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected String URL = "https://www.saucedemo.com/";

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver();
        log.info("Navigating  to URL: {}", URL);
        driver.get(URL);
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.info("Test Failed: {}", result.getName());
            takeScreenshot();
        }
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        log.info("Taking  screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
