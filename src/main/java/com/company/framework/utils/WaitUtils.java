package com.company.framework.utils;

import com.microsoft.playwright.Page;

public class WaitUtils {
    private final Page page;

    public WaitUtils(Page page) {
        this.page = page;
    }

    public void waitForSelector(String selector) {
        page.waitForSelector(selector);
    }
}
