import TestParameters
from selenium import webdriver
from selenium.webdriver.common.by import By
from AppObjects import Locators
from selenium.webdriver.support.select import Select

class desktoptest():
    
    def __init__(self):
        self.driver = self.create_SeleniumDriver()
        self.driver.implicitly_wait(15)
        self.driver.maximize_window()
        
    def create_SeleniumDriver(self):
        cloud = 'https://' + TestParameters.host + '/nexperience/perfectomobile/wd/hub'
        return webdriver.Remote(cloud , TestParameters.desktop_chome)
    
    def start_and_login(self):
        try:
            self.driver.get("http://www.amazon.co.uk")
            self.driver.find_element(By.ID, Locators.desktop_login_id).click()
            self.driver.find_element(By.ID, Locators.desktop_username_id).send_keys(TestParameters.AppUser)
            self.driver.find_element(By.ID, Locators.desktop_password_id).send_keys(TestParameters.AppPass)
            self.driver.find_element(By.ID, Locators.desktop_loginBTN_id).click()
        except Exception as e:
            print e
            
    def go_cart_checkout(self):
        try:
            self.driver.find_element(By.ID, Locators.desktop_cart_id).click()
            self.driver.find_element(By.XPATH, Locators.desktop_checkout_xpath).click()
            self.driver.find_element(By.ID, Locators.desktop_full_name_id).send_keys("Daniel")
            self.driver.find_element(By.ID, Locators.desktop_address_line1_id).send_keys("Amal 13")
            self.driver.find_element(By.ID, Locators.dekstop_city_id).send_keys("Rosh Haayin")
            self.driver.find_element(By.ID, Locators.desktop_country1_id).send_keys("Israel")
            self.driver.find_element(By.ID, Locators.desktop_post_code_id).send_keys("48100")
            Select(self.driver.find_element(By.ID, Locators.desktop_CountrySelect_id)).select_by_visible_text("Israel")
            self.driver.find_element(By.ID, Locators.desktop_phone_id).send_keys("972123456789")
            Select(self.driver.find_element(By.ID, Locators.desktop_address_type_id)).select_by_value("COM")
            self.driver.find_element(By.XPATH, Locators.desktop_Continue_xpath).click()
            
        except Exception as e:       
            print e
            
    def clear_basket(self):
            self.driver.get("http://www.amazon.co.uk")
            self.driver.find_element(By.ID, Locators.desktop_cart_id).click()
            self.driver.find_element(By.XPATH , Locators.desktop_deleteItem_xpath).click()
            
    def end_test(self):
        try:
            self.driver.close()
            self.driver.quit()
        except Exception as e:
            print e
