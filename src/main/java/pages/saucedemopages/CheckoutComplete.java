package pages.saucedemopages;

import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutComplete extends BasePage {

    @FindBy(xpath = "//span[@class='title']")
    private WebElement checkoutCompleteTitle;

    @FindBy(xpath = "//h2[@class='complete-header']")
    private WebElement checkoutCompleteHeader;

    @FindBy(xpath = "//div[@class='complete-text']")
    private WebElement checkoutCompleteText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsBtn;

    public CheckoutComplete(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutCompleteTitleDisplayed() {
        return checkoutCompleteTitle.isDisplayed();
    }

    public boolean isCheckoutCompleteHeaderDisplayed() {
        return checkoutCompleteHeader.isDisplayed();
    }

    public String getCheckoutCompleteHeaderText() {
        return getText(checkoutCompleteHeader);
    }

    public String getCheckoutCompleteText() {
        return getText(checkoutCompleteText);
    }

    public InventoryPage clickBackToProductsBtn() {
        click(backToProductsBtn);
        return new InventoryPage(driver);
    }
}
