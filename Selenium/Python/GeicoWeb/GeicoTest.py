from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from sys import argv

class GeicoTest:
    
    def __init__(self):
        self.capabilities = {
                            'user' : argv[1], 
                            'password' : argv[2],
                            'browserName' : 'mobileOS' 
                             }
        
        self.driver = webdriver.Remote('https://' + argv[3] + '/nexperience/perfectomobile/wd/hub' , self.capabilities)
        #self.driver = webdriver.Chrome()
        self.driver.implicitly_wait(20)
        
    def test(self):
        #Navigate Geico
        self.driver.get('https://Geico.com')
        
        #Choose insurance type and ZIP 
        selection = Select(self.driver.find_element(By.ID, 'insurancetype'))
        selection.select_by_value('motorcycle')
        self.driver.find_element(By.ID, 'zip').send_keys('50840')
        self.driver.find_element(By.ID, 'submitButton').click()
        
        #Fill form 
        self.driver.find_element(By.XPATH, '//*[@class ="radio"][2]').click()
        self.driver.find_element(By.ID, "firstName").send_keys('FirstName')
        self.driver.find_element(By.ID, "lastName").send_keys('LastName')
        self.driver.find_element(By.ID, "street").send_keys('Street Address')
        self.driver.find_element(By.ID, "apt").send_keys('1234')
        self.driver.find_element(By.ID, "zip").clear()
        self.driver.find_element(By.ID, "zip").send_keys('50840')
        self.driver.find_element(By.ID, "date-monthdob").send_keys('01')
        self.driver.find_element(By.ID, "date-daydob").send_keys('25')
        self.driver.find_element(By.ID, "date-yeardob").send_keys('1999')
        selection = Select(self.driver.find_element(By.ID, "hasCycle"))
        selection.select_by_value('N')
        
        #Submit 
        self.driver.find_element(By.ID , 'btnSubmit').submit()
        
        self.driver.close()
        self.driver.quit()
        
if __name__ == '__main__':
    GeicoTest().test()