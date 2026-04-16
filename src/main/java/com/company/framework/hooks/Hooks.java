package com.company.framework.hooks;

import com.company.framework.managers.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before(order = 0)
    public void beforeScenario() {
        // Session is created in TestContext constructor per scenario.
    }

    @After(order = 0)
    public void afterScenario() {
        testContext.getDriverManager().closeSession();
    }
}
