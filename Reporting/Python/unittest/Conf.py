import unittest
from selenium import webdriver
from perfecto import PerfectoExecutionContext,TestResultFactory,TestContext,PerfectoReportiumClient


class TestConf(unittest.TestCase):
    def __init__(self, *args, **kwargs):
        self.user = 'My_User'
        self.password = 'My_Pass'
        self.host = 'My_Host.perfectomobile.com'
        self.driver = None
        self.reporting_client = None

        super(TestConf, self).__init__(*args, **kwargs)

    def setUp(self):
        capabilities = {
            'platformName': 'Android',
            'deviceName': '',
            'user': self.user,
            'password': self.password
        }
        self.driver = webdriver.Remote('https://' + self.host + '/nexperience/perfectomobile/wd/hub', capabilities)
        self.create_reporting_client()
        self.reporting_client.test_start(self.id(),
                                         TestContext('Tag1', 'Tag2', 'Tag3'))

    def run(self, result=None):
        self.currentResult = result  # remember result for use in tearDown
        unittest.TestCase.run(self, result)  # call superclass run method

    def tearDown(self):
        try:
            if self.currentResult.wasSuccessful():
                self.reporting_client.test_stop(TestResultFactory.create_success())
            else:
                self.reporting_client.test_stop(TestResultFactory.create_failure(self.currentResult.errors,
                                                                                 self.currentResult.failures))
            # Print report's url
            print 'Report-Url: ' + self.reporting_client.report_url() + '\n'
        except Exception as e:
            print e.message

        self.driver.quit()

    def create_reporting_client(self):
        perfecto_execution_context = PerfectoExecutionContext(self.driver)
        self.reporting_client = PerfectoReportiumClient(perfecto_execution_context)
