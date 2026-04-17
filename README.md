# Playwright in Java Cucumber Framework

Production-ready automation framework template using:

- Playwright (UI automation)
- Java + Maven
- Cucumber (BDD)
- TestNG (runner + parallel support)
- RestAssured (API testing)
- Extent Reports (reporting)

---

## Project Folder Structure and Ownership

| Folder / Subfolder | Purpose | What code is maintained here |
|---|---|---|
| `src/main/java/com/company/framework/config` | Framework configuration | Config readers, constants, environment values, property mapping logic |
| `src/main/java/com/company/framework/driver` | Browser engine setup | Playwright creation, browser type resolution, launch options |
| `src/main/java/com/company/framework/locators` | UI locator repository | Centralized selector constants per page (`LoginPageLocators`, `DashboardPageLocators`) |
| `src/main/java/com/company/framework/pageobjects` | UI Page Object Model layer | Page Object classes (`BasePage`, `LoginPage`, `DashboardPage`) with reusable page actions |
| `src/main/java/com/company/framework/utils` | Reusable utility layer | Wait wrappers, JSON/Excel utilities, date-time helpers, common utility functions |
| `src/main/java/com/company/framework/api` | API abstraction layer | HTTP client wrappers, common API assertions, endpoint helper methods |
| `src/main/java/com/company/framework/api/pojoclasses` | API models | Request/response POJOs used for serialization/deserialization |
| `src/main/java/com/company/framework/managers` | Lifecycle and object orchestration | `DriverManager` and per-scenario context objects |
| `src/main/java/com/company/framework/pageobjectmanager` | Page object creation layer | `PageObjectManager` class that returns strongly-typed page objects |
| `src/main/java/com/company/framework/hooks` | Cucumber scenario hooks | `@Before` / `@After` setup-teardown and scenario cleanup |
| `src/main/java/com/company/framework/listeners` | Test lifecycle listeners | TestNG listeners, Extent report listener, logging hooks |
| `src/test/java/com/company/tests/runners` | Test execution entry points | Cucumber + TestNG runner classes and parallel execution settings |
| `src/test/java/com/company/tests/stepdefinitions` | BDD glue code | Step definitions that call page methods/API clients and assert behavior |
| `src/test/java/com/company/tests/dataproviders` | Data provider layer | TestNG DataProviders for Excel/JSON data-driven runs |
| `src/test/resources/features` | BDD specifications | Gherkin feature files and scenarios |
| `src/test/resources/testdata` | External test data | JSON/Excel test data files used by DataProviders |
| `src/test/resources/config` | Runtime environment config | `config.properties` for browser/env/headless/timeouts etc. |
| `src/test/resources/reports` | Reporting configuration | Extent report XML/configuration templates |
| `src/test/resources` | Test-level runtime resources | `testng.xml`, `logback.xml`, and shared non-code test resources |
| `target` | Generated artifacts | Build outputs, reports, compiled classes (auto-generated, do not edit) |

---

## Execution Flow (Start File -> Next File Chain)

### 1) Execution starts from

- **Start file:** `src/test/resources/testng.xml`
  - Maven Surefire is configured to execute this suite file.
  - Suite points to `com.company.tests.runners.TestRunner`.

### 2) Runner layer

- **Next file:** `src/test/java/com/company/tests/runners/TestRunner.java`
  - Reads feature path + glue packages via `@CucumberOptions`.
  - Registers listeners (`TestListener`, `ExtentReportListener`).
  - Scenario feed happens through Cucumber-TestNG integration.

### 3) Hook lifecycle

- **Next file:** `src/main/java/com/company/framework/hooks/Hooks.java`
  - `@Before`/`@After` executes for each scenario.
  - `@After` closes session to guarantee isolation.

### 4) Scenario context and driver creation

- **Next file chain:**  
  `src/main/java/com/company/framework/managers/TestContext.java`  
  -> `src/main/java/com/company/framework/managers/DriverManager.java`  
  -> `src/main/java/com/company/framework/driver/PlaywrightFactory.java`  
  -> `src/main/java/com/company/framework/driver/BrowserFactory.java`

- What happens:
  - Creates new Playwright instance.
  - Launches browser based on config.
  - Creates fresh `BrowserContext` and `Page` per scenario.
  - No static shared browser/page objects.

### 5) Step execution

- **Next file:** Step classes in `src/test/java/com/company/tests/stepdefinitions`
  - For UI: calls page actions via `PageObjectManager`.
  - For API: calls `ApiClient` / `ApiUtils`.

### 6) POM and API layers

- **UI chain:**  
  `PageObjectManager` -> `LoginPage` / `DashboardPage` -> `LoginPageLocators` / `DashboardPageLocators` -> Playwright actions.

- **API chain:**  
  `DataInsightSteps` -> `ApiClient` -> RestAssured -> API response -> `ApiUtils` assertions.

