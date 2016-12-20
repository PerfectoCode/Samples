## Ruby Test::Unit

This project demonstrates adding Reporting calls to Ruby Test::Unit testing framework.

:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/C%23-Implementation) for the complite SDK documentation.<br/>
:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/Reporting) to get started with Perfecto Reporting. 

### Getting started

Install Perfecto-Reporting gem using gem install command:<br/>
`gem install perfecto-reporting`

### Setting up the test

The project contains two files:<br/>
[PerfectoTest.rb](PerfectoTest.rb) - contains test configuration,driver and reporting client setup. <br/>
[test.rb](test.rb) - contains the test methods.

- Set your Perfecto lab User, Password and Host.
- Set your device DesiredCapabilities.

```Ruby
def initialize(name = nil)
  super(name) unless name.nil?

  host = 'My_Host.perfectomobile.com'
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
```

### Setting reporting client

*create_reportium_client* method in [PerfectoTest.rb](PerfectoTest.rb) file initializing a new reporting client instance. <br/>
Note! .withWebDriver must be called with WebDriver instance in order to initialize reporting client. <br/>
Adding Project, Job and ContextTags are optional.
```Ruby
def create_reportium_client
  perfectoExecutionContext = PerfectoExecutionContext.new(
      PerfectoExecutionContext::PerfectoExecutionContextBuilder
          .withProject(Project.new('Project name', '1')) # Optional
          .withJob(Job.new('Job name', 1)) # Optional
          .withContextTags('Test tag1', 'Test tag2', 'Test tag3') # Optional
          .withWebDriver(@driver)
          .build)

  @reportiumClient = PerfectoReportiumClient.new(perfectoExecutionContext)
end
```

*setup* method runs after each test, starting a new test in reporting system:<br/>
It's optional to add context tags to specific test:
```Ruby
# setup method
#
# setup method runs before each test and indicate a reporting.
# add here additional before test settings.
def setup
  puts 'starting a new test: ' + self.name
  @reportiumClient.testStart(self.name, TestContext.new('Ruby', 'unittest'))
end
``` 

## Running the test
Run the test via ruby ide or using the command: 
`Ruby test.rb`
