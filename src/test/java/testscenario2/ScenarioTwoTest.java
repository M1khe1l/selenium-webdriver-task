package testscenario2;

import base.BaseTest;
import base.TestData;
import business.steps.CheckoutSteps;
import business.steps.LoginSteps;
import business.steps.ShoppingSteps;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.saucedemopages.*;

@Epic("Scenario 2")
public class ScenarioTwoTest extends BaseTest {

    private InventoryPage inventoryPage;
    private ShoppingCart shoppingCart;

    private final CheckoutSteps checkoutSteps =  new CheckoutSteps();

    @Test
    public void verifyLogin(){
        LoginSteps loginSteps = new LoginSteps(driver);
        inventoryPage = loginSteps.login(TestData.STANDARD_USERNAME, TestData.PASSWORD);
        Assert.assertTrue(inventoryPage.isHeaderDisplayed(), "Inventory page is not displayed");
    }

    @Test(dependsOnMethods = "verifyLogin")
    public void verifyAddToCart(){
        ShoppingSteps shoppingSteps = new ShoppingSteps(inventoryPage);
        shoppingCart = shoppingSteps.addProductsAndGoToCart(TestData.BACKPACK,
                TestData.BIKE_LIGHT, TestData.FLEECE_JACKET, TestData.BOLT_T_SHIRT);
        Assert.assertEquals(shoppingCart.getShoppingCartItemCount(), 4, "Shopping cart item count is incorrect");
    }

    @Test(dependsOnMethods = "verifyAddToCart")
    public void verifyCheckoutComplete(){
        CheckoutComplete checkoutComplete = checkoutSteps.completeCheckout(shoppingCart, TestData.RANDOM_SYMBOLS, TestData.RANDOM_SYMBOLS, TestData.RANDOM_SYMBOLS);

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
