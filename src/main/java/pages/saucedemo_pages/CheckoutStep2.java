package pages.saucedemo_pages;

import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static utilities.UtilityMethods.sumOfDoublesFromString;

public class CheckoutStep2 extends BasePage {

    @FindBy(xpath = "//span[@class='title']")
    private WebElement checkout2Title;

    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> checkout2ItemsList;

    @FindBy(xpath = "//div[@class='item_pricebar']")
    private List<WebElement> checkout2PricebarList;

    @FindBy(xpath = "//div[@data-test='payment-info-value']")
    private WebElement paymentInfoValue;

    @FindBy(xpath = "//div[@data-test='shipping-info-value']")
    private WebElement shippingInfoValue;

    @FindBy(xpath = "//div[@data-test='subtotal-label']")
    private WebElement subtotalLabel;

    @FindBy(xpath = "//div[@data-test='tax-label']")
    private WebElement taxLabel;

    @FindBy(xpath = "//div[@data-test='total-label']")
    private WebElement totalLabel;

    @FindBy(id = "cancel")
    private WebElement cancelBtn;

    @FindBy(id = "finish")
    private WebElement finishBtn;


    public CheckoutStep2(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutTitleDisplayed() {
        return checkout2Title.isDisplayed();
    }

    private String[] getPriceListText() {
        String[] priceListText = new String[checkout2PricebarList.size()];

        for (int i = 0; i < checkout2PricebarList.size(); i++) {
            priceListText[i] = getText(checkout2PricebarList.get(i));
        }
        return priceListText;
    }

    public double getSumOfItemsPrice() {
        return sumOfDoublesFromString(getPriceListText());
    }

    public double getTaxPrice() {
        return sumOfDoublesFromString(getText(taxLabel));
    }

    public double getSubtotalPrice() {
        return sumOfDoublesFromString(getText(subtotalLabel));
    }

    public double getTotalPrice() {
        return sumOfDoublesFromString(getText(totalLabel));
    }

    public InventoryPage clickCancel() {
        click(cancelBtn);
        return new InventoryPage(driver);
    }

    public CheckoutComplete clickFinish() {
        click(finishBtn);
        return new CheckoutComplete(driver);
    }

}
