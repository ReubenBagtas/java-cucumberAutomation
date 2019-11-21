Feature: Example
  As a QA Engineer
  I want to make sure of stuff
  In order to ensure awesomeness across the world

  @workorders
  @smoke
  Scenario: Verify user can make Work Orders
    Given user is logged in
    When a Work Order is created
    Then Work Order is visible on the Work Order dashboard