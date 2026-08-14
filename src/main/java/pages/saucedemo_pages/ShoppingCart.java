package pages.saucedemo_pages;

import org.openqa.selenium.By;
import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends BasePage {

    @FindBy(xpath = "//span[@class='title']")
    private WebElement shoppingCartTitle;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingBtn;

    @FindBy(xpath = "//div[@class='cart_item']")
    private List<WebElement> cartItems;

    @FindBy(id = "checkout")
    private WebElement checkoutBtn;

    public ShoppingCart(WebDriver driver) {
        super(driver);
    }

    public boolean isShoppingCartTitleDisplayed() {
        return shoppingCartTitle.isDisplayed();
    }

    public List<String> getShoppingCartItemNames() {
        List<String> itemNames = new ArrayList<>();
        for(WebElement itemName : cartItems) {
            itemNames.add(itemName.getText());
        }
        return itemNames;
    }

    public int getShoppingCartItemCount() {
        return cartItems.size();
    }

    public ShoppingCart removeProductsFromCart(String... productNames) {
        for (String productName : productNames) {
            click(stringToByRemoval(productName));
        }
        return this;
    }

    public By stringToByRemoval(String productName) {
        String formattedName = productName.toLowerCase().replaceAll(" ", "-");
        return By.id("remove-" + formattedName);
    }

    public InventoryPage continueShopping() {
        click(continueShoppingBtn);
        return new InventoryPage(driver);
    }

    public CheckoutStep1 goToCheckout() {
        click(checkoutBtn);
        return new CheckoutStep1(driver);
    }

}
