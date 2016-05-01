import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Daniel.
 * Configure the cloud URL .
 */
public class cloud{
	
	private URL webURL;
	private URL mobileURL;
	public cloud() throws MalformedURLException{
		mobileURL = new URL("https://" + Properties.mobileHost + "/nexperience/perfectomobile/wd/hub");
		webURL = new URL("https://" + Properties.webHost + "/nexperience/perfectomobile/wd/hub");
	}
	
	public URL getwebURL(){
		return this.webURL;
	}
	public URL getmobileURL(){
		return this.mobileURL;
	}
}
