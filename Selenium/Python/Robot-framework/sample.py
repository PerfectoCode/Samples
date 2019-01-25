from robot.libraries.BuiltIn import BuiltIn
from perfecto import PerfectoExecutionContext, TestResultFactory, TestContext, PerfectoReportiumClient
from perfecto import model
import json

def get_reporting_client():
    se2lib = BuiltIn().get_library_instance('Selenium2Library')
    print se2lib._current_browser()
    cFields = [{'name':'Perfecto', 'value' : 'Selenium'}, {'name':'Automation', 'value' : 'Browser'}]
    cf1 = model.CustomField(cFields[0]['name'], cFields[0]['value'])
    cf2 = model.CustomField(cFields[1]['name'], cFields[1]['value'])
    job = {'name' : "RobotFramworkJob", 'number' : 11}
    project = {'name': 'myproject', 'version' : 1.0}
    perfecto_execution_context = PerfectoExecutionContext(webdriver=se2lib._current_browser(),
                                                              tags=["mytest"],
                                                              job=model.Job(job['name'], job['number']),
                                                              project=model.Project(project['name'], project['version']),
                                                              customFields=[cf1, cf2])
    reporting_client = PerfectoReportiumClient(perfecto_execution_context)

    print reporting_client

    return reporting_client


def reportium_start(reporting_client, testname, tags):


	cFields = [{'name':'name1', 'value' : 'value1'}, {'name':'name2', 'value' : 'value2'}]

	cf1 = model.CustomField(cFields[0]['name'], cFields[0]['value'])
	cf2 = model.CustomField(cFields[1]['name'], cFields[1]['value'])

 	reporting_client.test_start(testname, TestContext(customFields=[cf1, cf2], tags=tags))


def reportium_stop(reporting_client, status, errormessage):

 	cf1 = model.CustomField('key1', 'val1')
 	cf2 = model.CustomField('key2', 'val2')
 	tec = TestContext(customFields=[cf1, cf2], tags=['test1', 'test2'])
 	if status == 'PASS':
	 	reporting_client.test_stop(TestResultFactory.create_success())
	elif status == 'FAIL':
		reporting_client.test_stop(TestResultFactory.create_failure(errormessage,
                                                                         "FAILED",
                                                                         ),tec)

def sample():
    print "this is sample fucntion"


