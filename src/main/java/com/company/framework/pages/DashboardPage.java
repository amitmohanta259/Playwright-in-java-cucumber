package com.company.framework.pages;

import com.microsoft.playwright.Page;

public class DashboardPage extends BasePage {
    private final String header = "h1";

    public DashboardPage(Page page) {
        super(page);
    }

    public String getHeaderText() {
        return page.textContent(header);
    }
}
