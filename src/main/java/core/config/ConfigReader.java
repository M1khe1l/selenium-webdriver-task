package core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static final String DEFAULT_ENV = "qa";
    private static final Properties PROPERTIES = new Properties();

    static {
        String env = System.getProperty("env", DEFAULT_ENV);
        String fileName = "config/" + env.toLowerCase() + ".properties";
        logger.info("Loading configuration for environment: '{}' (file: {})", env, fileName);

        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalStateException("Configuration file not found on classpath: " + fileName);
            }
            PROPERTIES.load(inputStream);
            logger.debug("Configuration loaded successfully: {}", PROPERTIES);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load configuration file: " + fileName, e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' was requested but not found in configuration file", key);
        }
        return value;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static String getEnvironment() {
        return get("environment");
    }
}
