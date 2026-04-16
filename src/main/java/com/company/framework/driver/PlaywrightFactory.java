package com.company.framework.driver;

import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
    public Playwright createPlaywright() {
        return Playwright.create();
    }
}
