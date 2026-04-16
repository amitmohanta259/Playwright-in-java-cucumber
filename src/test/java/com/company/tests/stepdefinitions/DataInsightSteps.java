package com.company.tests.stepdefinitions;

import com.company.framework.api.ApiClient;
import com.company.framework.api.ApiUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class DataInsightSteps {
    private final ApiClient apiClient = new ApiClient();
    private final ApiUtils apiUtils = new ApiUtils();
    private Response response;

    @When("user calls api endpoint {string}")
    public void userCallsApiEndpoint(String endpoint) {
        response = apiClient.get("https://reqres.in", endpoint);
    }

    @Then("api response status should be {int}")
    public void apiResponseStatusShouldBe(int expectedStatusCode) {
        apiUtils.validateStatusCode(response, expectedStatusCode);
    }
}
