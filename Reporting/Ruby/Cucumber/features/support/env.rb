require_relative '../lib/utils/perfecto-utils'

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
  Utils::Reporting.create_reporting_client(Utils::Device.driver, 'Tag1', 'Tag2', 'Tag3') # Optional, add more tags 
  Utils::Reporting.start_new_test scenario.name, 'Tag1' # Optional, add more tags 

end

# Do after each scenarios
#
# check for scenario status
# if scenario success generates a successful test report,
# otherwise generates failure test report
#
# unless the driver nil quiting the session
After do |scenario|

  if scenario.failed?
    Utils::Reporting.reportiumClient.testStop TestResultFactory.createFailure scenario.exception.message, scenario.exception
  else
    Utils::Reporting.reportiumClient.testStop TestResultFactory.createSuccess
  end

  puts '========================================================================================'
  puts 'report-url: ' + Utils::Reporting.reportiumClient.getReportUrl
  puts '========================================================================================'

  unless Utils::Device.driver.nil?
    Utils::Device.driver.quit
  end

end