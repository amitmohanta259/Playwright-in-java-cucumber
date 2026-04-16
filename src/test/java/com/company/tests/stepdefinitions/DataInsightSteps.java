package com.company.tests.stepdefinitions;

import com.company.framework.api.ApiClient;
import com.company.framework.api.ApiUtils;
import com.company.framework.config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class DataInsightSteps {
    private final ApiClient apiClient = new ApiClient();
    private final ApiUtils apiUtils = new ApiUtils();
    private final ConfigReader configReader = new ConfigReader();
    private Response response;

    @When("user calls api endpoint {string}")
    public void userCallsApiEndpoint(String endpoint) {
        String apiBaseUrl = configReader.get("apiBaseUrl");
        response = apiClient.get(apiBaseUrl, endpoint);
    }

    @Then("api response status should be {int}")
    public void apiResponseStatusShouldBe(int expectedStatusCode) {
        apiUtils.validateStatusCode(response, expectedStatusCode);
    }
}
