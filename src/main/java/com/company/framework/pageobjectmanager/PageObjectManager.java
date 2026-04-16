package com.company.framework.pageobjectmanager;

import com.company.framework.pageobjects.DashboardPage;
import com.company.framework.pageobjects.LoginPage;
import com.microsoft.playwright.Page;

public class PageObjectManager {
    private final Page page;
    private LoginPage objLoginPage;
    private DashboardPage objDashboardPage;

    public PageObjectManager(Page page) {
        this.page = page;
    }

    public LoginPage getObjLoginPage() {
        return (objLoginPage == null) ? objLoginPage = new LoginPage(page) : objLoginPage;
    }

    public DashboardPage getObjDashboardPage() {
        return (objDashboardPage == null) ? objDashboardPage = new DashboardPage(page) : objDashboardPage;
    }
}
