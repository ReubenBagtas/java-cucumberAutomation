Feature: Example
  As a QA Engineer
  I want to make sure of stuff
  In order to ensure awesomeness across the world

  @demo
  @circle
  Scenario: Verify user can sign in
    Given user is on the login page
    When user logs in with valid admin credentials
    Then Work Orders dashboard is displayed

  @demo
  Scenario: Verify Work orders can be created
    Given user is logged in
    When a Work Order is created
    Then Work Order is visible on the Work Order dashboard

  @fail
  @demo
  Scenario: Verify error in reports
    Given user is on the login page
    When user logs in with valid admin credentials
    Then throw an error