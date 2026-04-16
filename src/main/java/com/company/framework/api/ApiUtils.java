package com.company.framework.api;

import io.restassured.response.Response;
import org.testng.Assert;

public class ApiUtils {
    public void validateStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Unexpected status code");
    }
}
