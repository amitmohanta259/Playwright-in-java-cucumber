Feature: Data insight API

  @api @smoke
  Scenario: Validate users API
    When user calls api endpoint "/users/1"
    Then api response status should be 200
