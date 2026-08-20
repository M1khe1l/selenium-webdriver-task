package business.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.saucedemopages.InventoryPage;
import pages.saucedemopages.ShoppingCart;

public class ShoppingSteps {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingSteps.class);
    private final InventoryPage inventoryPage;

    public ShoppingSteps(InventoryPage inventoryPage) {
        this.inventoryPage = inventoryPage;
    }

    public ShoppingCart addProductsAndGoToCart(String... products) {
        logger.info("Business step: Adding {} product(s) to cart and opening cart",  products.length);
        for( String product : products ) {
            logger.info("Adding product to cart: {}", product);
        }
        return inventoryPage.addProductsToCart(products).goToCart();
    }

    public InventoryPage addProducts(String... products) {
        logger.info("Business step: Adding {} product(s) and go to cart",  products.length);
        return inventoryPage.addProductsToCart(products);
    }

    public InventoryPage removeProducts(String... products) {
        logger.info("Business step: Removing {} product(s) from cart", products.length);
        return inventoryPage.removeProductsFromCart(products);
    }

}
