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
