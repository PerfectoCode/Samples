import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;

public class SwissPageObjects {
	//public static String userFirstNameId;
	public By flightTabId;
	public By flightSpecScr;
	public By from;
	public By selectFrom;
	public By to;
	public By selectTo;
	public By datesSpecPage;
	public By fromDate;
	public By fromDateSet;
	public By toDate;
	public By toDateSet;
	public By flightClass;
	public By economyClass;
	public By searchFlightButton;
	public By flightToSelectPage;
	public By firstFlightTree;
	public By depInfo;
	public By depTimeNode;
	public By arvInfo;
	public By arvTimeNode;
	public By economyPrice;
	public By price;
	public By currency;
	public By flightSelection;
	public By selectReturnFlightBtn;
	public By flightBackSelectPage;
	public By secondFlightTree;
	public By continueToSeatBtn;
	public By seatSelectBtn;
	public By freeRowAStandardSeats;


	public SwissPageObjects(String platform)
	{
		switch(platform) {
		case "Desktop":
		case "desktop":
			setDesktopXpaths();
			break;
		case "Mobile":
		case "mobile":
			setMobileXpaths();
			break;
		default:
			System.out.println("illegal platform");
			break;
		}
	}  

	private void setDesktopXpaths() {
		this.flightTabId = By.id("tabs_1_0");
		this.datesSpecPage = By.xpath("//*[@class='bookingbar frm-dark js-bookingbar']");
		this.from = By.id("Origin");
		this.selectFrom = By.xpath("//a[ancestor::*[@id='site-container'] and @data-autocomplete='Zurich (ZRH)']");
		this.to = By.id("Destination");
		this.selectTo = By.xpath("//a[ancestor::*[@id='site-container'] and @data-autocomplete='London (LON)']");
		this.fromDate = By.id("OutboundDate");
		this.fromDateSet = By.xpath("//a[contains(@title,'20 June 2016') and ancestor::*[contains(@class,'outbound')]]");
		this.toDate = By.id("InboundDate");
		this.toDateSet = By.xpath("//a[contains(@title,'30 June 2016') and ancestor::*[contains(@class,'return')]]");
		this.searchFlightButton = By.xpath("//button[ancestor::*[@id='bookingbar-flight'] and contains(text(),'Search')]");
		this.flightToSelectPage = By.xpath("//*[parent::*[@id='matrixMaintitle'] and contains(text(),'Zurich')]");
		this.firstFlightTree = By.xpath("//*[@id='frm-matrix']/div/div[1]");
		this.depInfo = By.xpath("//div[@class='book-bundle-flightentry--departure']");
		this.depTimeNode = By.xpath("//div[@class='book-bundle-flightentry--departure']/strong");
		this.arvInfo = By.xpath("//div[@class='book-bundle-flightentry--arrival']");
		this.arvTimeNode = By.xpath("//div[@class='book-bundle-flightentry--arrival']/strong");
		this.price = By.xpath("//span[contains(@class,'price')]");
		this.currency = By.xpath("//span[contains(@class,'currency')]");
		this.economyPrice = By.xpath("//button[@data-type='economy' and ancestor::*[@class='book-bundle-buttons']]");
		this.flightSelection = By.xpath("//label[contains(@class,'bottom') and descendant::span[text()='Light']]");
		this.selectReturnFlightBtn = By.xpath("//button[ancestor::*[@id='stickybasket'] and contains(text(),'Select return flight')]");
		this.flightBackSelectPage = By.xpath("//*[parent::*[@id='matrixMaintitle'] and contains(text(),'London')]");
		this.secondFlightTree = By.xpath("//*[@id='frm-matrix']/div[2]/div[1]");
		this.continueToSeatBtn = By.xpath("//button[ancestor::*[@id='stickybasket'] and contains(text(),'Continue')]");
		this.freeRowAStandardSeats = By.xpath("//button[ancestor::*[@id='seatmapForm'] and contains(@class,'is-standard') and not(contains(@class,'is-occupied')) and contains(@name,'a')]");
		
	}

	private void setMobileXpaths() {
		this.flightTabId = By.id("//*[@resource-id='com.yoc.swiss.swiss:id/book_flight']");
		//this.flightSpecScr = By.xpath("");
		//this.datesSpecPage = By.xpath("");
		this.from = By.xpath("(//input[@id='SearchODCalenderModel_SearchCriteria_Origin'])[1]");
		//this.selectFrom = By.xpath("");
		this.to = By.xpath("(//input[@id='SearchODCalenderModel_SearchCriteria_Destination'])[1]");
		//this.selectTo = By.xpath("");
		this.fromDate = By.xpath("//input[@id='datefrom']");
		//this.fromDateSet = By.xpath("");
		this.toDate = By.xpath("//input[@id='dateto']");
		//this.toDateSet = By.xpath("");
		this.flightClass = By.xpath("//option[@value='Y' and text()='Economy']");
		this.economyClass = By.xpath("//*[@text='Economy']");
		this.searchFlightButton = By.xpath("//button[contains(text(),'Search flights')]");
		//this.flightToSelectPage = By.xpath("");
		this.firstFlightTree = By.xpath("//h3[contains(text(),'20/06/2016') and contains(text(),'Zurich') and contains(text(),'London')]");
		/*this.depInfo = By.xpath("");
		this.depTimeNode = By.xpath("");
		this.arvInfo = By.xpath("");
		this.arvTimeNode = By.xpath("");
		this.price = By.xpath("");
		this.currency = By.xpath("");*/
		this.economyPrice = By.xpath("/html[1]/body[1]/div[3]/div[2]/div[3]/div[1]/span[1]/div[1]/div[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[2]/div[1]/button[1]");
		this.flightSelection = By.xpath("/html[1]/body[1]/div[3]/div[2]/div[3]/div[1]/span[1]/div[1]/div[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[2]/div[1]/div[1]/ul[1]/li[1]/div[1]/div[2]/label[1]/span[1]/span[1]");
		this.selectReturnFlightBtn = By.xpath("/html[1]/body[1]/div[3]/div[2]/div[3]/div[1]/span[1]/div[1]/div[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[2]/button[1]");
		//this.flightBackSelectPage = By.xpath("");
		this.secondFlightTree = By.xpath("//h3[contains(text(),'30/06/2016') and contains(text(),'Zurich') and contains(text(),'London')]");
		this.continueToSeatBtn = By.xpath("//button[parent::*[@class='book_bundle_row--submit'] and contains(text(),'Continue')]");
		this.seatSelectBtn = By.xpath("//div[contains(@class,'seat-selection') and text()='Select']");
		this.freeRowAStandardSeats = By.xpath("./button[ancestor::*[@id='seatmapForm'] and contains(@class,'is-standard') and not(contains(@class,'is-occupied')) and contains(@name,'a')]");
	}

}
