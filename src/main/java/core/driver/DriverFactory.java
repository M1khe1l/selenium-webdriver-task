package core.driver;

import core.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            String browserProperty = System.getProperty("browser");
            if (browserProperty == null || browserProperty.isBlank()) {
                browserProperty = ConfigReader.get("browser");
                logger.info("No -Dbrowser passed, using browser from config {}", browserProperty);
            }
            BrowserType browserType = BrowserType.fromString(browserProperty);
            logger.info("Creating new WebDriver instance for browser: {}", browserType);
            DRIVER_THREAD_LOCAL.set(createDriver(browserType));
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            logger.info("Quitting WebDriver instance for thread: {}", Thread.currentThread().getName());
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }

    private static WebDriver createDriver(BrowserType browserType) {
        WebDriver driver;
        switch (browserType) {
            case FIREFOX -> {
                logger.info("Creating Firefox driver");
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions());
            }
            case CHROME -> {
                logger.info("Creating Chrome driver");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions());
            }
            case EDGE -> {
                logger.info("Creating Edge driver");
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver(edgeOptions());
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserType);

        }

        return driver;
    }


    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("signon.rememberSignons", false);
        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }
}
