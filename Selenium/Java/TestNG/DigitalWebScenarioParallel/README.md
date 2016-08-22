#Digital Web Scenario, Parallel Execution

Demo java project, of parallel execution, mobile and desktop browsers side by side

The project is a Maven project, utilizing the TestNG framework.

**To do**:
- Import the project as a Maven Project
- Specify your target Web machines and mobile device IDs in the testng.xml file
- Specify your cloud credentials, if executed from CI / Maven, specify the host, username and password via the np.testHost, np.testUsername and np.testPassword arguments. If executed locally, you can manually specify them in the Utils.java class
- Execute the test by right clicking the testng.xml file and selecting Run As/TestNG Suite.
