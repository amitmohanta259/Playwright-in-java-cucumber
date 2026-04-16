Feature: Login

  @ui @smoke
  Scenario: Successful login
    Given user opens login page
    When user logs in with username "standard_user" and password "secret_sauce"
    Then dashboard page title should contain "Dashboard"
