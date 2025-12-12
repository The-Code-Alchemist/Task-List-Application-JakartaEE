Feature: Task Management
  As a user
  I want to be able to create and view tasks
  So that I can organize my work

  Scenario: Create a new task
    Given a user named "cucumber_user" exists
    When the user creates a task with title "Learn Cucumber"
    Then the task list for "cucumber_user" should contain "Learn Cucumber"