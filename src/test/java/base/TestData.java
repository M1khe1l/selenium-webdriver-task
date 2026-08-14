package base;

import java.util.Random;

public class TestData {

    public final static String LOCKED_OUT_ERROR_MESSAGE = "Epic sadface: Sorry, this user has been locked out.";
    public final static String INVALID_USER_ERROR_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    public final static String LOCKED_OUT_USERNAME = "locked_out_user";
    public final static String STANDARD_USERNAME = "standard_user";
    public final static String PASSWORD = "secret_sauce";
    public static final String BACKPACK = "Sauce Labs Backpack";
    public static final String BIKE_LIGHT = "Sauce Labs Bike Light";
    public static final String BOLT_T_SHIRT =  "Sauce Labs Bolt T-Shirt";
    public static final String FLEECE_JACKET = "Sauce Labs Fleece Jacket";
    public static final String ONESIE = "Sauce Labs Onesie";
    public static final String RED_T_SHIRT = "Test.allTheThings() T-Shirt (Red)";

    public static final String RED_T_SHIRT_DESCRIPTION ="This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.";
    public static final String BACKPACK_DESCRIPTION = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.";

    public static final String SORT_A_Z = "az";
    public static final String SORT_Z_A = "za";
    public static final String SORT_HI_LOW = "hilo";
    public static final String SORT_LOW_HI = "lohi";
    public static final String CHECKOUT_COMPLETE_HEADER = "Thank you for your order!";
    public static final String CHECKOUT_COMPLETE_TEXT = "Your order has been dispatched, and will arrive just as fast as the pony can get there!";
    public static final String INVENTORY_TITLE = "Products";
    public static final String RANDOM_SYMBOLS = new Random().ints(48, 122)
            .limit(5)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

}
