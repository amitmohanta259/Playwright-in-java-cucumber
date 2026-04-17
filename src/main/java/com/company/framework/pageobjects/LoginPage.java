package com.company.framework.pageobjects;

import com.company.framework.locators.LoginPageLocators;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    public LoginPage(Page page) {
        super(page);
    }

    public void open(String baseUrl) {
        page.navigate(baseUrl);
    }

    public void login(String username, String password) {
        page.fill(LoginPageLocators.USERNAME_INPUT, username);
        page.fill(LoginPageLocators.PASSWORD_INPUT, password);
        page.click(LoginPageLocators.LOGIN_BUTTON);
    }

    public String getErrorMessage(){
        return page.locator(LoginPageLocators.errorMessage).innerText();
    }
}