### 7) Reporting and listener hooks

- **Files:**  
  `src/main/java/com/company/framework/listeners/TestListener.java`  
  `src/main/java/com/company/framework/listeners/ExtentReportListener.java`

- What happens:
  - Captures test lifecycle events.
  - Logs start/failure/success.
  - Flushes Extent report in finish lifecycle.

### 8) Scenario teardown

- **Final lifecycle file:** `Hooks.java` (`@After`)
  - Closes context/browser/playwright.
  - Ensures strict test isolation for parallel runs.

---

## Why `package org.testng does not exist` happened

The error occurs when TestNG is referenced in code compiled under `src/main/java`, but TestNG dependency is set to test-only scope.

### Fix applied

- Updated `pom.xml` so these are available to main framework classes:
  - `org.testng:testng`
  - `io.cucumber:cucumber-java`
  - `io.cucumber:cucumber-picocontainer`

This resolves missing package imports in framework classes such as listeners/hooks/assert helpers.

---

## Run Commands

```bash
mvn clean test
```

If you want a specific suite:

```bash
mvn -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml test
```

## Headless Mode Configuration

Headless mode is controlled only from the framework config file (not Maven command flags).

Update this file:

- `src/test/resources/config/config.properties`

Update this key:

```properties
headless=true
```

- `headless=true` -> browser runs in headless mode (no UI).
- `headless=false` -> browser runs in headed mode (UI is visible).

Example:

```bash
mvn clean test
```

---

## Browser Launch and Navigation Code

### Browser launch code location

- File: `src/main/java/com/company/framework/managers/DriverManager.java`
- Method: `createSession()`

```java
public void createSession() {
    playwright = playwrightFactory.createPlaywright();
    browser = browserFactory.createBrowser(
            playwright,
            configReader.get("browser"),
            configReader.getBoolean("headless")
    );
    context = browser.newContext();
    page = context.newPage();
}
```

### Browser launch options (headless true/false) location

- File: `src/main/java/com/company/framework/driver/BrowserFactory.java`
- Method: `createBrowser(...)`

```java
public Browser createBrowser(Playwright playwright, String browserName, boolean headless) {
    BrowserTypeResolver resolver = new BrowserTypeResolver(playwright);
    return resolver.resolve(browserName).launch(
            new com.microsoft.playwright.BrowserType.LaunchOptions().setHeadless(headless)
    );
}
```

### Navigate/open URL code location

- File: `src/main/java/com/company/framework/pageobjects/LoginPage.java`
- Method: `open(String baseUrl)`

```java
public void open(String baseUrl) {
    page.navigate(baseUrl);
}
```

### Where the navigation method is called

- File: `src/test/java/com/company/tests/stepdefinitions/LoginSteps.java`
- Method: `userOpensLoginPage()`

```java
@Given("user opens login page")
public void userOpensLoginPage() {
    PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
    LoginPage objLoginPage = objPageObjectManager.getObjLoginPage();
    objLoginPage.open(configReader.get("baseUrl"));
}
```

---

## Login Feature Detailed Breakdown

This section explains the complete login automation flow: where code is stored, the important code blocks, and what each block does.

### 1) Login feature file (BDD test data and steps)

- File: `src/test/resources/features/login.feature`
- Purpose: Defines login scenarios in business-readable format and drives data variations.

```gherkin
Feature: Login

  @ui @smoke
  Scenario Outline: Successful login
    Given user opens login page
    When user logs in with username "<username>" and password "<password>"
    Then dashboard based on the "<status>" page title should contain "<Message>"

    Examples:
      | username | password | status  | Message                       |
      | PTuser   | Pass@123 | valid   | My Day                        |
      | PTuser   | Pass@12  | invalid | Invalid username or password. |
      |          | Pass@123 | invalid | Invalid username or password. |
      | PTuser   |          | invalid | Invalid username or password. |
```

What this does:
- Uses one `Scenario Outline` for both valid and invalid login.
- Sends values from `Examples` into step definitions using placeholders.
- `status` controls whether success assertion or error assertion is expected.

### 2) Test runner file (what gets executed)

- File: `src/test/java/com/company/tests/runners/TestRunner.java`
- Purpose: Connects Cucumber with TestNG and controls tag-based execution.

```java
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.company.framework.hooks", "com.company.tests.stepdefinitions"},
        plugin = {"pretty", "summary"},
        tags = "@ui and @smoke",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```

What this does:
- Runs only scenarios matching `@ui and @smoke`.
- Loads hooks and step definitions from `glue` packages.
- `parallel = false` executes scenarios one-by-one.

### 3) Step definition file (feature steps to Java actions)

- File: `src/test/java/com/company/tests/stepdefinitions/LoginSteps.java`
- Purpose: Maps each Gherkin step to page-object calls and assertions.

