package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage{

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void type(WebElement element, String text) {
        wait.until(webDriver -> element.isDisplayed());
        element.clear();
        element.sendKeys(text);
    }

    protected void click(WebElement element) {
        wait.until(webDriver -> element.isDisplayed());
        element.click();
    }
    protected void click(By locator) {
        wait.until(webDriver -> driver.findElement(locator).isDisplayed());
        driver.findElement(locator).click();
    }

    protected String getText(WebElement element) {
        wait.until(webDriver -> element.isDisplayed());
        return element.getText();
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return wait.until(webDriver -> element.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementDisplayedNoWait(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected List<WebElement> getListOfElements(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected By buildLocator(String idPrefix, String productName) {
        String formattedName = productName.toLowerCase().replaceAll(" ", "-");
        return By.id(idPrefix + formattedName);
    }
}
