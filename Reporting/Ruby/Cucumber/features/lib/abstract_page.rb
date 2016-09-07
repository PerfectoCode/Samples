
# Abstract page
#
# Use as template for page object module
class Page

  attr_accessor :driver

  # create a new page
  #
  # driver - driver to test on
  def initialize driver
    @driver = driver
  end

end