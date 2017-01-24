## Robot Framework 

The following sample demonstrate using [Robot](http://robotframework.org/) framework for executing test on Perfecto Lab. <br/>

### Getting Started: 
- Make sure Python 2.7 is installed (use python --version) and configured in Env path. 
- Download robotframework & selenium2library: <br/>
Use `pip install robotframework==2.9.2` and `pip install selenium2library`. 

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

- Execute the test using `pybot perfecto.robot`