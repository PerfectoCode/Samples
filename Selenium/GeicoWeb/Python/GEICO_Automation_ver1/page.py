#from element import BaseWebElement
from locators import MainPageLocators
from locators import CustomerInformationPageLocators
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class BasePage(object):
    """Basic page template """
    def __init__(self, driver):
        self.driver = driver

"""Main page object """
class MainPage(BasePage):

    def is_title_match(self):
        """Verifies the page title"""
        title = 'Save on more than just car insurance'
        return title in self.driver.title

    def choose_insurance(self):
        self.driver.find_element(*MainPageLocators.insurance_type).click()

    def set_zip(self , given_zip):
        
        self.driver.find_element(*MainPageLocators.zip).send_keys(given_zip)

    def submit(self):
        self.driver.find_element(*MainPageLocators.submit).click()

"""CustomerPage object """
class CustomerInformationPage(BasePage):

    def set_FirstName(self , firstname):
        self.driver.find_element(*CustomerInformationPageLocators.first_name).send_keys(firstname)

    def set_LastName(self , lastname):
        self.driver.find_element(*CustomerInformationPageLocators.last_name).send_keys(lastname)

    def set_StreetAddress(self , streetaddress):
        self.driver.find_element(*CustomerInformationPageLocators.street_address).send_keys(streetaddress)

    def set_APT(self , apt):
        self.driver.find_element(*CustomerInformationPageLocators.apt).send_keys(apt)

    def set_ZIP(self , zip):
        elem = self.driver.find_element(*CustomerInformationPageLocators.zip)
        elem.clear()
        elem.send_keys(zip)

    def set_DateOfBirth(self , month , day , year):
        self.driver.find_element(*CustomerInformationPageLocators.birth_day).send_keys(day)
        self.driver.find_element(*CustomerInformationPageLocators.birth_month).send_keys(month)
        self.driver.find_element(*CustomerInformationPageLocators.birth_year).send_keys(year)
    
    def set_HaveAutoInsurance(self , yes_no):
        if yes_no == 'No':
            self.driver.find_element(*CustomerInformationPageLocators.have_auto_insurance_no).click()
        else :
            self.driver.find_element(*CustomerInformationPageLocators.have_auto_insurance_yes).click()

    def set_CurrentlyHaveMutorInsurence(self , Yes_No):
            elem = self.driver.find_element(*CustomerInformationPageLocators.have_motorcycle_insurance)
            elem.click()
            if Yes_No == 'No':
                self.driver.find_element(*CustomerInformationPageLocators.have_motorcycle_insurance_no).click()
            else:
                self.driver.find_element(*CustomerInformationPageLocators.have_motorcycle_insurance_no).click()

    def submit(self):
            self.driver.find_element(*CustomerInformationPageLocators.next_button).click()          
 