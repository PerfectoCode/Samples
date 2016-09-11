Feature: As a end user I would like to navigate to google.
  Then I would like to search for Perfecto-Code repository in GitHub.
  After that I want to make sure Perfecto is in the page's title.

  Scenario: Searching and navigating to Perfecto-Code repository.
    Given I in google search page
    Then searching for Perfecto-Code repositorys
    And click the first search result
    Then I validate the page's title.