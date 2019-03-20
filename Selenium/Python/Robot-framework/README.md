## Robot Framework 

The following sample demonstrate using [Robot](http://robotframework.org/) framework for executing test on Perfecto Lab. <br/>

### Getting Started: 
- Make sure Python 2.7 is installed (use python --version) and configured in Env path. 
- Download robotframework & selenium2library: <br/>
Use `pip install robotframework` and `pip install robotframework-selenium2library`. 
Download Perfecto repot with 'pip install perfecto'

Follow more information in - https://developers.perfectomobile.com/display/PD/Download+SDK#DownloadSDK-Python

- Set your credentials within the [perfecto.robot](perfecto.robot) file: 
```robot
*** Variables ***
| @{_tmp}
| ... | platformName:Android,
| ... | browserName:chrome,
| ... | deviceName:,
| ... | user:UserName,
| ... | password:Password
```

- Execute the test using `robot .` or separate files 'robot perfecto_Web_Desktop.robot' and 'robot perfecto_Web_Mobile.robot', which will run test in Mobile and Desktop web browsers
