from Conf import TestConf
import unittest
from selenium.webdriver.common.by import By


class ReportingTests(TestConf):

    def test_navigation(self):
        self.reporting_client.test_step('Step1: Navigate to google')
        self.driver.get('https://google.com')

        self.reporting_client.test_step('Step2: Search PerfectoCode GitHub repo')
        self.driver.find_element(By.NAME, 'q').send_keys('PerfectoCode GitHub')
        self.driver.find_element(By.ID, 'tsbb').click()

        self.reporting_client.test_step('Step3: Navigate to the first search result')
        self.driver.find_element(By.CSS_SELECTOR, '#rso > div > div:nth-child(1) > div > div > div._OXf > h3 > a')

        self.reporting_client.test_step('Step4: Validate title contains the work Perfecto')
        assert 'Perfecto' in self.driver.title

if __name__ == '__main__':
    unittest.main()
