package testscenario3;

import base.BaseTest;
import base.TestData;
import business.steps.LoginSteps;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.saucedemopages.InventoryItemPage;
import pages.saucedemopages.InventoryPage;
import pages.saucedemopages.LoginPage;

@Epic("Scenario 3")
public class ScenarioThreeTest extends BaseTest{

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private InventoryItemPage inventoryItemPage;

    private LoginSteps loginSteps;

    @Test(priority = 1)
    public void loginWithInvalidCredentials() {
        loginSteps = new LoginSteps(driver);
        loginPage = loginSteps.attemptLogin(TestData.RANDOM_SYMBOLS, TestData.PASSWORD);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        softAssert.assertEquals(loginPage.getErrorMessage(), TestData.INVALID_USER_ERROR_MESSAGE,
                "Error message is incorrect");
        softAssert.assertAll();

    }

    @Test(priority = 2)
    public void loginWithLockedOutUser() {
        loginPage = loginSteps.attemptLogin(TestData.LOCKED_OUT_USERNAME, TestData.PASSWORD);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
        softAssert.assertEquals(loginPage.getErrorMessage(), TestData.LOCKED_OUT_ERROR_MESSAGE,
                "Error message is incorrect");
        softAssert.assertAll();
    }

    @Test(priority = 3)
    public void loginWithStandardUser() {
        inventoryPage = loginSteps.login(TestData.STANDARD_USERNAME, TestData.PASSWORD);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(inventoryPage.isTitleDisplayed(), "Title is not displayed");
        softAssert.assertEquals(inventoryPage.getInventoryTitle(), TestData.INVENTORY_TITLE,
                "Inventory page title is incorrect");
    }

    @Test(priority = 4)
    public void sortProductHiLow() {
        int index = inventoryPage.sortProductsByValue(TestData.SORT_HI_LOW)
                .getIndexOfHighesPrice();
        Assert.assertEquals(index, ZERO, "Highest price item must be first");
    }

    @Test(priority = 5)
    public void sortProductLowHi() {
        int index = inventoryPage.sortProductsByValue(TestData.SORT_LOW_HI)
                .getIndexOfLowesPrice();
        Assert.assertEquals(index, ZERO, "Lowest price item must be first");
    }

    @Test(priority = 6)
    public void showItem() {
        inventoryItemPage = inventoryPage.showItem(TestData.RED_T_SHIRT);
        Assert.assertEquals(inventoryItemPage.getItemDescription(), TestData.RED_T_SHIRT_DESCRIPTION,
                "Item description is incorrect");
    }

    @Test(dependsOnMethods = "showItem")
    public void addToCartAndContinueShopping() {
        inventoryPage = inventoryItemPage.addToCart()
                .goBackToProducts();
        Assert.assertEquals(inventoryPage.getCartItemCount(), ONE, "CartItemCount is incorrect");
    }

    @Test(dependsOnMethods = "addToCartAndContinueShopping")
    public void addToCartSecondItemAndCheckItemsAdded() {
        inventoryItemPage = inventoryPage.addProductsToCart(TestData.BACKPACK)
                .showItem(TestData.BACKPACK);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(inventoryItemPage.getItemDescription(), TestData.BACKPACK_DESCRIPTION,
                "Item description is incorrect");
        softAssert.assertEquals(inventoryItemPage.getCartItemCount(), TWO, "CartItemCount is incorrect");
        softAssert.assertAll();
    }

}
