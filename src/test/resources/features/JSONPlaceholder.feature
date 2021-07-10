Feature: Validate email address in comments

  Scenario: Search for Delphine
    When I search for the user with username "Delphine"
    Then I should retrieve the user details

  Scenario: Search for posts written by Delphine
    When I retrieve the posts written by the user
    Then I should retrieve all the posts

  Scenario: Retrieve the comments and validate email's format
    When I retrieve the comments for each post
    Then I should validate emails in the comment are in proper format

  Scenario: Resource unavailable error
    When an user tries to retrieve the "resource" which is not available
    Then resource unavailable error should be thrown