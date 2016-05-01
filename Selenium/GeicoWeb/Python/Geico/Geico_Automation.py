from selenium import webdriver
from page import MainPage
from page import CustomerInformationPage
from PerfectoLabUtils import PerfectoLabUtils
from capabilities_hash import devices
import capabilities_hash
import threading
import sys

class test():
    
    """ Constructor creating devices and load host for each one"""
    def __init__(self , mcm):
        host = mcm #+ '.perfectomobile.com'
        self.drivers = {}
        for testname , device in devices.iteritems(): 
            try:          
                driver = webdriver.Remote('https://' + mcm + '/nexperience/perfectomobile/wd/hub' , device[1])
                driver.implicitly_wait(25)
                driver.get('http://www.geico.com')
                if device[0] == 'Windows':
                    driver.maximize_window() 
                self.drivers[driver] = [device[0] , testname]
            except Exception as e:  
                print e
            
    """ The test itself , Geico scenario """
    def do_test(self , platform , driver):
        try:
            print 'Started test on: ' + platform + '.'
            #working on the main page.
            main_page = MainPage(driver)
            #assert main_page.is_title_match() , "Main page's title not match! "
            main_page.choose_insurance()
            main_page.set_zip("1234")
            main_page.submit()
            
            #Customer information page.
            custumer_info_page = CustomerInformationPage(driver)
            custumer_info_page.set_FirstName("Daniel")
            custumer_info_page.set_LastName("Alfasi")
            custumer_info_page.set_StreetAddress("Amal13")
            custumer_info_page.set_APT("1234")
            custumer_info_page.set_ZIP("50840")
            custumer_info_page.set_DateOfBirth('8','23','1992')
            custumer_info_page.set_HaveAutoInsurance('Yes')
            custumer_info_page.set_CurrentlyHaveMutorInsurence('No')
            custumer_info_page.submit()
        except Exception as e:
            print e

    """End test  , Here you could download the test report """
    def end_test(self):
        for driver , device in self.drivers.iteritems():
            driver.close()
            #in order to download the report enable this code.
            #params = {}
            #driver.execute_script("mobile:execution:close", params)
            #set the report's file name here.
            #file_name = 'Report'
            #format = 'pdf' #report format
            #PerfectoLabUtils.download_report(driver , format , file_name)
        for driver , device in self.drivers.iteritems():
            driver.quit()

    """ Starting the test by loading the test method for each device"""
    def start_test(self):
        workers = []
        for driver , platform in self.drivers.iteritems():
            worker = threading.Thread(target = self.do_test , args=(platform[0] , driver))
            worker.start()
            workers.append(worker)
    
        for worker in workers:
            worker.join()


if __name__ == '__main__':
    host = 'demo.perfectomobile.com'
    if len(sys.argv) > 1:
        #host = sys.argv.pop() 
        capabilities_hash.user = sys.argv[0]
        print capabilities_hash.user
        capabilities_hash.password = sys.argv[1]
        print capabilities_hash.password
        #SampleCode.platform_name = sys.argv.pop()
        #SampleCode.description = sys.argv.pop()

    t = test(host)
    t.start_test()
    t.end_test()
