Feature: Login

  @ui @smoke
  Scenario Outline: Successful login
    Given user opens login page
    When user logs in with username "<username>" and password "<password>"
    Then dashboard page title should contain "Swag Labs"

    Examples:
      | username | password |
      | PTuser   | Pass@123 |
