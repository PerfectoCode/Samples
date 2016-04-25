from selenium.webdriver.common.by import By

"""Main page locators. """
class MainPageLocators(object):
    insurance_type  = (By.ID , 'optionMotorcycle') 
    submit          = (By.ID , 'submitButton')
    zip             = (By.ID , 'zip')

"""Custuemer page locators """
class CustomerInformationPageLocators(object):
    first_name                      = (By.ID , 'firstName')
    last_name                       = (By.ID , 'lastName')
    street_address                  = (By.ID , 'street')
    apt                             = (By.ID , 'apt')
    zip                             = (By.ID , 'zip')
    birth_month                     = (By.ID , 'date-monthdob')
    birth_day                       = (By.ID , 'date-daydob')
    birth_year                      = (By.ID , 'date-yeardob')
    have_auto_insurance_no          = (By.XPATH , "//*[@class ='radio'][2]")
    have_auto_insurance_yes         = (By.XPATH , "//*[@class ='radio'][1]")
    have_motorcycle_insurance       = (By.ID , 'hasCycle')
    have_motorcycle_insurance_yes   = (By.XPATH , "//*[@id = 'hasCycle']/option[2]")
    have_motorcycle_insurance_no    = (By.XPATH , "//*[@id = 'hasCycle']/option[3]")
    next_button                     = (By.ID , 'btnSubmit')