```java
@Given("user opens login page")
public void userOpensLoginPage() {
    PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
    objLoginPage = objPageObjectManager.getObjLoginPage();
    objLoginPage.open(configReader.get("baseUrl"));
}

@When("user logs in with username {string} and password {string}")
public void userLogsInWithUsernameAndPassword(String username, String password) {
    PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
    objLoginPage = objPageObjectManager.getObjLoginPage();
    objLoginPage.login(username, password);
}

@Then("dashboard based on the {string} page title should contain {string}")
public void dashboardBasedOnTheStatusPageTitleShouldContainMessage(String status, String expectedText) {
    PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
    objDashboardPage = objPageObjectManager.getObjDashboardPage();
    if(status.equals("valid")){
        Assert.assertTrue(objDashboardPage.getTitle().contains(expectedText));
    }else{
        Assert.assertTrue(objLoginPage.getErrorMessage().equals(expectedText));
    }
}
```

What this does:
- Gets page objects from `PageObjectManager` (same scenario `Page` instance).
- Calls login action with dynamic example data.
- For valid data: verifies dashboard title text.
- For invalid data: verifies login error message text.

### 4) Locator files (all selectors in one place)

- Files:
  - `src/main/java/com/company/framework/locators/LoginPageLocators.java`
  - `src/main/java/com/company/framework/locators/DashboardPageLocators.java`
- Purpose: Centralized UI selectors to avoid hardcoding locators in steps/page logic.

```java
public final class LoginPageLocators {
    public static final String USERNAME_INPUT = "//input[@id='username']";
    public static final String PASSWORD_INPUT = "//input[@id='password']";
    public static final String LOGIN_BUTTON = "//input[@id='kc-login']";
    public static final String errorMessage ="//*[@id='input-error']";
}
```

```java
public final class DashboardPageLocators {
    public static final String HEADER = "h1";
    public static final String TITLE = "//*[text()='My Day']";
}
```

What this does:
- If UI selectors change, update only locator classes.
- Keeps step definitions readable and stable.

### 5) Page object files (UI operations by page)

- Files:
  - `src/main/java/com/company/framework/pageobjects/BasePage.java`
  - `src/main/java/com/company/framework/pageobjects/LoginPage.java`
  - `src/main/java/com/company/framework/pageobjects/DashboardPage.java`
- Purpose: Encapsulates page behavior and reusable page-level methods.

`LoginPage.java`:

```java
public void open(String baseUrl) {
    page.navigate(baseUrl);
}

public void login(String username, String password) {
    page.fill(LoginPageLocators.USERNAME_INPUT, username);
    page.fill(LoginPageLocators.PASSWORD_INPUT, password);
    page.click(LoginPageLocators.LOGIN_BUTTON);
}

public String getErrorMessage(){
    return page.locator(LoginPageLocators.errorMessage).innerText();
}
```

`BasePage.java`:

```java
public String getTitle() {
    return page.locator(DashboardPageLocators.TITLE).innerText();
}
```

What this does:
- `open()` navigates to login URL.
- `login()` performs user action sequence.
- `getErrorMessage()` reads invalid-login error.
- `getTitle()` (in base class) is used by success validation path.

### 6) PageObjectManager file (page object factory/cache)

- File: `src/main/java/com/company/framework/pageobjectmanager/PageObjectManager.java`
- Purpose: Creates page objects once per scenario and reuses them.

```java
public LoginPage getObjLoginPage() {
    return (objLoginPage == null) ? objLoginPage = new LoginPage(page) : objLoginPage;
}

public DashboardPage getObjDashboardPage() {
    return (objDashboardPage == null) ? objDashboardPage = new DashboardPage(page) : objDashboardPage;
}
```

What this does:
- Ensures all page objects use the same Playwright `Page`.
- Avoids repeated object creation in every step method.
- Keeps step definitions cleaner and consistent.

### 7) End-to-end login flow in this framework

1. `TestRunner` picks `@ui and @smoke` login scenario.
2. Step definition opens login page via `LoginPage.open(baseUrl)`.
3. Credentials from `Examples` are passed to `LoginPage.login(...)`.
4. Assertion path splits by `status`:
   - `valid` -> dashboard title check.
   - `invalid` -> login error message check.

---

## Recommended Next Improvements

- Add environment profiles (`dev`, `qa`, `prod`) using `-Denv=...`.
- Add screenshot/video capture on failure in hooks/listeners.
- Add retry analyzer for known flaky external dependencies only.
- Add CI workflow to publish Extent report artifacts.

---

## Main Java Structure (Important)

The following components are present in `src/main/java/com/company/framework`:

- **Page Objects:** `pageobjects/BasePage.java`, `pageobjects/LoginPage.java`, `pageobjects/DashboardPage.java`
- **Page Object Manager:** `pageobjectmanager/PageObjectManager.java`
- **Locator folder:** `locators/LoginPageLocators.java`, `locators/DashboardPageLocators.java`
