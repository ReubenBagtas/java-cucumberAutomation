Feature: Example
  As a QA Engineer
  I want to make sure of stuff
  In order to ensure awesomeness across the world

  @requests
  @smoke
  Scenario: Verify Admin user can create Request from Requests page
    Given user is on the login page
    And user logs in with valid admin credentials
    When a Request is created
    Then Request is visible on the Requests dashboard

