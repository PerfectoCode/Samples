package Reporting.Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import com.perfecto.reportium.client.ReportiumClient;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class RemindWin extends KeepPage {
	private By header = By.xpath("//*[@resource-id='com.google.android.keep:id/dialog_title']");
	private By dateHd = By.xpath("//*[@resource-id='com.google.android.keep:id/date_spinner']");
	private By today = By.xpath("//*[@resource-id='com.google.android.keep:id/reminder_date_today']");
	private By tmrrw = By.xpath("//*[@resource-id='com.google.android.keep:id/reminder_date_tomorrow']");
	private By nxtwk = By.xpath("//*[@resource-id='com.google.android.keep:id/reminder_date_same_weekday']");
	private By svBtn = By.xpath("//*[@resource-id='com.google.android.keep:id/save']");
	private By cncl = By.xpath("//*[@resource-id='com.google.android.keep:id/cancel']");
	
	public RemindWin(AndroidDriver<MobileElement> d, ReportiumClient r) {
		super(d, r);
		
		r.testStep("Selecting when to remind you");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(d)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(header));
		} catch (Exception t) {
			System.out.println("Failed to identify the SetReminder page");
		}		
	}
	
	public void pickDate() {
		driver.findElement(dateHd).click();
	}
	
	public void remToday() {
		driver.findElement(today).click();
	}
	
	public void remTmrrw() {
		driver.findElement(tmrrw).click();
	}
	
	public void remWk() {
		driver.findElement(nxtwk).click();
	}
	
	public NoteEditor save() {
		driver.findElement(svBtn).click();
		return new NoteEditor(driver, rep);
	}

	public NoteEditor cancel() {
		driver.findElement(cncl).click();
		return new NoteEditor(driver, rep);
	}
}
