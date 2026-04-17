package com.company.framework.pageobjects;

import com.company.framework.locators.DashboardPageLocators;
import com.company.framework.utils.WaitUtils;
import com.microsoft.playwright.Page;

public class BasePage {
    protected final Page page;
    protected final WaitUtils waitUtils;

    public BasePage(Page page) {
        this.page = page;
        this.waitUtils = new WaitUtils(page);
    }

    public String getTitle() {
        return page.locator(DashboardPageLocators.TITLE).innerText();

    }
}
