# Selenium WebDriver Task

This project is a Maven-based Selenium WebDriver automation task implemented in Java.
Selenium WebDriver + TestNG automation framework for [saucedemo.com](https://www.saucedemo.com/), built as a layered TAF: **Core** (driver/config/logging), **Business** (steps + page objects), **Test** (TestNG scenarios).

## Tech Stack

- Java 17, Maven
- Selenium WebDriver 4.46.0, WebDriverManager 5.9.2
- TestNG 7.12.0
- Allure TestNG 2.35.4
- Log4j2 2.26.1 (via SLF4J2)

## Project Structure

```
src
├── main/java
│   ├── business/
│   │   ├── model/Product.java
│   │   └── steps/           # CheckoutSteps, LoginSteps, ShoppingSteps
│   ├── core/
│   │   ├── config/ConfigReader.java
│   │   ├── driver/          # BrowserOptionsFactory, BrowserType, DriverFactory, LoggingWebDriverListener
│   │   └── reporting/ScreenshotUtils.java
│   ├── pages/
│   │   ├── base/BasePage.java
│   │   └── saucedemopages/  # LoginPage, InventoryPage, InventoryItemPage, ShoppingCart, CheckoutStep1/2, CheckoutComplete
│   └── utilities/UtilityMethods.java
├── main/resources/config/
│   ├── qa.properties
│   └── staging.properties
├── test/java
│   ├── base/                # BaseTest, TestData
│   ├── cucumberglue/        # Hooks, LoginStepDefinitions
│   ├── cucumberrunner/TestRunner.java
│   ├── testscenario1/ScenarioOneTest.java
│   ├── testscenario2/ScenarioTwoTest.java
│   └── testscenario3/ScenarioThreeTest.java
└── test/resources/
    ├── features/login.feature
    ├── cucumber.xml
    ├── log4j2.xml
    ├── reportportal.properties
    ├── smoke.xml
    ├── regression.xml
    └── testng.xml
```

## Configuration

Environment properties: `src/main/resources/config/{qa,staging}.properties` (`base.url`, `browser`, `environment`).

Select environment / browser at runtime (defaults: `env=qa`, browser from properties file):

```bash
mvn test -Denv=staging -Dbrowser=firefox
```

Supported browsers: `chrome`, `firefox`, `edge`.

## Running Tests

```bash
mvn clean test                                   # default suite (smoke.xml, set in pom.xml)
mvn clean test -DsuiteXmlFile=regression.xml      # run regression suite
mvn clean test -Dtest=testscenario1.ScenarioOneTest   # single class
```

## Test Suites

| Suite | Scope | Mode |
|---|---|---|
| `smoke.xml` | `smoke` group tests | Sequential |
| `regression.xml` | `regression` group tests | Parallel by class (10 threads) |
| `testng.xml` | All test classes | Parallel by class (10 threads) |

## Reporting & Logging

- Allure results: `target/allure-results` → `mvn allure:report` / `mvn allure:serve`
- Failed tests auto-attach a screenshot (saved to `screenshots/`)
- Logs: console + daily rolling file at `logs/test-execution.log` (30-day retention), configured in `log4j2.xml`

## Test Scenarios

- **Scenario 1** – Login, add/remove cart items, verify cart persists across navigation
- **Scenario 2** – Login, add items, complete full checkout flow
- **Scenario 3** – Invalid/locked-out/valid login, sort products, view item details, add to cart from item page

## Notes

- Test methods within each scenario class use `dependsOnMethods`, so classes must run as a whole, not as isolated methods.
- `DriverFactory` uses `ThreadLocal<WebDriver>` for safe parallel execution.
