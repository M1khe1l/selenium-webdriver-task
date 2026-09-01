package base;

import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.epam.reportportal.utils.files.ByteSource;
import core.config.ConfigReader;
import core.driver.DriverFactory;
import core.reporting.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(ReportPortalTestNGListener.class)
public class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        log.info("setup() running on thread: {}", Thread.currentThread().getName());
        driver = DriverFactory.getDriver();
        String baseURL = ConfigReader.getBaseUrl();
        driver.get(baseURL);
    }

    @AfterMethod(alwaysRun = true)
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.info("Test Failed: {}", result.getName());
            byte[] screenshot = takeScreenshot();
            ScreenshotUtils.saveToDisk(screenshot, result.getName());
            ReportPortal.emitLog(
                    new ReportPortalMessage(ByteSource.wrap(screenshot), "image/png", "Screenshot on failure"),
                    "ERROR",
                    java.util.Calendar.getInstance().getTime()
            );
        }

    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        log.info("tearDown() running on thread: {}", Thread.currentThread().getName());
        DriverFactory.quitDriver(driver);
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        log.info("Taking  screenshot");
        return ScreenshotUtils.capture(driver);
    }
}
