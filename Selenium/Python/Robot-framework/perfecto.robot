*** Settings ***
| Library | Selenium2Library

*** Variables ***
| @{_tmp}
| ... | platformName:Android,
| ... | browserName:chrome,
| ... | deviceName:,
| ... | user:UserName,
| ... | password:Password


| ${CAPABILITIES}   | ${EMPTY.join(${_tmp})}
| ${REMOTE_URL}     | 
http://Host.perfectomobile.com/nexperience/perfectomobile/wd/hub
| ${URL}            | https://google.com/
| |
| ${TEXT_SHOULD_CONTAIN_1} | Product Samples

*** Test Cases ***
| Example using robot with Perfecto
| | [Tags]
| | ... | Tag1
| | ... | Tag2
| | ... | Tag3
| | [Setup]
| | ... | Open Browser
| | ... | ${URL}
| | ... | remote_url=${REMOTE_URL}
| | ... | desired_capabilities=${CAPABILITIES}
| |
| | Page should contain element | id=lst-ib
| |
| | Input text | id=lst-ib | PerfectoCode GitHub
| | Click button | id=tsbb
| |
| | Click Element | xpath=(//*[.='PerfectoCode/Samples - GitHub'])[3]
| | Page should contain | ${TEXT_SHOULD_CONTAIN_1}
| |
| | [Teardown] | Close all browsers
