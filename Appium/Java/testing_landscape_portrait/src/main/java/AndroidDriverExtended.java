import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.net.URL;
import java.util.HashMap;


/**
 * AndroidDriverExtended.
 * Read implementation and documentation the project's GitHub repo.
 */
public class AndroidDriverExtended extends AndroidDriver {

    //Xpath locating all the scrollable components
    private final static String scrollable = "(//*[@scrollable = \"true\"])[%d]";

    private int timesToScroll = 2;
    private int scrollLength = 1;
    private int ScrollableContext = 1;

    /**
     * Construct AndroidDriverExtended instance
     *
     * @param url          remote host
     * @param capabilities DesiredCapabilities instance
     */
    public AndroidDriverExtended(URL url, Capabilities capabilities) {
        super(url, capabilities);
    }

    /**
     * findElement extended
     * in case element not found trying to scroll down timesToScroll times
     *
     * @param by identifier
     * @return WebElement if found, otherwise returns null
     */
    @Override
    public WebElement findElement(By by) {
        WebElement elem = null;

        try {
            elem = super.findElement(by);

        } catch (NoSuchElementException ex) {
            System.out.println("Could not locate elements in the UI trying with scroll down.");

            int attempts = 0;

            while (elem == null && attempts < getTimesToScroll()) {
                elem = findElementUsingScrollDown(by);
                attempts++;
            }

        } finally {
            if (elem != null)
                return elem;
            else
                throw new NoSuchElementException("Cannot locate an element after " + getTimesToScroll() + " attempts, using " + this.toString());
        }
    }

    /**
     * Find element implementation using ScrollDown.
     * Ignoring "NoSuchElementException"
     *
     * @param by element locator
     * @return {@link WebElement} which found using findEelement method otherwise null
     */
    private WebElement findElementUsingScrollDown(By by) {
        try {
            ScrollDown();
            return super.findElement(by);
        } catch (Exception e) {
            // Catching .by class exception "NoSuchElementException".
        }
        return null;
    }

    /**
     * Scroll down using JavaScript execution
     */
    private void ScrollDown() {
        try {
            for (int i = 0; i < getScrollLength(); i++) {
                HashMap<String, String> scrollObject = new HashMap();
                scrollObject.put("direction", "down");
                scrollObject.put("element", ((RemoteWebElement) super.findElement(By.xpath(String.format(scrollable, getScrollableContext())))).getId());
                this.executeScript("mobile: scroll", scrollObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*********************
     * Getters & Setters *
     *********************/
    public int getTimesToScroll() {
        return timesToScroll;
    }

    public void setTimesToScroll(int timesToScroll) {
        timesToScroll = timesToScroll;
    }

    public int getScrollLength() {
        return scrollLength;
    }

    public void setScrollLength(int scrollLength) {
        scrollLength = scrollLength;
    }

    public int getScrollableContext() {
        return ScrollableContext;
    }

    public void setScrollableContext(int scrollableContext) {
        ScrollableContext = scrollableContext;
    }
}
