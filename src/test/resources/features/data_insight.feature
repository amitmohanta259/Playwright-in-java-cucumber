Feature: Data insight API

  @api @smoke
  Scenario: Validate users API
    When user calls api endpoint "/api/users?page=2"
    Then api response status should be 200
