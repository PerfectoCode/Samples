Device("DUT").SetTOProperty "device_id","VS986B75A2FF5"
Device("DUT").Open

'*************************************************************
'To use a Persona in your test, use one of the following 3 options
'*************************************************************
'OPTION 1
'Select one of the pre-defined Wind tunnel Personas
'See https://community.perfectomobile.com/posts/1048047 for more info 
'Device("DUT").Setup "persona=Peter"
'
'OPTION 2
'Select one of the pre-defined personas, but modify at runtime one or more of its properties
'Adding parameters to a pre-defined persona overrides the relevant persona detail. In this example, Peter’s location (London) would be overridden by Paris
'Device("DUT").Setup "persona=Peter","address=Paris, France"
'
'OPTION 3
'Create your own persona. Use the below functions:
' This uses the Wind Tunnel UFT function library, download it to use 
'To set location use either the locationCoordiantates or the locationAddress 
'all parameters are optional
'To send just some of the parmaters send empty params instead
'
'personaJson = createWTPersona ("MyPersonaName", "MyPersona Description", "myImage.png","network profile","orientation","locationCoordiantates","locationAddress","background applications")
'PostPersona "MyLabURL", "MyUser", "myPassword", "MyPersonaName", personaJson 
'PostPersonaImage "MyLabURL", "MyUser", "myPassword", "myImage.png", "myImageLocalPath"
'Device("DUT").Setup "repositoryFile=PRIVATE:Personas/MyPersonaName.json"
'
'*************************************************************

'in this sample, we use a modified pre-defined persona - option 2 above
Device("DUT").Setup "persona=Peter","address=Paris, France"



'Create Lab object and set point of interest
PerfectoLab("lab_id:=lab").PointOfInterest "Open Starbucks Application","status=success"

'Lab has 3 commands
'POI - creates poi in the WT report
'Report timer - generates report, needa working sample
'Script info - workaround for 12.5+ - nned sample 

'Start Application
Device("DUT").Applications.Start "name=Starbucks","timeout=0"

'time the duration it takes for the app to open, then validate it against the threshold for successs, see results in the report
Dim uxTimer
Device("DUT").Checkpoints.TextCheckpoint "pay with your phone","source=camera","timeout=20","measurement=accurate","analysis=automatic"
uxTimer= clng(Device("DUT").GetROProperty("last_timer_ux"))

'Report the timer
PerfectoLab("lab_id:=lab").ReportTimer "Launch App",int(uxTimer),"threshold=5000","description=Timer for Starbucks App launch"

'Setting Point of Interest
PerfectoLab("lab_id:=lab").PointOfInterest "Closing Starbucks Application"

Device("DUT").Applications.Close "name=Starbucks"


Device("DUT").Close

