import time

from selenium.webdriver import ActionChains
from selenium.webdriver.remote.command import Command
from selenium.webdriver.remote.mobile import Mobile

from WindTunnelUtils import WindTunnelUtils
from PerfectoLabUtils import PerfectoLabUtils
from selenium import webdriver
from selenium.webdriver.common.by import By

class RemoteWebDriverTest:

    def __init__(self):
        pass

    @staticmethod
    def main():
        print('Run started')
        
		""" Provide over here your cloud information : Host , user and password """
        host        = "My_Host.perfectomobile.com" 
        user        = "My_User" 
        password    = "My_Password" 
        
		#Capabilities for example , Provide your device capabilities here.
        capabilities = {
            'user'              : user,
            'password'          : password,
            'platformName'      : 'Windows',
            'platformVersion'   : '8.1',
            'browserName'       : 'Chrome',
            'browserVersion'    : '49'
        }


        driver = webdriver.Remote("https://" + host + "/nexperience/perfectomobile/wd/hub", capabilities)
        driver.implicitly_wait(15)
        driver.maximize_window()

		""" Test """
        try:
            print('Test started')
			
            #Search in google.
            driver.get('https://google.com')
            driver.find_element(By.NAME, 'q').send_keys('perfecto mobile')
            driver.find_element(By.XPATH, "//*[@id='rso']/div[1]/div[1]/div/h3/a").click() # Clicks the first search result.
            
            #Fill a form in perfecto site.
            driver.find_element(By.XPATH, "//*[text() = 'Start Free']").click()
            driver.find_element(By.ID , "FirstName").send_keys('MyFirstName')
            driver.find_element(By.ID , "LastName").send_keys('MyLastName')
            driver.find_element(By.ID , "Company").send_keys('MyCompany');
            driver.find_element(By.ID , "Mobile_Testing_Role__c").click()
            driver.find_element(By.XPATH , "//*[@value = 'Development']").click()
            driver.find_element(By.ID , "Email").send_keys("MyEmail@Somehost.com")
            driver.find_element(By.ID , "Phone").send_keys("123456789")
            driver.find_element(By.ID , "Country").click()
            driver.find_element(By.XPATH , "//*[@value = 'Israel']").click()

        except Exception as e:
            print(e)
            
        finally:
            try:
                # disconnect from the Remote server
                if driver is not None:
                    # Retrieve the URL of the Wind Tunnel Report, can be saved to your execution summary and used to download the report at a later point
                    report_url = driver.capabilities[WindTunnelUtils.SINGLE_TEST_REPORT_URL_CAPABILITY]

                    driver.close()

                    # In case you want to download the report or the report attachments, do it here.
                    # PerfectoLabUtils.download_report(driver, 'pdf', '/test/report')
                    # PerfectoLabUtils.download_attachment(driver, 'video', '/test/video', 'flv')
                    # PerfectoLabUtils.download_attachment(driver, 'image', '/test/image', 'jpg')

            except Exception as e:
                print(e)

            driver.quit()

        print('Run ended')

    @staticmethod
    def switch_to_context(driver, context):
        driver.execute(Command.SWITCH_TO_CONTEXT, {"name": context})

    @staticmethod
    def get_current_context_handle(driver):
        mobile = Mobile(driver)
        return mobile.context

    @staticmethod
    def get_context_handles(driver):
        mobile = Mobile(driver)
        return mobile.contexts

if __name__ == "__main__":
    RemoteWebDriverTest().main()
