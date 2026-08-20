package business.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.saucedemopages.CheckoutComplete;
import pages.saucedemopages.CheckoutStep1;
import pages.saucedemopages.CheckoutStep2;
import pages.saucedemopages.ShoppingCart;

public class CheckoutSteps {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutSteps.class);

    /**
     * Completes the entire checkout flow starting from the Shopping Cart page:
     * cart -> checkout info -> checkout overview -> order confirmation.
     */
    public CheckoutComplete completeCheckout(ShoppingCart cart,
                                             String firstName,
                                             String lastName,
                                             String zip) {
        logger.info("Business step: starting checkout flow");
        CheckoutStep1 checkoutStep1 = cart.goToCheckout();
        logger.debug("On checkout step 1, filling personal info fields");

        CheckoutStep2 checkoutStep2 = checkoutStep1.fillTheFields(firstName, lastName, zip).continueToCheckout();
        logger.debug("On checkout step 2, order overview");

        CheckoutComplete checkoutComplete = checkoutStep2.clickFinish();
        logger.info("Business step: checkout flow completed");

        return checkoutComplete;
    }

}
