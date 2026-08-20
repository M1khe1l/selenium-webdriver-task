package pages.saucedemopages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import pages.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static utilities.UtilityMethods.parseDoubleFromString;

public class InventoryPage extends BasePage {

    private static final By inventoryItemsList = By.xpath("//div[@class='inventory_item']");
    public static final By itemNestedPrice = By.cssSelector("[data-test='inventory-item-price']");
    public static final By itemNestedName = By.cssSelector("[data-test='inventory-item-name']");

    @FindBy(id = "react-burger-menu-btn")
    private WebElement topMenuBtn;

    @FindBy(xpath = "//span[@class='title']")
    private WebElement inventoryTitle;

    @FindBy(xpath = "//div[@class='header_secondary_container']")
    private WebElement inventoryPageHeader;

    @FindBy(id = "shopping_cart_container")
    private WebElement shoppingCartContainer;

    @FindBy(xpath = "//span[@class='shopping_cart_badge']")
    private WebElement shoppingCartBadge;

    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement productsSortDropMenu;

    @FindBy(xpath = "//div[@class='inventory_item']")
    private WebElement inventoryAllItems;

    @FindBy(xpath = "//div[@class='inventory_item_name ']")
    private WebElement inventoryItemsNameList;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Edd products to cart: {productNames}")
    public InventoryPage addProductsToCart(String... productNames) {
        for (String productName : productNames) {
            click(stringToByAddition(productName));
        }
        return this;
    }

    @Step("Remove products from cart: {productNames}")
    public InventoryPage removeProductsFromCart(String... productNames) {
        for (String productName : productNames) {
            click(stringToByRemoval(productName));
        }
        return this;
    }

    public By stringToByAddition(String productName) {
        String formattedName = productName.toLowerCase().replaceAll(" ", "-");
        return By.id("add-to-cart-" + formattedName);
    }

    public By stringToByRemoval(String productName) {
        String formattedName = productName.toLowerCase().replaceAll(" ", "-");
        return By.id("remove-" + formattedName);
    }

    public InventoryPage sortProductsByValue(String sortValue) {
        Select sortOption = new Select(productsSortDropMenu);
        sortOption.selectByValue(sortValue);
        return this;
    }

    public boolean isShoppingCartContainerDisplayed() {
        return shoppingCartContainer.isDisplayed();
    }

    public boolean isHeaderDisplayed() {
        return inventoryPageHeader.isDisplayed();
    }

    public boolean isTitleDisplayed() {
        return inventoryTitle.isDisplayed();
    }

    public String getInventoryTitle() {
        return inventoryTitle.getText();
    }

    public InventoryItemPage showItem(String name) {
        for (WebElement element: getListOfElements(inventoryItemsList)) {
            if(getNestedNameFromElement(element).equals(name)){
                element.findElement(itemNestedName).click();
                break;
            }
        }
        return new InventoryItemPage(driver);
    }

    public ShoppingCart goToCart() {
        click(shoppingCartContainer);
        return new ShoppingCart(driver);
    }

    public boolean isShoppingCartBadgeDisplayedNoWait() {
        return isElementDisplayedNoWait(shoppingCartBadge);
    }

    public int getCartItemCount(){
        if(isShoppingCartBadgeDisplayedNoWait()) {
            try {
                return Integer.parseInt(shoppingCartBadge.getText());
            } catch (NoSuchElementException e) {
                return 0;
            }
        }
        return 0;
    }

    public int getIndexOfHighesPrice(){
        List<Double> priceList = getDoubleListOfPrices(inventoryItemsList, itemNestedPrice);
        int index = 0;
        for (int i = 0 ; i < priceList.size() ; i++){
            if (priceList.get(i) > priceList.get(index)){
                index = i;
            }
        }
        return index;
    }

    public int getIndexOfLowesPrice(){
        List<Double> priceList = getDoubleListOfPrices(inventoryItemsList, itemNestedPrice);
        int index = 0;
        for (int i = 0 ; i < priceList.size() ; i++){
            if (priceList.get(i) < priceList.get(index)){
                index = i;
            }
        }
        return index;
    }

    private List<Double> getDoubleListOfPrices(By listLocator, By priceLocator) {
        List<WebElement> itemsList = getListOfElements(listLocator);
        List<Double> prices = new ArrayList<>();
        for (WebElement item : itemsList) {
            WebElement price = item.findElement(priceLocator);
            prices.add(parseDoubleFromString(getText(price)));
        }
        return prices;
    }



    private String getNestedNameFromElement(WebElement element) {
        return element.findElement(itemNestedName).getText();
    }
}
