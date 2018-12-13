*** Settings ***
Suite Setup       Open Browser    ${URL}    remote_url=${REMOTE_URL}    desired_capabilities=${CAPABILITIES}
Suite Teardown    Close all browsers
Library           Selenium2Library
Library           sample.py

*** Variables ***
@{_tmp}           platformName:iOS,    browserName:mobileOS,    user:<USER_NAME>,    password:<PASSWD>
${CAPABILITIES}    ${EMPTY.join(${_tmp})}
${REMOTE_URL}     https://<CLOUD_ADDRESS>/nexperience/perfectomobile/wd/hub
${URL}            https://google.com/
${TEXT_SHOULD_CONTAIN_1}    Product Samples
${ELEMENT_LOAD_TIME}    20

*** Test Cases ***
Example using robot with Perfecto
    [Tags]    Tag1    Tag2    Tag3    mytest
    ${tags}=    Create List    test1    test2
    ${reporting_client}=    get_reporting_client
    log    ${reporting_client}
    reportium_start    ${reporting_client}    MobileRobotTestPass    ${tags}
    Page should contain element    class=gLFyf    ${ELEMENT_LOAD_TIME}
    Input text    class=gLFyf    PerfectoCode GitHub
    Click button    class=Tg7LZd
    Click Element    xpath=(//*[contains(text(), 'PerfectoCode')])[1]
    Page should contain    ${TEXT_SHOULD_CONTAIN_1}
    [Teardown]    reportium_stop    ${reporting_client}    ${TEST STATUS}    ${TEST MESSAGE}

Example using robot with Perfecto Fail
    [Tags]    Tag1    Tag2    Tag3    mytest
    ${tags}=    Create List    test1    test2
    ${reporting_client}=    get_reporting_client
    log    ${reporting_client}
    reportium_start    ${reporting_client}    MobileRobotTestFail    ${tags}
    Page should contain element    class=gLFyf    ${ELEMENT_LOAD_TIME}
    Input text    class=gLFyf1    PerfectoCode GitHub
    Click button    class=Tg7LZd
    Click Element    xpath=(//*[contains(text(), 'PerfectoCode')])[1]
    Page should contain    ${TEXT_SHOULD_CONTAIN_1}
    [Teardown]    reportium_stop    ${reporting_client}    ${TEST STATUS}    ${TEST MESSAGE}

test
    Sample
