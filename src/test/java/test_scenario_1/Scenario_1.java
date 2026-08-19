package test_scenario_1;

import base.BaseTest;
import base.TestData;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.saucedemo_pages.*;

import java.util.List;
@Epic("Scenario 1")
public class Scenario_1 extends BaseTest {

    private static final int ZERO_PRODUCTS_COUNT = 0;
    private static final int TWO_PRODUCTS_COUNT = 2;
    private static final int THREE_PRODUCTS_COUNT = 3;

    private InventoryPage inventoryPage;
    private ShoppingCart shoppingCart;

    @Test
    @Story("login with valid credentials")
    @Description("Verifies that an existing user can log in with correct password.")
    public void verifyLogin() {
        inventoryPage = new LoginPage(driver)
                .login(TestData.STANDARD_USERNAME, TestData.PASSWORD);
        Assert.assertTrue(inventoryPage.isHeaderDisplayed(), "Inventory page is not displayed");
    }

    @Test(dependsOnMethods = "verifyLogin")
    @Step("adding two Items to cart")
    public void addTwoItemsToCart() {
        inventoryPage.addProductsToCart(TestData.BACKPACK, TestData.BIKE_LIGHT);
        Assert.assertEquals(inventoryPage.getCartItemCount(), TWO_PRODUCTS_COUNT, String.format("Incorrect number of items in cart, " +
                "expected %d but got %d", TWO_PRODUCTS_COUNT, inventoryPage.getCartItemCount()));
    }

    @Test(dependsOnMethods = "addTwoItemsToCart")
    public void addThirdItemToCart() {
        inventoryPage.addProductsToCart(TestData.BOLT_T_SHIRT);
        Assert.assertEquals(inventoryPage.getCartItemCount(), THREE_PRODUCTS_COUNT, String.format("Incorrect number of items in Cart, " +
                "expected %d but got %d ", THREE_PRODUCTS_COUNT, inventoryPage.getCartItemCount()));
    }

    @Test(dependsOnMethods = "addThirdItemToCart")
    public void removeProductsFromCart() {
        inventoryPage.removeProductsFromCart(TestData.BOLT_T_SHIRT, TestData.BIKE_LIGHT, TestData.BACKPACK);
        Assert.assertEquals(inventoryPage.getCartItemCount(), ZERO_PRODUCTS_COUNT, "shopping card badge should not be displayed");
    }

    @Test(dependsOnMethods = "removeProductsFromCart")
    public void goToCartWithTreeProducts() {
        shoppingCart = inventoryPage.addProductsToCart(TestData.FLEECE_JACKET, TestData.RED_T_SHIRT, TestData.ONESIE)
                .goToCart();
        Assert.assertTrue(shoppingCart.isShoppingCartTitleDisplayed(), "Shopping cart title is not displayed");
    }

    @Test(dependsOnMethods = "goToCartWithTreeProducts")
    public void verifyProductsInCart() {
        List<String> namesList =shoppingCart.getShoppingCartItemNames();
        String names = namesList.toString();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(names.contains(TestData.FLEECE_JACKET));
        softAssert.assertTrue(names.contains(TestData.RED_T_SHIRT));
        softAssert.assertTrue(names.contains(TestData.ONESIE));
        softAssert.assertEquals(shoppingCart.getShoppingCartItemCount(), THREE_PRODUCTS_COUNT,
                "expected " + THREE_PRODUCTS_COUNT + "products, but got " + shoppingCart.getShoppingCartItemCount());
        softAssert.assertAll();
    }

    @Test(dependsOnMethods = "verifyProductsInCart")
    public void continueShopping() {
        String inventoryTitle = shoppingCart.continueShopping()
                .getInventoryTitle();
        Assert.assertEquals(inventoryTitle, TestData.INVENTORY_TITLE);
    }

    @Test(dependsOnMethods = "continueShopping")
    public void verifyCartBadge(){
        Assert.assertEquals(inventoryPage.getCartItemCount(), THREE_PRODUCTS_COUNT,
                String.format("Shopping cart container should contain %d products", THREE_PRODUCTS_COUNT));
    }

}
