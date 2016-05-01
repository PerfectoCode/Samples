"""
Set here your desired capabilities.
Import this module to the test class in order to import this capabilities.
"""

user     = 'My_User' #your perfecto user.
password = 'My_Pass' #your perfecto password.

#########################################################################
#Down here copy capabilities for the device and update the devices dict.#
#########################################################################

#This devices for example , you may change it by your choice.



capabilities_ios = {
    'user'              : user,
    'password'          : password,
    'platformName': 'iOS',
    'model': 'iPhone-6',
    }

capabilities_windows7_Chrome49 = {
    'user'              : user,
    'password'          : password,
    'platformName'      : 'Windows',
    'platformVersion'   : '7',
    'browserName'       : 'Chrome',
    'browserVersion'    : '49',
    'resolution'        : '1366x768',
    }

capabilities_windows7_Firefox45 = {
    'user'              : user,
    'password'          : password,
    'platformName': 'Windows',
    'platformVersion': '7',
    'browserName': 'Firefox',
    'browserVersion': '45',
    'resolution': '1366x768',
}


"""
Devices hash.
Key     = 'Name of test case'.
Value   = List [ PlatformName, device capabilities].
"""
devices = {'GalaxyS6-test':         ['Mobile' , capabilities_ios]
          }
