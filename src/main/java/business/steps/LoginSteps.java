package business.steps;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.saucedemopages.InventoryPage;
import pages.saucedemopages.LoginPage;

public class LoginSteps {

    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    private WebDriver driver;

    public LoginSteps(WebDriver driver) {
        this.driver = driver;
    }

    public InventoryPage login(String username, String password) {
        logger.info("logged in as user: {}", username);
        InventoryPage inventoryPage = new LoginPage(driver).login(username, password);
        logger.debug("Login submitted for user '{}'", username);
        return inventoryPage;
    }

    public LoginPage attemptLogin(String username, String password) {
        logger.info("Attempting login expected to fail, user: {}", username);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        logger.debug("Login attempt submitted for user '{}', staying on Login page", username);
        return loginPage;
    }
}
