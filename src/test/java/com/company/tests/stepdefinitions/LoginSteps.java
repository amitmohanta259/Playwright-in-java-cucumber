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
    LoginPage objLoginPage;
    DashboardPage objDashboardPage;
    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("user opens login page")
    public void userOpensLoginPage() {
        PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
        objLoginPage = objPageObjectManager.getObjLoginPage();
        objLoginPage.open(configReader.get("baseUrl"));
    }

    @When("user logs in with username {string} and password {string}")
    public void userLogsInWithUsernameAndPassword(String username, String password) {
        PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
        objLoginPage = objPageObjectManager.getObjLoginPage();
        objLoginPage.login(username, password);
    }

    @Then("dashboard based on the {string} page title should contain {string}")
    public void dashboardBasedOnTheStatusPageTitleShouldContainMessage(String status, String expectedText) {
        PageObjectManager objPageObjectManager = testContext.getPageObjectManager();
        objDashboardPage = objPageObjectManager.getObjDashboardPage();
        if(status.equals("valid")){
            Assert.assertTrue(objDashboardPage.getTitle().contains(expectedText));
        }else{
            Assert.assertTrue(objLoginPage.getErrorMessage().equals(expectedText));
        }

    }
}
