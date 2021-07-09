Feature: Validate email address in comments

  Scenario: Search for Delphine in JSONPlaceholder API
    When I search for the user with username "Delphine"
    Then I should retrieve the user details

  Scenario: Search for posts written by Delphine
    When I retrieve the posts written by the user
    Then I should retrieve all the posts

  Scenario: Retrieve the comments and validate email's format
    When I retrieve the comments for each post
    Then I should validate emails in the comment are in proper format