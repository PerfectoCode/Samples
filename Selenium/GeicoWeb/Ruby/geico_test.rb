require 'selenium-webdriver'
require 'uri'
require 'Locators'

class Geico_test
  include Locators
  
  host          = ARGV[0] #'My-Host.perfectomobile.com'
  user          = ARGV[1] #'My_User'
  pass          = ARGV[2] #'My_Password'
  url           = "http://" + host + "/nexperience/perfectomobile/wd/hub"
  
  capabilities  = {
    :deviceName => '123456',
    :user       => user,
    :password   => pass  
	#:CapabilitieExample => SomeValue,
  }
  
  driver = Selenium::WebDriver.for(:remote, :url => url, :desired_capabilities => capabilities)
  driver.manage.timeouts.implicit_wait = 25
  driver.get "www.geico.com"
  
  ####test#### PageObject Model
  begin
    #Main Page
    main = MainPage.new(driver)
    main.set_insurance_type
    main.set_zip("1234")
    main.submit
    
    #Costumer Information Page
    costumer_info_page = CostumerInformationPage.new(driver)
    costumer_info_page.set_FirstName("MyName")
    costumer_info_page.set_LastName("MyLastName")
    costumer_info_page.set_StreetAdress("MyStreetAddress")
    costumer_info_page.set_APT("1234")
    costumer_info_page.set_ZIP("50840")
    costumer_info_page.set_BirthDay("11" , "1" , "1111")
    costumer_info_page.set_geico_auto_insurance_no
    costumer_info_page.set_motorcycle_insurance_no
    costumer_info_page.submit
  end
  ensure 
    driver.quit
  
end
