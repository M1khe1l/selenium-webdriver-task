package cucumberglue;

import business.steps.LoginSteps;
import core.config.ConfigReader;
import core.driver.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pages.saucedemopages.InventoryPage;
import pages.saucedemopages.LoginPage;

public class LoginStepDefinitions {
    private final String INVENTORY_PAGE_RESULT = "inventory page displayed";
    private final WebDriver driver = DriverFactory.getDriver();

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        driver.get(ConfigReader.getBaseUrl());
    }

    @When("the user attempts to login with  username {string} and password {string}")
    public void theUserAttemptsToLoginWithUsernameAndPassword(String username, String password) {
        LoginSteps loginSteps = new LoginSteps(driver);
        loginPage = loginSteps.attemptLogin(username, password);
        inventoryPage = new InventoryPage(driver);
    }

    @Then("the result should be {string}")
    public void theResult_should_be(String expectedResult) {
        SoftAssert softAssert = new SoftAssert();

        if (expectedResult.equals(INVENTORY_PAGE_RESULT)) {
            softAssert.assertTrue(inventoryPage.isHeaderDisplayed(), "Inventory page is not displayed");
        } else {
            softAssert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
            softAssert.assertEquals(loginPage.getErrorMessage(),  expectedResult, "Error message is incorrect");
        }
        softAssert.assertAll();
    }
}
