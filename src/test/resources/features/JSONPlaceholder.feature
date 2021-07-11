Feature: Validate email address in comments

  Scenario Outline: Search for the user <userName>
    When I search for the user with username "<userName>"
    Then I should retrieve the user details
    Examples:
      | userName |
      | Delphine |

  Scenario: Search for posts written by the user
    When I retrieve the posts written by the user
    Then I should retrieve all the posts

  # To Highlight: These first 3 scenarios can also be written in a single scenario but inorder to maintain better readability, I have implemented in this way
  # These 3 scenarios are inter-dependant as per requirement - user -> all posts written by that user -> all comments posted for each post
  Scenario: Retrieve the comments and validate email's format
    When I retrieve the comments for each post
    Then I should validate emails in the comment are in proper format

  Scenario: Resource unavailable error
    When an user tries to retrieve the "resource" which is not available
    Then resource unavailable error should be thrown

  Scenario: Resource creation - fake response validation
    When an user writes a new post and it should be created
    But the actual posts resource should not updated with new post