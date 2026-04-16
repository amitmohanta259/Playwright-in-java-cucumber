package com.company.tests.stepdefinitions;

import com.company.framework.config.ConfigReader;
import com.company.framework.managers.TestContext;
import com.company.framework.pageobjectmanager.PageObjectManager;
import com.company.framework.pageobjects.DashboardPage;
import com.company.framework.pageobjects.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    private final TestContext testContext;
    private final ConfigReader configReader = new ConfigReader();

    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("user opens login page")
    public void userOpensLoginPage() {
        PageObjectManager pageObjectManager = testContext.getPageObjectManager();
        LoginPage loginPage = pageObjectManager.loginPage();
        loginPage.open(configReader.get("baseUrl"));
    }

    @When("user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        PageObjectManager pageObjectManager = testContext.getPageObjectManager();
        LoginPage loginPage = pageObjectManager.loginPage();
        loginPage.login(username, password);
    }

    @Then("dashboard page title should contain {string}")
    public void dashboardPageTitleShouldContain(String expectedText) {
        PageObjectManager pageObjectManager = testContext.getPageObjectManager();
        DashboardPage dashboardPage = pageObjectManager.dashboardPage();
        Assert.assertTrue(dashboardPage.getTitle().contains(expectedText));
    }
}
