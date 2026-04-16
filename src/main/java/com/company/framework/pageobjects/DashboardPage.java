package com.company.framework.pageobjects;

import com.company.framework.locators.DashboardPageLocators;
import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {
    public DashboardPage(Page page) {
        super(page);
    }

    public String getHeaderText() {
        return page.textContent(DashboardPageLocators.HEADER);
    }
}
