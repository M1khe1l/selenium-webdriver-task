package test_scenario_2;

import base.BaseTest;
import base.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.saucedemo_pages.*;

public class Scenario_2 extends BaseTest {

    private InventoryPage inventoryPage;
    private ShoppingCart shoppingCart;
    private CheckoutStep1 checkoutStep1;
    private CheckoutStep2 checkoutStep2;
    private CheckoutComplete checkoutComplete;

    @Test
    public void verifyLogin(){
        inventoryPage = new LoginPage(driver)
                .login(TestData.STANDARD_USERNAME, TestData.PASSWORD);
        Assert.assertTrue(inventoryPage.isHeaderDisplayed(), "Inventory page is not displayed");
    }

    @Test(dependsOnMethods = "verifyLogin")
    public void verifyAddToCart(){
        shoppingCart = inventoryPage.addProductsToCart(TestData.BACKPACK,
                        TestData.BIKE_LIGHT, TestData.FLEECE_JACKET, TestData.BOLT_T_SHIRT)
                .goToCart();
        Assert.assertEquals(shoppingCart.getShoppingCartItemCount(), 4, "Shopping cart item count is incorrect");
    }

    @Test(dependsOnMethods = "verifyAddToCart")
    public void goToCheckoutStep1(){
        checkoutStep1 = shoppingCart.removeProductsFromCart(TestData.BACKPACK)
                .goToCheckout();
                Assert.assertTrue(checkoutStep1.isCheckoutTitleDisplayed(), "Checkout step 1 page Title is not displayed");
    }

    @Test(dependsOnMethods = "goToCheckoutStep1")
    public void goToCheckoutStep2(){
        checkoutStep2 = checkoutStep1.fillTheFields(TestData.RANDOM_SYMBOLS, TestData.RANDOM_SYMBOLS, TestData.RANDOM_SYMBOLS)
                .continueToCheckout();
        Assert.assertTrue(checkoutStep2.isCheckoutTitleDisplayed(), "Checkout step 2 page Title is not displayed");
    }

    @Test(dependsOnMethods = "goToCheckoutStep2")
    public void verifyCheckoutComplete(){
        checkoutComplete = checkoutStep2.clickFinish();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(checkoutComplete.isCheckoutCompleteTitleDisplayed(),
                "Checkout complete page Title is not displayed");
        softAssert.assertTrue(checkoutComplete.isCheckoutCompleteHeaderDisplayed(),
                "Checkout complete header page Title is not displayed");
        softAssert.assertEquals(checkoutComplete.getCheckoutCompleteHeaderText(), TestData.CHECKOUT_COMPLETE_HEADER,
                "Checkout complete header text is incorrect");
        softAssert.assertEquals(checkoutComplete.getCheckoutCompleteText(), TestData.CHECKOUT_COMPLETE_TEXT,
                "Checkout complete text is incorrect");
        softAssert.assertAll();
    }
}
