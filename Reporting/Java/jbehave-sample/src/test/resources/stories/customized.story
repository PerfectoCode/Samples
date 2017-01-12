Narrative:
In order to navigate to the PerfectoCode GitHub repository
As a user or a customer
I want to make sure google search works as well

Meta:
@PerfectoTags Tag1, Tag2, a longer tag

Scenario: Search in google Jbehave
Meta:
@PerfectoTags FirstScenario, Success
Given I navigate to google
When I search for PerfectoCode GitHub
Then I click the first search result
Then I validate Perfecto is in the page's title

Scenario: Search in google should fail Jbehave
Meta:
@PerfectoTags SecondScenario, Failure
Given I navigate to google
When I search for PerfectoCode GitHub
Then I click imaginary button
Then I validate Perfecto is in the page's title
