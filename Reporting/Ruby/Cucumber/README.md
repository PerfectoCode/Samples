## Cucumber

This Project shows how to build a Ruby test with Cucumber testing framework integrated with PerfectoLab and Perfecto Reporting.

:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/C%23-Implementation) for the complete SDK documentation.<br/>
:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/Reporting) to get started with Perfecto Reporting. 

## Getting Started

Install Perfecto-Reporting and Cucumber Gems using the following Gem commands:<br/>
`gem install perfecto-reporting`<br/>
`gem install cucumber`

## Integration With Perfecto :

[perfecto-utils.rb](features/lib/utils/perfecto-utils.rb) Within features/lib/utils directory contains three sub modules:<br/>
```Ruby
Utils::Device # Helps to create remote webdriver with Perfecto Lab.
Utils::Reporting # Creates Reporting client and include test start and step methods.
Utils::Cucumber # Saves the scenario test instance.
```
For more information about this three sub modules click [here](features/lib/utils/perfecto-utils.rb) to see the Utils module implementation. 

## Feature file and Steps implementation:

The [feature](features/feature.Feature) file contains one or more scenarios for implementation.<br/>
Each scenario contains one or more steps, which must start with one of the keywords *Given*, *When*, *Then* and *And*.<br/>
```Feature
  Scenario: Getting insurance from Geico
    When I navigate to Geico home page
    Then I choose Motorcycle & ATV insurance
    Then Insert my ZIP Code: 50145
    And Click start button
    Given I click No on the insurance checkbox
    And I submit the Customer Information form
```

The implementation of this steps is within the [step_definitions](features/step_definitions) directory.<br/>
For this project the the implementation of the steps is within the [steps_implementation.rb](features/step_definitions/steps_implementation.rb) file.

Test behavior of the test such as before and after each scenario methods implemented in [env.rb](features/support/env.rb) file.<br/>
For example the before each scenario the following method will be executed:
```Ruby
# Do before each scenario
#
# saves scenario (test instance) in utils module
# that way it's available in the test
#
# Create a new webdriver instance
# Create a new reporting client
# Log a new test with scenario name
Before do |scenario|

  Utils::Cucumber.scenario = scenario

  host = 'My_Host.perfectomobile.com'
  user = 'My_User'
  pass = 'My_Pass'

  capabilities = {
      :platformName => 'Android',
      :model => '',
      :platformVersion => '',
      :browserName => '',
      :browserVersion => '',
      :deviceName => '',
      :user => user,
      :password => pass
  }

  Utils::Device.create_device host, user, pass, capabilities
  Utils::Reporting.create_reporting_client(Utils::Device.driver, 'ruby', 'cucumber', 'testTag3' ) # optional to use one or more test tags.
  Utils::Reporting.start_new_test scenario.name, 'TestTag1', 'TestTag2' # optional to use one or more test tags.

end
``` 

The sample uses the following require statements:<br/>
```Ruby
require_relative '../lib/utils/perfecto-utils' # Utils for integration with perfecto
require_relative '../lib/geico_pages' # Page Object model
```

Step implementation:<br/>
The following lines of code from the steps implementation file implements the first step in the scenario:<br/>
```Ruby
When /^I navigate to Geico home page$/ do
  Utils::Reporting.logStep
  @homePage = Geico::HomePage.new Utils::Device.driver
end
```
For each step Cucumber will look for a matching step definition. Each step definition consists of a *keyword*, a *string* or *regular expression*.<br/>
`Utils::Reporting.logStep` - Will log the step in reporting, this step will be available in reporting ui. <br/>
`@homePage = Geico::HomePage.new Utils::Device.driver` - Using the Page object model, for the page implementation see [geico_pages.rb](features/lib/geico_pages.rb) file. 

## Running the project :

Use Cucumber in the command line: <br/>
`cucumber` <br/>
To see more available options use:<br/>
`cucumber -h`
