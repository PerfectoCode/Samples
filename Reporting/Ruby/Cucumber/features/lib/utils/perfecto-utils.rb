require 'selenium-webdriver'
require 'perfecto-reporting'

# module Utils
#
# This module contains utils for easier integration with perfecto .
#
# Available utils:
# Device - device utils such as initialize
# Reporting - initialize client, create steps ...
# Cucumber - get scenario instance .
#
# Example:
# Utils::Device.create_device my_host my_user ...
module Utils

  module Device

    @@driver = nil

    # Getting a new device from Perfecto Cloud
    #
    # host - remote address (perfecto lab)
    # user - username for perfecto lab
    # pass - password for perfecto lab
    # capabilities - device desired capabilities
    #
    # return new selenium webdriver instance
    def self.create_device host, user, pass, capabilities

      capabilities['user'] = user
      capabilities['password'] = pass

      # create a new driver instance
      @@driver = Selenium::WebDriver.for(:remote, :url => 'http://' + host + '/nexperience/perfectomobile/wd/hub', :desired_capabilities => capabilities)

      # setting timeouts
      @@driver.manage.timeouts.implicit_wait = 15
      @@driver.manage.timeouts.page_load = 10

      return @@driver
    end

    def self.driver
      @@driver
    end

  end # end Device

  module Reporting

    @@reportiumClient
    @@currentStep = 0

    # Getting a new instance of reporting client
    #
    # driver - must be given in order to initialize new reporting client
    # *tags - optional, one or more tags to be attached to the report
    # project - optional, project instance with name and version
    # job - optional, jenkins job name and number
    #
    # return new reporting client instance
    def self.create_reporting_client driver, *tags
      perfectoExecutionContext = PerfectoExecutionContext.new(
          PerfectoExecutionContext::PerfectoExecutionContextBuilder
              .withContextTags(*tags) # Optional
              .withWebDriver(driver)
              .build)

      @@reportiumClient = PerfectoReportiumClient.new(perfectoExecutionContext)
      return @@reportiumClient
    end

    def self.start_new_test test_name, *tags
      @@currentStep = 0
      @@reportiumClient.testStart test_name,  TestContext.new(*tags)
    end

    # helper function to log a test step
    #
    # log a test step using scenario instance
    # scenario step name will be assigned to be reporting step description
    def self.logStep
      unless @@reportiumClient.nil?
        step_name = Utils::Cucumber.scenario.test_steps[@@currentStep].name
        @@reportiumClient.testStep step_name
      else
        raise 'Reporting client uninitialized'
      end
    end

    # returns reporting client instance
    def self.reportiumClient
      @@reportiumClient
    end

  end # end Reporting

  module Cucumber

    @@scenario

    # getter scenario instance
    #
    # return scenario instance
    def self.scenario
      @@scenario
    end

    # setter scenario instance
    #
    # scenario - scenario instance to be assigned
    def self.scenario= scenario
      @@scenario = scenario
    end

  end # end Cucumber

end

