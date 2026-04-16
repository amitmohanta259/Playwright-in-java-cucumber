package com.company.framework.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ApiClient {
    public Response get(String baseUrl, String endpoint) {
        return RestAssured.given().baseUri(baseUrl).get(endpoint);
    }

    public Response post(String baseUrl, String endpoint, Map<String, Object> payload) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(payload)
                .post(endpoint);
    }
}
