package base;

import core.config.ConfigReader;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    private static final String SCREENSHOT_DIRECTORY = "screenshots";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = DriverFactory.getDriver();
        String baseURL = ConfigReader.getBaseUrl();
        log.info("Navigating  to URL: {}", baseURL);
        driver.get(baseURL);
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            log.info("Test Failed: {}", result.getName());
            byte[] screenshot = takeScreenshot();
            saveScreenshotToDisk(screenshot, result.getName());

        }
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }


    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] takeScreenshot() {
        log.info("Taking  screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    /**
     * Persists the screenshot to disk (under ./screenshots) so it survives beyond the
     * Allure report and can be picked up as a CI build artifact. Logs the saved path.
     */
    private void saveScreenshotToDisk(byte[] screenshot, String testName) {
        try {
            Path directory = Paths.get(SCREENSHOT_DIRECTORY);
            Files.createDirectories(directory);

            String fileName = testName + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, screenshot);

            log.info("Screenshot saved: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save screenshot to disk for test '{}'", testName, e);
        }
    }
}
