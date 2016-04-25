require 'selenium-webdriver'
require 'uri'

module Locators
  
  #Main Page Elements Handeling.
  class MainPage
    
    $insurance_type  = {id: "insurancetype"}
    $motorcycle_type = {id: "optionMotorcycle"}
    $zip             = {id: "zip"}
    $submit          = {id: "submitButton"}
    
    attr_reader :driver
    #constructor
    def initialize(driver)
      @driver = driver
    end
    
    #set insurance type
    def set_insurance_type
      driver.find_element($insurance_type).click
      driver.find_element($motorcycle_type).click
    end
    
    #set zip
    def set_zip(zip)
      driver.find_element($zip).send_keys(zip)
    end
    
    #submit
    def submit
      driver.find_element($submit).click
    end
      
  end
  
  class CostumerInformationPage
  
    $first_name               = {id: "firstName"}
    $last_name                = {id: "lastName"}
    $street_address           = {id: "street"}
    $apt                      = {id: "apt"}
    $zip                      = {id: "zip"}
    $birth_day                = {id: "date-daydob"}
    $birth_month              = {id: "date-monthdob"}
    $birth_year               = {id: "date-yeardob"}
    $geico_auto_insurance_yes = {xpath: "//*[@class = 'radio'][1]"}
    $geico_auto_insurance_no  = {xpath: "//*[@class = 'radio'][2]"}
    $motorcycle_insurance_yes = {xpath: "//*[@id = 'hasCycle']/option[2]"}
    $motorcycle_insurance_no  = {xpath: "//*[@id = 'hasCycle']/option[3]"}
    $submit_2                   = {id: "btnSubmit"}
  
    attr_reader :driver
    #constructor
    def initialize(driver)
      @driver = driver
    end

    def set_FirstName(first_name)
      driver.find_element($first_name).send_keys(first_name)
    end
    
    def set_LastName(last_name)
      driver.find_element($last_name).send_keys(last_name)
    end
    
    def set_StreetAdress(street_address)
      driver.find_element($street_address).send_keys(street_address)
    end
    
    def set_APT(apt)
      driver.find_element($apt).send_keys(apt)
    end
    
    def set_ZIP(zip)
      elem = driver.find_element($zip) 
      elem.clear
      elem.send_keys(zip)
    end
    
    def set_BirthDay(day , month , year)
      driver.find_element($birth_day).send_keys(day)
      driver.find_element($birth_month).send_keys(month)
      driver.find_element($birth_year).send_keys(year)
    end
    
    def set_geico_auto_insurance_no
      driver.find_element($geico_auto_insurance_no).click
    end
    
    def set_motorcycle_insurance_no
      driver.find_element($motorcycle_insurance_no).click
    end

    def submit
      driver.find_element($submit_2).click
    end
  end
  
end
