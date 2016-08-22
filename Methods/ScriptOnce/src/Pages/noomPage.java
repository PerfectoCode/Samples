package Pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

enum OpSystem {
	Android, iOS
}

public abstract class noomPage {
	
	protected AppiumDriver<MobileElement> driver;
	protected OpSystem opS;
	
	public noomPage(AppiumDriver<MobileElement> d, String os) {
		this.driver = d;
		if (os.equalsIgnoreCase("android")) opS = OpSystem.Android;
		if (os.equalsIgnoreCase("ios")) opS = OpSystem.iOS;	
	}

	protected void selectSlider(Integer dispVal, Integer target, Integer ofs, WebElement tbl, String chXpath, Integer range, Integer interval) {
		Integer diff = target - dispVal;
		String htTab;
		Integer extreme = 0;
		while (diff > range || diff < (range * -1)) {
			if (diff > range) {
				 // need to move the slide rule by pressing on the extreme right
				dispVal += range;
				extreme = ((dispVal / interval) - 1) * interval;
			} else if (diff < (range * -1)) {
				// or move by pressing on extreme left
				dispVal -= range;
				extreme = ((dispVal / interval) + 1) * interval;
			}
			// move the slide ruler in direction of the target
			htTab = String.format(chXpath, extreme.toString());
			tbl.findElement(By.xpath(htTab)).click();
			
			diff = target - dispVal;
		}
		
		// click on the age ruler
		String hsTop = tbl.getAttribute("y");
		String hsHt = tbl.getAttribute("height");
		Map<String, Object> params2 = new HashMap<>();
		params2.put("content", target.toString());
		params2.put("screen.top", hsTop);
		params2.put("screen.height", hsHt);
		params2.put("shift", "left");
		params2.put("offset", ofs.toString());
		driver.executeScript("mobile:text:select", params2);
		
	}
}
