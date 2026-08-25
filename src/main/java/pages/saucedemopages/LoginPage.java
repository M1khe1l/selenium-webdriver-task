package pages.saucedemopages;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//div[@class='login_logo']")
    private WebElement loginPageHeader;

   @FindBy(id ="user-name")
   private WebElement usernameField;

   @FindBy(id = "password")
   private WebElement passwordField;

   @FindBy(id = "login-button")
   private WebElement loginButton;

   @FindBy(xpath = "//div[@class='error-message-container error']")
   private WebElement errorMessageContainer;

   @FindBy(className = "error-button")
   private WebElement errorCloseButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login with username: {username}, and password: {password}")
    public InventoryPage login(String username, String password) {
        setUsername(username);
        setPassword(password);
        click(loginButton);
        return new InventoryPage(driver);
    }

    public void setUsername(String username) {
        type(usernameField, username);
    }

    public void setPassword(String password) {
        type(passwordField, password);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessageContainer);
    }

    public LoginPage closeErrorMessage() {
        click(errorCloseButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessageContainer);
    }
}
