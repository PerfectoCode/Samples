package Reporting.Pages;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.perfecto.reportium.client.ReportiumClient;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class StickyBoard extends KeepPage {
	private By newList = By.xpath("//*[@resource-id='com.google.android.keep:id/new_list_button']");
	private String expNote = "//android.widget.FrameLayout[contains(@content-desc, '%s')]";
	private By header = By.xpath("//*[@text='Notes']");
	
	public StickyBoard(AndroidDriver<MobileElement> d, ReportiumClient r) {
		super(d, r);
		
		r.testStep("Showing the Bulletin Board of notes.");
		// should use this in Wait function!
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(d)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(header));
		} catch (Exception t) {
			System.out.println("Failed to identify the StickyBoard page");
		}		
	}
	
	public NoteEditor createNewList() {
		driver.findElement(newList).click();
		return new NoteEditor(driver, rep);
	}
	
	public boolean findNote(String title) {
		try {
			String xp = String.format(expNote, title);
			driver.findElementByXPath(xp);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
