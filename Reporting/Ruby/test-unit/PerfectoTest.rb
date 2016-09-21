require 'selenium-webdriver'
require 'test/unit'
require 'perfecto-reporting'

# PerfectoTest
#
# This class defines the test behavior and integration with Perfecto Lab.
class PerfectoTest < Test::Unit::TestCase

  # driver - selenium driver
  # reportiumClient - handle reporting methods against reporting server
  # exception - if test fails log the exception into the report
  attr_accessor :driver, :reportiumClient, :exception

  # initialize a new fixture instance
  #
  # name - the name of the current test to run
  #
  # TODO:
  # 1. Set your device capabilities.
  # 2. Set your Perfecto lab user, password and host.
  def initialize(name = nil)
    super(name) unless name.nil?

    host = 'MY_LAB.perfectomobile.com'
    user = 'My_User'
    pass = 'My_Pass'

    # device capabilities:
    capabilities = {
        :platformName => 'Android',
        :model => '',
        :platformVersion => '',
        :browserName => 'mobileOS',
        :browserVersion => '',
        :deviceName => '',
        :user => user,
        :password => pass
    }

    # create a new driver instance
    @driver = Selenium::WebDriver.for(:remote, :url => 'http://' + host + '/nexperience/perfectomobile/wd/hub', :desired_capabilities => capabilities)

    # create new reportium client
    create_reportium_client
  end

  # initialize reportium client
  #
  # TODO: (optional)
  # Define a project, job and context tags for one or more tests.
  def create_reportium_client
    perfectoExecutionContext = PerfectoExecutionContext.new(
        PerfectoExecutionContext::PerfectoExecutionContextBuilder
            .withProject(Project.new('Reporting SDK Ruby', '1')) # Optional
            .withJob(Job.new('Ruby Job', 1)) # Optional
            .withContextTags('Test tag1', 'Test tag2', 'Test tag3') # Optional
            .withWebDriver(@driver)
            .build)

    @reportiumClient = PerfectoReportiumClient.new(perfectoExecutionContext)
  end

  # setup method
  #
  # setup method runs before each test and indicate a reporting.
  # add here additional before test settings.
  def setup
    puts 'starting a new test: ' + self.name
    @reportiumClient.testStart(self.name, TestContext.new('Tag1', 'Tag2', 'unittest'))
  end

  # End test method
  #
  # This method run after each test.
  # If the test succeeded create a successful test report,
  # Otherwise create a failure test report.
  #
  # Closing the current session.
  def teardown

    if self.passed?
      @reportiumClient.testStop(TestResultFactory.createSuccess)
    else
      @reportiumClient.testStop(TestResultFactory.createFailure(@exception.message, @exception))
    end

    puts '========================================================================================================================'
    puts 'report url: ' + @reportiumClient.getReportUrl
    puts '========================================================================================================================'

    @driver.close
    @driver.quit
  end

end
