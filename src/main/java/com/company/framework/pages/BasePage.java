package com.company.framework.pages;

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
        return page.title();
    }
}
