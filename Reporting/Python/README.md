## Reporting SDK for Python

This sample shows how to add Perfecto Reporting to you unittest tests.

:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/Python-Implementation) for the complete SDK documentation.<br/>
:information_source: Click [here](https://github.com/PerfectoCode/Samples/wiki/Reporting) to get started with Perfecto Reporting. 

## Getting Started

Install Perfecto Reporting using pip command:
`pip install perfecto`

## Integration With Perfecto :

[Conf.py](Conf.py) contains already all the integration you need in order to run the tests with perfecto.
Set your Perfecto lab user, password and host:
```Python

class TestConf(unittest.TestCase):
    def __init__(self, *args, **kwargs):
        self.user = 'My_User'
        self.password = 'My_Pass'
        self.host = 'My_Host.perfectomobile.com'
        ...
```

The sample uses the following import statements:<br/>
```Python
from perfecto import PerfectoExecutionContext,TestResultFactory,TestContext,PerfectoReportiumClient
```
## Running the project :

Use Python from command line:
`Python test_navigation.py`