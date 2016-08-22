from appium import webdriver

import TestParameters
from AppObjects import Locators
from DesktopTest import desktoptest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

"""
This is an implementation of cross driver test on amazon mobile app and website.
Using Appium on mobile application and Selenium on the website session.
In both cases (App and website) all the Locators been tested on the UK version.
The test move between Amazon Application (Adding to basket an item scenario) and then fill information on Amazon's UK website.
All the test locators can be found at AppObjects.py.
Personal information supplied at TestParameters.py.
"""
class MobileTest():
    
    def __init__(self):
        self.driver = self.create_AppiumDriver()
        self.driver.implicitly_wait(15)
        self.driver.switch_to.context("NATIVE_APP")
        self.wait = WebDriverWait(self.driver , 10)
        
    def create_AppiumDriver(self):
        cloud = 'https://' + TestParameters.host + '/nexperience/perfectomobile/wd/hub'
        return webdriver.Remote(cloud ,TestParameters.iPhone_6)
    
    """
    Test session , here comes all the methods of the test by order.
    """
    def TEST(self):
        try:
            self.openApp()
            self.LoginApp()
            self.search()
            self.LogOutAPP()
            self.closeApp() 
            
        except Exception as e:
            print e
            
        finally:
            self.EndTest()
        
        #From that point switching to Selenium driver and starting web test.
        try:
            desktop_test = desktoptest()
            desktop_test.start_and_login()
            desktop_test.go_cart_checkout()
            desktop_test.clear_basket()
            desktop_test.end_test()
        except Exception as e:
            print e
    
    """       
    Test Methods 
    """
    def openApp(self):
        script = {"name" : "Amazon"}
        self.driver.execute_script('mobile:application:open' , script)
        
    def closeApp(self):
        script = {"name" : "Amazon"}
        self.driver.execute_script('mobile:application:close' , script)
        
    def EndTest(self):
        try:
            self.driver.close()
            self.driver.quit()
        except Exception as e:
            print e
    
    
    def LoginApp(self):
        try:
            self.wait.until(EC.visibility_of_element_located((By.XPATH , Locators.Sign_in))).click()
            self.driver.find_element_by_xpath(Locators.userName).send_keys(TestParameters.AppUser)
            self.driver.find_element_by_xpath(Locators.password).send_keys(TestParameters.AppPass)
            self.driver.find_element_by_xpath(Locators.login_btn_ios).click()
        except Exception as e:
            print e    
    
    def LogOutAPP(self):
        try:
            self.driver.find_element_by_xpath(Locators.AppMenu).click()
            self.driver.find_element_by_xpath("//*[@name = 'Not "+TestParameters.YourName+"? Sign out' or @text= 'Not" + TestParameters.YourName +"? Sign out']").click()
            self.driver.find_element_by_xpath(Locators.Sign_out_popup_ios).click()
        except Exception as e:
            print e
    
    def search(self):
        try: 
            try:
                self.wait.until(EC.visibility_of_element_located((By.XPATH , Locators.search_box))).send_keys(TestParameters.searchValue)
            except:
                self.wait.until(EC.visibility_of_element_located((By.XPATH , Locators.search_box))).send_keys(TestParameters.searchValue)
               
            self.driver.find_element_by_xpath(Locators.search_result).click()
            self.driver.find_element_by_xpath(Locators.darkSideAlbum).click()
            #In order to perform a scroll choose two elements.
            #Then driver.scroll method scrolling from the first element to the second element. 
            elem = self.driver.find_element_by_xpath("//*[@label = 'Details']") 
            elem2 = self.driver.find_element_by_xpath("//*[@label = 'Original recording remastered, Original recording reissued']")
            self.driver.scroll(elem, elem2)
            self.driver.find_element_by_xpath(Locators.AddtoBasket).click()
            
        except Exception as e:
            print e
    

""" Main method """
if __name__ == '__main__':
    MobileTest().TEST()

