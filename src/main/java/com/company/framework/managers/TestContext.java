package com.company.framework.managers;

import com.company.framework.pageobjectmanager.PageObjectManager;

public class TestContext {
    private final DriverManager driverManager;
    private final PageObjectManager pageObjectManager;

    public TestContext() {
        this.driverManager = new DriverManager();
        this.driverManager.createSession();
        this.pageObjectManager = new PageObjectManager(driverManager.getPage());
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }
}
