package com.perfecto.commons.testng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlPackage;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.perfecto.commons.testng.Logger.STATUS;

/**
 * TestNGSuite is the class for representing TestNG Test Suite. It is
 * implemented as singleton class, to represent one test suite per execution.
 * 
 * @author Prasant Sutaria
 * @version %I%, %G%
 * @since 1.0
 */

public class TestNGSuite {

	private String suiteName;
	private static TestNGSuite testNGSuite;
	private List<String> selectedGroups;
	private XmlSuite xmlSuite;
	private List<XmlPackage> xmlPackage;

	private TestNGSuite(String suiteName, List<String> selectedGroups,int threadCount) {

		this.suiteName = suiteName;
		this.selectedGroups = selectedGroups;
		this.xmlPackage = new ArrayList<>();
		this.xmlPackage.add(new XmlPackage("com.perfecto.tests.*"));
		this.xmlSuite = new XmlSuite();
		xmlSuite.setName(this.suiteName);
		xmlSuite.setTests(getTestNodes());
		xmlSuite.addListener("com.perfecto.commons.testng.TestNGListener");
		xmlSuite.setParallel(ParallelMode.TESTS);
		xmlSuite.setThreadCount(threadCount);
	}

	/**
	 * Returns an List of XmlTest Object.
	 * <p>
	 * This method always returns a List of Test represented by XmlTest Object.
	 *
	 * @return List of XmlTest Object
	 * @see List, XmlTest
	 */
	private List<XmlTest> getTestNodes() {

		List<XmlTest> tests = new ArrayList<>();
		Iterator<String> groupsIterator = selectedGroups.iterator();
		while (groupsIterator.hasNext()) {
			tests.add(getTestNode(groupsIterator.next()));
		}
		return tests;
	}

	/**
	 * Returns an XmlTest Object.
	 * <p>
	 * This method always returns a Test represented by XmlTest Object.
	 *
	 * @return XmlTest Object
	 * @see XmlTest
	 */
	private XmlTest getTestNode(String groupName) {
		XmlTest currentTestNode = new XmlTest();
		
		currentTestNode.setName(groupName);
		currentTestNode.setPackages(this.xmlPackage);
		
		List<String> groupsIncluded = new ArrayList<>();
		groupsIncluded.add(groupName);
		
		currentTestNode.setIncludedGroups(groupsIncluded);
		currentTestNode.setSuite(xmlSuite);
		currentTestNode.setParallel(ParallelMode.METHODS);
		return currentTestNode;
	}

	/**
	 * Returns an TestNGSuite Object.
	 * <p>
	 * This method always returns a Test Suite represented by TestNGSuite Object.
	 *
	 * @return TestNGSuite Object
	 * @see TestNGSuite
	 */
	public static TestNGSuite getTestSuite(String[] commandLineArg) throws InvalidCommandLineArgumentException{

		if (testNGSuite == null) {

			Map<String, Object> args = getCommandLineArgs(commandLineArg);
			
			String suiteName = (String) args.getOrDefault("SuiteName", "TestSuite");
			int threadCount = (int)args.getOrDefault("ThreadCount", 1);

			if(args.containsKey("Groups")) {
				@SuppressWarnings("unchecked")
				List<String> selectedGroups = (List<String>) args.getOrDefault("Groups", new ArrayList<>());

				testNGSuite = new TestNGSuite(suiteName, selectedGroups,threadCount);
				
				System.out.println(testNGSuite.xmlSuite.toXml());
			}else {
				throw new InvalidCommandLineArgumentException("Groups name should be provided.");
			}

		}
		return testNGSuite;
	}

	private static Map<String, Object> getCommandLineArgs(String[] commandLineArg) throws InvalidCommandLineArgumentException{

		Map<String, Object> argMap = new HashMap<>();
		String current;

		for (int index = 0; index < commandLineArg.length; ++index) {
			current = commandLineArg[index].toUpperCase();
			switch (current) {
			case "--SUITENAME":
			case "--SUITE_NAME":
				++index;
				current = commandLineArg[index];
				if(current.startsWith("--")) {
					throw new InvalidCommandLineArgumentException("SuiteName parameter should follow by Suite name");
				}else {
					argMap.put("SuiteName", current);
				}
				
				break;
			case "--GROUPS":
			case "--GROUP":
				++index;
				current = commandLineArg[index];
				if(current.startsWith("--")) {
					throw new InvalidCommandLineArgumentException("Groups parameter should follow by comma seperated group names");
				}else {
					argMap.put("Groups", Arrays.asList(current.split(",")));
				}
				
				break;
			case "--THREADCOUNT":
			case "--THREAD_COUNT":
				++index;
				current = commandLineArg[index];
				if(current.startsWith("--")) {
					throw new InvalidCommandLineArgumentException("ThreadCount parameter should follow by Thread count number");
				}else {
					try {
						argMap.put("ThreadCount", Integer.parseInt(current));
					}catch(Exception e) {
						throw new InvalidCommandLineArgumentException("ThreadCount parameter should follow by Thread count number");
					}
					
				}
				break;
			default:
				break;
			}

		}

		return argMap;
	}

	/**
	 * Returns an String Object.
	 * <p>
	 * This method always returns a Test Suite name represented by String Object.
	 *
	 * @return String Object
	 * @see String
	 */
	public String getSuiteName() {
		return this.suiteName;
	}

	/**
	 * Returns an List Object.
	 * <p>
	 * This method always returns a Selected Groups represented by List Object.
	 *
	 * @return List Object
	 * @see List, String
	 */
	public List<String> getSelectedGroups() {
		return this.selectedGroups;
	}

	/**
	 * Returns Nothing.
	 * <p>
	 * This method executes the testNG suite represented by TestNGSuite class.
	 *
	 * @return void
	 * @see TestNGSuite
	 */
	public void run() {

		TestNG testNG = new TestNG();
		List<XmlSuite> suites = new ArrayList<>();
		suites.add(xmlSuite);
		testNG.setXmlSuites(suites);
		
		try {
			testNG.run();
		}catch(Exception e) {
			Logger.log(STATUS.FAIL, e.getMessage());
		}
		
	}

}
