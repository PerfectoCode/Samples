require_relative './abstract_page'

module Geico

  class HomePage < Page

    @@Url = 'https://www.geico.com'

    def initialize driver
      super driver
      @driver.get @@Url
    end

    def insurance_type insurance_type
      @driver.find_element(:xpath => '//*[contains(text() , \'' + insurance_type + '\')]').click
    end

    def insert_zip zip
      @driver.find_element(:id => 'zip').send_keys(zip)
    end

    def submit
      @driver.find_element(:id => 'submitButton').click
    end

  end

  class CostumerPage < Page

    def has_insurance ans
      @driver.find_element(:xpath => '(//*[text() = \'' + ans + '\'])[1]').click
    end

    def insert_costumer_info
      @driver.find_element(:id => 'firstName').send_keys 'Test Name'
      @driver.find_element(:id => 'lastName').send_keys 'Test LastName'
      @driver.find_element(:id => 'street').send_keys 'SomeStreet'
      @driver.find_element(:id => 'apt').send_keys '12345'
      @driver.find_element(:id => 'date-monthdob').send_keys '11'
      @driver.find_element(:id => 'date-daydob').send_keys '11'
      @driver.find_element(:id => 'date-yeardob').send_keys '1992'
      @driver.find_element(:id => 'hasCycle').click
      @driver.find_element(:xpath => '//*[@id = \'hasCycle\']/option[@value = \'N\']').click
    end

  end

end