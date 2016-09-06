require_relative 'PerfectoTest.rb'

# Test class
#
# Add here more tests here.
# configuration for the tests can be found in PerfectoTest class.
class MyTests < PerfectoTest

  def test_navigation_to_perfecto_code_should_fail
    begin
      @reportiumClient.testStep('Step1: navigate to google')
      @driver.get 'https://google.com'

      @reportiumClient.testStep('Step2: Searching for PerfectoCode')
      @driver.find_element(:name => 'q').send_keys('PerfectoCode GitHub')

      #click search button
      @driver.find_element(:id => 'tsbb').click

      #click the first search result
      @driver.find_element(:css => '#rso > div.g.kno-result._rk.mnr-c.g-blk > div > div > div._OKe > div:nth-child(2) > div > div > div > div.rc > div._OXf > h3 > a').click

      @reportiumClient.testStep('Step3: Asserting page title contains keyword')
      assert(@driver.title.include? 'Perfecto')

    # Logging the exception into the reporting client
    rescue Exception => exception
      @exception = exception
      raise exception
    end
  end

end