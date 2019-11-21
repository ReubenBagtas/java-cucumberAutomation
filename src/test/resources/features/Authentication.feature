Feature: Example
  As a user
  I want to be able to log in

  @auth
  @smoke
  Scenario: Verify user can sign in
    Given user is on the login page
    When user logs in with valid admin credentials
    Then Work Orders dashboard is displayed

  @smoke
  @auth
  Scenario: Verify user can sign out
    Given user is on the login page
    And user logs in with valid admin credentials
    When user logs out
    Then user is directed to the login page

  @smoke
  Scenario: Verify user can sign up
    Given user is on the signup page
    When user signs up
    Then Work Orders dashboard is displayed