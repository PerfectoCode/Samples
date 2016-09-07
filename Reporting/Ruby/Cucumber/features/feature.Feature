#encoding: utf-8
Feature: Testing Geico website using Ruby and Cucumber testing framework.
  Each test step should be logged with Perfecto Reporting client.
 
  Scenario: Getting insurance from Geico
    When I navigate to Geico home page
    Then I choose Motorcycle & ATV insurance
    Then Insert my ZIP Code: 50145
    And Click start button
    Given I click No on the insurance checkbox
    And I submit the Customer Information form
