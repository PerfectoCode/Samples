# Airbnb cross platform sample.

This test performs double step authentication, where a mobile number is entered into an Airbnb account, 
the Web app sends an SMS to the mobile, and the code in the SMS is entered into the Web app to complete authentication.

Two drivers are created in a single test - RemoteWebDriver and IOSDriver, the later using NATIVE_APP context. 

**To do before running this test**:
- Define your lab, username, and password.
- Determine phone number of the mobile to use for this test. Insert mobile ID and number in script right before mobileDrive creation.
- Create an account in Airbnb with your email and a password, and enter these in the script at point of login to Airbnb.
