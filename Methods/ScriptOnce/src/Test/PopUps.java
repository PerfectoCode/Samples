package Test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class PopUps {

	public static void checkLogin(AppiumDriver<MobileElement> driver) {
		WebElement alert;
		try {
    		alert = driver.findElementByXPath("//UIAAlert[contains(@name, 'Sign In')]");
    		alert.findElement(By.className("UIATextField")).sendKeys("perfectoyw@gmail.com");
    		alert.findElement(By.className("UIASecureTextField")).sendKeys("Appium#8");
    		alert.findElement(By.name("OK")).click();
    	} catch (NoSuchElementException n1) {
            System.out.println("No username alert, just plow ahead!");
        }
	}

	public static void checkNotif(AppiumDriver<MobileElement> driver) {
		WebElement alert;
        try {
            alert = driver.findElementByXPath("//UIAAlert[contains(@name, 'Send You Notifications')]");
            alert.findElement(By.xpath("//*[starts-with(@name, 'Don')]")).click();
        } catch (NoSuchElementException n1) {
            System.out.println("No notifications popup, just plow ahead!");
        }
		
	}
}
