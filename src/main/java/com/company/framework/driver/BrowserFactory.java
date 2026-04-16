package com.company.framework.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
    public Browser createBrowser(Playwright playwright, String browserName, boolean headless) {
        BrowserTypeResolver resolver = new BrowserTypeResolver(playwright);
        return resolver.resolve(browserName).launch(
                new com.microsoft.playwright.BrowserType.LaunchOptions().setHeadless(headless)
        );
    }

    private static class BrowserTypeResolver {
        private final Playwright playwright;

        private BrowserTypeResolver(Playwright playwright) {
            this.playwright = playwright;
        }

        private com.microsoft.playwright.BrowserType resolve(String browserName) {
            return switch (browserName.toLowerCase()) {
                case "chromium" -> playwright.chromium();
                case "firefox" -> playwright.firefox();
                case "webkit" -> playwright.webkit();
                default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
            };
        }
    }
}
