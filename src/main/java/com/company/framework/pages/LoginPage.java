package com.company.framework.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final String usernameInput = "#username";
    private final String passwordInput = "#password";
    private final String loginButton = "button[type='submit']";

    public LoginPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl + "/login");
    }

    public void login(String username, String password) {
        page.fill(usernameInput, username);
        page.fill(passwordInput, password);
        page.click(loginButton);
    }
}
