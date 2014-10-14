Feature: Automatically set up the database
  The database is automatically updated on the first start-up when a new version of the application is installed.

  Scenario: Implement a new database change set
    Given the database does not have the change set named 'xyz:1'
    When a version of the application that knows the changeset named 'xyz:1' is run
    Then the database should now have the change set named 'xyz:1'
