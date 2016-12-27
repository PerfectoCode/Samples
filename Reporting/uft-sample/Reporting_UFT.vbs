Public Function TestLogin()
    ' Add Test start to reporting
    PerfectoLab("lab_id:=lab").TestStart "name=PerfectoCommunityAppLogIn","tags=AndroidNativeAppTests","jobName=Job1","jobNumber=11","projectName=Sample Reportium project","projectVersion=1.0"

    ' Open device
    PerfectoLab("lab_id:=lab").TestStep "name=step1: Open device" ' Add Test Step to Reporting
    PerfectoLab("lab_id:=lab").SelectDevice("os=Android")
    Device("DUT").SetTOProperty "device_id","03157DF3B81ABD26"
    Device("DUT").Open

    ' Install and start application
    PerfectoLab("lab_id:=lab").TestStep "name=step2: Install application" ' Add Test Step to Reporting
    Device("DUT").Applications.Install "PUBLIC:Android/android.perfecto.apk"
    Device("DUT").Applications.Start "name=Perfecto Mobile"

    ' Validate login page
    PerfectoLab("lab_id:=lab").TestStep "name=step3: Validate login page" ' Add Test Step to Reporting
    Device("DUT").MNativeElement("identifier:=//*[@resourceid='com.bloomfire.android.perfecto:id/sso1']").GetROProperty "text"

    ' Login to app
    PerfectoLab("lab_id:=lab").TestStep "name=step4: Login to app" ' Add Test Step to Reporting
    Device("DUT").MNativeElement("identifier:=//*[@resourceid='com.bloomfire.android.perfecto:id/email_address']").Set "MyCommunityUser"
    Device("DUT").MNativeElement("identifier:=//*[@resourceid='com.bloomfire.android.perfecto:id/password']").Set  "MyCommunityPassword"
    Device("DUT").MNativeElement("identifier:=//*[@resourceid='com.bloomfire.android.perfecto:id/item_button_sign_in_done']").Click

    ''' Other test commands here...

    ' Close device
    Device("DUT").Close

    ' Add Test end with status 'success' to reporting
    PerfectoLab("lab_id:=lab").TestEnd "success=true"
End Function

On Error Resume Next
TestLogin
If Err.Number <> 0 Then
    ' Add Test end with status 'failure' to reporting
    PerfectoLab("lab_id:=lab").TestEnd "success=false","failureDescription=" + Err.Description
End If

On Error GOTO 0
