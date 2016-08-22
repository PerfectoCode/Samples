from selenium import webdriver
from selenium.webdriver.common.by import By
import unittest

class TestClass(unittest.TestCase):
    
    """ 
    Test setUp
    TODO: Set your Perfecto lab user,password and host  
    """
    def setUp(self):
        print "Run started"
        host = 'MY_HOST.perfectomobile.com'
        user = 'MY_USER'
        password = 'MY_PASS' 
        
        capabilities = {
                        #Capabilities example 
                        'platformName'      : 'Windows',
                        'platformVersion'   : '7',
                        'browserName'       : 'Chrome',
                        'browserVersion'    : '50',
                        'user'              : user,
                        'password'          : password
                        }
        
        self.driver = webdriver.Remote('https://' + host + '/nexperience/perfectomobile/wd/hub' , capabilities)
        self.driver.implicitly_wait(25)
        if(capabilities['platformName'] == 'Windows'):
            self.driver.maximize_window()
    
    """ Test execute_script method """
    def test_case(self):
        print "Starting test"
        
        #Navigation
        self.driver.execute_script('window.location.href = "https://www.amazon.com/";')
        
        #Assert Navigation successed 
        title_should_be = 'Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more'
        assert self.driver.title == title_should_be

        #Fill form using execute script method - Vanilla JS
        self.driver.execute_script('document.getElementById("nav-link-yourAccount").click();')
        self.driver.execute_script('document.getElementById("createAccountSubmit").click();')
        self.driver.execute_script('document.getElementById("ap_customer_name").value = "Testing name";')
        
        #Asserts equals between name value and 'should be' value
        form_val =  self.driver.execute_script('return document.getElementById("ap_customer_name").value;')
        assert form_val == 'Testing name'
        
        self.driver.execute_script('document.getElementById("ap_email").value = "Testing_mail@mailservice.com";')
        self.driver.execute_script('document.getElementById("ap_password").value = "passTest123";')
        self.driver.execute_script('document.getElementById("ap_password_check").value = "passTest123";')
        self.driver.execute_script('document.getElementById("continue").click()')
        
    """ tearDown test end - closing the device """
    def tearDown(self):
        print "Run ended"
        self.driver.close()
        self.driver.quit()    
        
if __name__ == '__main__':
    unittest.main()
        
