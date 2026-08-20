package pages.saucedemopages;

import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutStep1 extends BasePage {

    @FindBy(xpath = "//span[@class='title']")
    private WebElement checkout1Title;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    public CheckoutStep1(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutTitleDisplayed() {
        return checkout1Title.isDisplayed();
    }

    public CheckoutStep1 fillTheFields(String firstName, String lastName, String postalCode){
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        return this;
    }

    public InventoryPage cancel() {
        click(cancelButton);
        return new InventoryPage(driver);
    }

    public CheckoutStep2 continueToCheckout() {
        click(continueButton);
        return new CheckoutStep2(driver);
    }
}
