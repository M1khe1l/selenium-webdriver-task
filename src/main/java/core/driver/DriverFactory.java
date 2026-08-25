package core.driver;

import core.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private static final Map<BrowserType, Supplier<RemoteWebDriver>> DRIVER_CREATORS = new EnumMap<>(BrowserType.class);

    static {
        DRIVER_CREATORS.put(BrowserType.CHROME, () -> {
            logger.info("Creating Chrome driver");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(BrowserOptionsFactory.chromeOptions());
        });
        DRIVER_CREATORS.put(BrowserType.FIREFOX, () -> {
            logger.info("Creating Firefox driver");
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(BrowserOptionsFactory.firefoxOptions());
        });
        DRIVER_CREATORS.put(BrowserType.EDGE, () -> {
            logger.info("Creating Edge driver");
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver(BrowserOptionsFactory.edgeOptions());
        });
    }

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
            WebDriver rawDriver = createDriver(browserType);
            WebDriver decoratedDriver = decorateWithLogging(rawDriver);
            DRIVER_THREAD_LOCAL.set(decoratedDriver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            logger.info("Quitting WebDriver instance for thread: {}", Thread.currentThread().getName());
            try{
                driver.close();
                driver.quit();
            } catch (Exception e){
                logger.error("Error quitting WebDriver instance", e);
            } finally {
                if (DRIVER_THREAD_LOCAL.get() == driver) {
                    DRIVER_THREAD_LOCAL.remove();
                }
            }
        }
    }

    private static WebDriver createDriver(BrowserType browserType) {
       Supplier<RemoteWebDriver> creator = DRIVER_CREATORS.get(browserType);
       if (creator == null) {
           throw new IllegalArgumentException("Unsupported driver: " + browserType);
       }
       return creator.get();
    }

    private static WebDriver decorateWithLogging(WebDriver rawDriver) {
        logger.debug("Decorating WebDriver instance with logging listener");
        return new EventFiringDecorator<>(new LoggingWebDriverListener()).decorate(rawDriver);
    }
}
