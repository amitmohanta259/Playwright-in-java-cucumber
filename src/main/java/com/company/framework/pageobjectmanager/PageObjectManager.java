package com.company.framework.pageobjectmanager;

import com.company.framework.pageobjects.DashboardPage;
import com.company.framework.pageobjects.LoginPage;
import com.microsoft.playwright.Page;

public class PageObjectManager {
    private final Page page;

    public PageObjectManager(Page page) {
        this.page = page;
    }

    public LoginPage loginPage() {
        return new LoginPage(page);
    }

    public DashboardPage dashboardPage() {
        return new DashboardPage(page);
    }
}
