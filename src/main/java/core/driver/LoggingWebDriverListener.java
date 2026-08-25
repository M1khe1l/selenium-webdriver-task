package core.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LoggingWebDriverListener implements WebDriverListener {

    private static final Logger logger = LoggerFactory.getLogger(LoggingWebDriverListener.class);

    @Override
    public void beforeGet(WebDriver driver, String url) {
        logger.info("Navigating to URL: {}", url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        logger.debug("Navigation completed. Current URL: {}", driver.getCurrentUrl());
    }

    @Override
    public void beforeClick(WebElement element) {
        logger.info("Clicking element: {}", describe(element));
    }

    @Override
    public void afterClick(WebElement element) {
        logger.debug("Click completed on element: {}", describe(element));
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        String elementDescription = describe(element);
        String valueToLog = joinKeys(keysToSend);
        logger.info("Typing '{}' into element: {}", valueToLog, elementDescription);
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        logger.debug("Finished typing into element: {}", describe(element));
    }

    @Override
    public void beforeClear(WebElement element) {
        logger.debug("Clearing element: {}", describe(element));
    }

    @Override
    public void beforeFindElement(WebDriver driver, org.openqa.selenium.By locator) {
        logger.debug("Looking for element: {}", locator);
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        logger.error("Error invoking '{}' on '{}' with args '{}'",
                method.getName(), target, args == null ? "[]" : java.util.Arrays.toString(args), e);
    }

    private String describe(WebElement element) {
        try {
            String raw = element.toString();
            int arrowIndex = raw.indexOf("-> ");
            if (arrowIndex == -1) {
                return raw;
            }
            String locator = raw.substring(arrowIndex + 3);
            if (locator.endsWith("]")) {
                locator = locator.substring(0, locator.length() - 1);
            }
            return locator;
        } catch (Exception e) {
            return "<stale or unavailable element>";
        }
    }

    private boolean isSensitiveField(String elementDescription) {
        String lower = elementDescription.toLowerCase();
        return lower.contains("password") || lower.contains("pwd") || lower.contains("secret");
    }

    private String joinKeys(CharSequence... keysToSend) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence key : keysToSend) {
            sb.append(key);
        }
        return sb.toString();
    }
}
