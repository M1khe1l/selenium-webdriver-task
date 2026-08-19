package base;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected String URL = "https://www.saucedemo.com/";

    @BeforeClass
    public void setup() {
        driver = createDriver();
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
        log.info("Tearing down Browser");
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        log.info("Taking  screenshot");
        return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    protected WebDriver createDriver() {
        if (driver != null) {
            return driver;
        }
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    protected WebDriver getDriver() {
        return createDriver();
    }
}
