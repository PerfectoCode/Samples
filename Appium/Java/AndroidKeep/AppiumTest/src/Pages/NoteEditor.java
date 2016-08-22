package Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class NoteEditor extends KeepPage {
	private By titleF = By.xpath("//*[@resource-id='com.google.android.keep:id/editable_title']");
	private By newItem = By.xpath("//android.widget.EditText[@resource-id='com.google.android.keep:id/description' and @text ='']");
	private By addItem = By.xpath("//*[@resource-id='com.google.android.keep:id/add_item_text_view']");
	private By backBtn = By.xpath("//*[@content-desc='Navigate up']");
	private By remindBtn = By.xpath("//*[@resource-id='com.google.android.keep:id/menu_reminder']");
	
	public NoteEditor(AndroidDriver<MobileElement> d) {
		super(d);
		
		//verify that we are on the page!
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(d)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(titleF));
		} catch (Exception t) {
			System.out.println("Failed to identify the Note Editor page");
		}		
	}
	
	public void addTitle(String title) {
		try {
			WebElement titField = driver.findElement(titleF);
			titField.click();
			titField.sendKeys(title);
		} catch (Exception n) {
			System.out.println("Unable to enter note title");
		}
	}
	
	public void enterItem(String item) {
		try {
			WebElement itemF = driver.findElement(newItem);
			itemF.click();
			itemF.sendKeys(item);
		} catch (Exception n) {
			System.out.println("Unable to enter note item");
		}
	}
	
	public void addItem(String item) {
		try {
			WebElement itemF = driver.findElement(addItem);
			itemF.click();
			enterItem(item);
		} catch (Exception n) {
			System.out.println("Unable to enter note item");
		}	
	}
	
	public StickyBoard goBack() {
		driver.findElement(backBtn).click();
		return new StickyBoard(driver);
	}
	
	public RemindWin remind() {
		driver.findElement(remindBtn).click();
		return new RemindWin(driver);
	}
}
