package com.company.framework.managers;

import com.company.framework.pages.DashboardPage;
import com.company.framework.pages.LoginPage;
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
