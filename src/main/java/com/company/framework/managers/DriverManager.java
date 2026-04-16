package com.company.framework.managers;

import com.company.framework.config.ConfigReader;
import com.company.framework.driver.BrowserFactory;
import com.company.framework.driver.PlaywrightFactory;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class DriverManager {
    private final PlaywrightFactory playwrightFactory = new PlaywrightFactory();
    private final BrowserFactory browserFactory = new BrowserFactory();
    private final ConfigReader configReader = new ConfigReader();

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

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

    public Page getPage() {
        return page;
    }

    public void closeSession() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
