package core.reporting;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIRECTORY = "screenshots";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private ScreenshotUtils() {}

    public static byte[] capture(WebDriver driver) {
        logger.info("Taking screenshot");
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void saveToDisk(byte[] screenshot, String testName) {
        try {
            Path directory = Paths.get(SCREENSHOT_DIRECTORY);
            Files.createDirectories(directory);

            String fileName = testName + "_" + LocalDateTime.now().format(TIMESTAMP_FORMAT) + ".png";
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, screenshot);

            logger.info("Screenshot saved: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot to disk for test '{}'", testName, e);
        }
    }
}
