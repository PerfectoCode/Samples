Feature: As a user I would like to navigate to google.
  Then I would like to search for Perfecto-Code repository in GitHub.
  After that I want to make sure that the page's title is what i expected for.

  @ScenarioTag
  Scenario: Searching and navigating to Perfecto-Code repository.
    Given I'm on the google search page
    And I search for Perfecto-Code repository
    And click the first search result
    Then I validate the page's title.