package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = createDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected static WebDriver createDriver() {
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

    protected static WebDriver getDriver() {
        return createDriver();
    }
}
