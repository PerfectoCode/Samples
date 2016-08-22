require 'selenium-webdriver'
require 'uri'

host  = 'MY_HOST.perfectomobile.com'
user  = 'MY_USER'
pass  = 'MY_PASS'
url   = "http://" + host + "/nexperience/perfectomobile/wd/hub"
  
capabilities  = {
  :user             => user,
  :password         => pass,
:platformName       => 'Windows',
  :platformVersion  => '8.1',
  :browserName 	    => 'Chrome',
  :browserVersion   => '50',
}

driver = Selenium::WebDriver.for(:remote, :url => url, :desired_capabilities => capabilities)
driver.manage.timeouts.implicit_wait = 25

#Begin test
begin
  puts "Run started"
  
  #Navigation
  driver.execute_script('window.location.href = "https://www.amazon.com/";')

  #Fill form using execute script method - Vanilla JS
  driver.execute_script('document.getElementById("nav-link-yourAccount").click();')
  driver.execute_script('document.getElementById("createAccountSubmit").click();')
  driver.execute_script('document.getElementById("ap_customer_name").value = "Testing name";')
  driver.execute_script('document.getElementById("ap_email").value = "Testing_mail@mailservice.com";')
  driver.execute_script('document.getElementById("ap_password").value = "passTest123";')
  driver.execute_script('document.getElementById("ap_password_check").value = "passTest123";')
  driver.execute_script('document.getElementById("continue").click()')
  
rescue Exception => e
  puts e
ensure 
  puts "Run ended"
  driver.close
  driver.quit
end

