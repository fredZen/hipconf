Feature: Edit a session

  Scenario: Publish a new session when logged in
    We want to minimize the obstacles to creating a session, so we allow users to create a session with just a working title.

    Given I am logged in as Paul
    When I create a new session titled "Just what the doctor ordered"
    And I publish the session
    Then the session "Just what the doctor ordered" by Paul is visible in the list of sessions
