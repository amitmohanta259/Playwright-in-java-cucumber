package com.company.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners({
        com.company.framework.listeners.TestListener.class,
        com.company.framework.listeners.ExtentReportListener.class
})
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.company.framework.hooks", "com.company.tests.stepdefinitions"},
        plugin = {"pretty", "summary"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
