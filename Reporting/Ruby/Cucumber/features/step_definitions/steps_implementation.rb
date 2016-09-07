require_relative '../lib/utils/perfecto-utils'
require_relative '../lib/geico_pages'

When /^I navigate to Geico home page$/ do
  Utils::Reporting.logStep
  @homePage = Geico::HomePage.new Utils::Device.driver
end

Then /^I choose (.*) insurance$/ do |insurance_type|
  Utils::Reporting.logStep
  @homePage.insurance_type insurance_type
end

Then /^Insert my ZIP Code: (\d+)$/ do |zip|
  Utils::Reporting.logStep
  @homePage.insert_zip zip
end

And /^Click start button$/ do
  Utils::Reporting.logStep
  @homePage.submit
end

Given /^I click (.*) on the insurance checkbox$/ do |ans|
  Utils::Reporting.logStep
  @costumerPage = Geico::CostumerPage.new Utils::Device.driver
  @costumerPage.has_insurance ans
end

And /^I submit the Customer Information form$/ do
  Utils::Reporting.logStep
  @costumerPage.insert_costumer_info
end