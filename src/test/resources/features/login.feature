Feature: Login

  @ui @smoke
  Scenario Outline: Successful login
    Given user opens login page
    When user logs in with username "<username>" and password "<password>"
    Then dashboard based on the "<status>" page title should contain "<Message>"

    Examples:
      | username | password | status  | Message                       |
      | PTuser   | Pass@123 | valid   | My Day                        |
      | PTuser   | Pass@12  | invalid | Invalid username or password. |
      |          | Pass@123 | invalid | Invalid username or password. |
      | PTuser   |          | invalid | Invalid username or password. |
