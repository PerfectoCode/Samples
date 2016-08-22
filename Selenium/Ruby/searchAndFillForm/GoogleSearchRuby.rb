require 'selenium-webdriver'
require 'uri'

#Test class.
class GoogleSearchRuby
  
  host          = 'My_Host.perfectomobile.com'
  user          = 'My_User'
  pass          = 'My_Password'
  
  #Provide your device capabilities here.
  @@capabilities  = {
    :user             => user,
    :password         => pass,
    :platformName     => 'Windows',
    :platformVersion  => '8.1',
    :browserName      => 'Chrome',
    :browserVersion   => '49'
  }
  
  @@url           = "http://" + host + "/nexperience/perfectomobile/wd/hub"
  
  #### test method ####
  def test()  
    begin
      #Creating a driver.
      driver = Selenium::WebDriver.for(:remote, :url => @@url, :desired_capabilities => @@capabilities)
      driver.manage.timeouts.implicit_wait = 25
      driver.manage.window.maximize
      
      #Search in google.
      driver.get 'https://google.com'
      driver.find_element(:name , 'q').send_keys('perfecto mobile')
      driver.find_element(:xpath , "//*[@id='rso']/div[1]/div[1]/div/h3/a").click()

      #Fill a form in perfecto site.
      driver.find_element(:xpath , "//*[text() = 'Start Free']").click();
      driver.find_element(:id , "FirstName").send_keys("MyFirstName");
      driver.find_element(:id , "LastName").send_keys("MyLaseName");
      driver.find_element(:id , "Company").send_keys("MyCompany");
      driver.find_element(:id , "Mobile_Testing_Role__c").click();
      driver.find_element(:xpath , "//*[@value = 'Development']").click();
      driver.find_element(:id , "Email").send_keys("MyEmail@Somehost.com");
      driver.find_element(:id , "Phone").send_keys("123456789");
      driver.find_element(:id , "Country").click();
      driver.find_element(:xpath , "//*[@value = 'Israel']").click();
      
    rescue=>e 
      puts e
    ensure
      driver.quit
    end  #end begin
  end  #end def
  
end #end class.


#Building a test class (GoogleSearchRuby) instance and starting the test.
GoogleSearchRuby.new.test()

