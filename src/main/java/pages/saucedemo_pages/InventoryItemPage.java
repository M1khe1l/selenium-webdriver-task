package pages.saucedemo_pages;

import org.openqa.selenium.NoSuchElementException;
import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryItemPage extends BasePage {

    @FindBy(id = "back-to-products")
    private WebElement backToProductsBtn;

    @FindBy(id = "shopping_cart_container")
    private WebElement shoppingCart;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement shoppingCartBadge;

    @FindBy(id = "inventory_details_price")
    private WebElement itemsProductPrice;

    @FindBy(id = "add-to-cart")
    private WebElement addToCartBtn;

    @FindBy(xpath = "//div[@class='inventory_details_desc large_size']")
    private WebElement itemsProductDescription;

    public InventoryItemPage(WebDriver driver) {
        super(driver);
    }

    public InventoryItemPage addToCart(){
        click(addToCartBtn);
        return this;
    }

    public InventoryPage goBackToProducts() {
        click(backToProductsBtn);
        return new InventoryPage(driver);
    }

    public ShoppingCart goToShoppingCart() {
        click(shoppingCart);
        return new ShoppingCart(driver);
    }

    public String getItemPrice() {
        return getText(itemsProductPrice);
    }

    public String getItemDescription() {
        return getText(itemsProductDescription);
    }

    public boolean isShoppingCartBadgeDisplayed() {
        return isElementDisplayed(shoppingCartBadge);
    }

    public int getCartItemCount(){
        if(isShoppingCartBadgeDisplayed()) {
            try {
                return Integer.parseInt(shoppingCartBadge.getText());
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
        return 0;
    }
}
