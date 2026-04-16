# Playwright Java Framework Structure

## Maven Layout

```text
src
 ├── main/java/com/company/framework
 ├── test/java/com/company/tests
 └── test/resources
```

## Folder-Wise Implementation Guide

- `src/main/java/com/company/framework/config`
  - **Purpose:** Centralized configuration and constants.
  - **Example files:** `ConfigReader.java`, `FrameworkConstants.java`
  - **Code here:** Property loading, environment mapping, immutable constants.

- `src/main/java/com/company/framework/driver`
  - **Purpose:** Browser and Playwright creation logic.
  - **Example files:** `PlaywrightFactory.java`, `BrowserFactory.java`
  - **Code here:** Browser type resolution, launch options, non-test engine setup.

- `src/main/java/com/company/framework/pageobjects`
  - **Purpose:** Page Object Model classes for UI flows.
  - **Example files:** `BasePage.java`, `LoginPage.java`, `DashboardPage.java`
  - **Code here:** Element locators, page actions, page-level assertions/helpers.

- `src/main/java/com/company/framework/pageobjectmanager`
  - **Purpose:** Centralized page object creation and access.
  - **Example files:** `PageObjectManager.java`
  - **Code here:** Build and return page object instances from the active `Page`.

- `src/main/java/com/company/framework/utils`
  - **Purpose:** Reusable helper utilities.
  - **Example files:** `WaitUtils.java`, `JsonUtils.java`, `ExcelUtils.java`, `CommonUtils.java`
  - **Code here:** Wait wrappers, file parsing, data conversion, timestamp and utility helpers.

- `src/main/java/com/company/framework/api`
  - **Purpose:** API test abstraction and reusable request logic.
  - **Example files:** `ApiClient.java`, `ApiUtils.java`, `pojoclasses/UserRequest.java`
  - **Code here:** HTTP operations, API assertions, request/response POJOs.

- `src/main/java/com/company/framework/managers`
  - **Purpose:** Lifecycle and object orchestration.
  - **Example files:** `DriverManager.java`, `TestContext.java`
  - **Code here:** Per-scenario browser session management and dependency wiring.

- `src/main/java/com/company/framework/hooks`
  - **Purpose:** Scenario-level setup/teardown.
  - **Example files:** `Hooks.java`
  - **Code here:** `@Before` and `@After` hooks, cleanup, test isolation handling.

- `src/main/java/com/company/framework/listeners`
  - **Purpose:** TestNG lifecycle listeners and reporting hooks.
  - **Example files:** `TestListener.java`, `ExtentReportListener.java`
  - **Code here:** Test start/end logs, pass/fail tracking, extent report flush.

- `src/test/java/com/company/tests/runners`
  - **Purpose:** Cucumber-TestNG execution entry points.
  - **Example files:** `TestRunner.java`
  - **Code here:** `@CucumberOptions`, scenario DataProvider config, listener registration.

- `src/test/java/com/company/tests/stepdefinitions`
  - **Purpose:** Glue code mapping feature steps to framework logic.
  - **Example files:** `LoginSteps.java`, `DataInsightSteps.java`
  - **Code here:** Step-level orchestration using pages, API clients, and context objects.

- `src/test/java/com/company/tests/dataproviders`
  - **Purpose:** TestNG data providers from external data.
  - **Example files:** `TestDataProvider.java`
  - **Code here:** Excel and JSON adapters returning `Object[][]`.

- `src/test/resources/features`
  - **Purpose:** Gherkin business scenarios.
  - **Example files:** `login.feature`, `data_insight.feature`
  - **Code here:** Human-readable scenario definitions and tags.

- `src/test/resources/testdata`
  - **Purpose:** Externalized test data.
  - **Example files:** `testdata.xlsx`, `testdata.json`
  - **Code here:** Input data only, no execution logic.

- `src/test/resources/config`
  - **Purpose:** Runtime environment settings.
  - **Example files:** `config.properties`
  - **Code here:** Browser, environment URL, headless mode, timeouts.

- `src/test/resources/reports`
  - **Purpose:** Report configuration templates.
  - **Example files:** `extent-config.xml`
  - **Code here:** Report visual and metadata config.

- `src/test/resources`
  - **Purpose:** Supporting framework resources.
  - **Example files:** `testng.xml`, `logback.xml`
  - **Code here:** Suite setup and logging configuration.

## Naming Conventions Best Practices

- Use package names in lowercase: `com.company.framework.pageobjects`.
- Use class names in PascalCase: `LoginPage`, `DriverManager`.
- Use methods in camelCase with action intent: `createSession`, `validateStatusCode`.
- Use variables in camelCase and descriptive names: `baseUrl`, `expectedStatusCode`.
- Keep feature filenames in snake_case or lowercase words: `data_insight.feature`.

## Scalability and Parallel Execution Improvements

- Create fresh Playwright `BrowserContext` and `Page` per scenario (already designed via `TestContext`).
- Keep scenario state instance-scoped; avoid global singletons in test flow.
- Run `@DataProvider(parallel = true)` and configure `thread-count` in `testng.xml`.
- Add environment profiles (`dev`, `qa`, `prod`) and config override strategy.
- Split pages by module and add component objects for reusable sections.
- Add retry analyzer only for known flaky external dependencies.

## Common Mistakes to Avoid

- Sharing `Page`, `BrowserContext`, or test data objects across threads.
- Creating static mutable caches for page objects or request payloads.
- Hardcoding waits (`Thread.sleep`) instead of explicit waits.
- Putting assertions inside page classes for every operation (prefer step/test layer assertions).
- Mixing UI locators and API payload builders in the same class.
