
public class AppLocators{
	/**
	 * Application locators.
	 */
	
	//Pre test pop-up allow.
	final static String AllowLocation 		= "//*[@text = 'Allow']";
	
	//Login app locators.
	final static String moreOptionsButton 	= "//*[@content-desc='More options']";
	final static String SignInMenuButton	= "//*[@text='Sign In']";
	final static String SignInIMDbAccount	= "//*[@text = 'IMDb']";
	final static String AppEmail			= "//*[@resource-id='ap_email']";
	final static String AppPassword			= "//*[@resource-id='ap_password']";
	final static String SignInButton		= "//*[@resource-id='signInSubmit-input']";
	
	//Search and result locators
	final static String Search				= "//*[@resource-id='com.imdb.mobile:id/search']";
	final static String SearchBox			= "//*[@resource-id='com.imdb.mobile:id/search_src_text']";
	final static String firstSearchResult	= "//*[@resource-id='com.imdb.mobile:id/main_scroller']/android.widget.LinearLayout[1]";
	
	//Movie page locators
	final static String RateThisButton		= "//*[@text = 'Rate this']";
	final static String StartsButton		= "//*[@resource-id='com.imdb.mobile:id/stars']";
	final static String SaveRating			= "//*[@resource-id='com.imdb.mobile:id/save_button']";
	
	/**
	 * Web locators.
	 */
	//Login web.
	final static String webLogIn			= "//*[@id = 'nblogin']";
	final static String WebIMDbLogIn		= "//*[@class = 'auth-sprite imdb-logo retina']";
	final static String webEmail			= "//*[@id = 'ap_email']";
	final static String webPass				= "//*[@id = 'ap_password']";
	final static String webSubmitButton		= "//*[@id = 'signInSubmit']";
	
	//Search and results on web.
	final static String SearchBarweb 		= "//*[@name= 'q']";
	final static String SearchButtonWeb		= "//*[@class = 'magnifyingglass navbarSprite']";
	final static String FirstWebResult		= "//*[@class = 'findResult odd' ]/td[2]/a[1]";
	final static String webYourRating		= "//*[@class ='star-rating-star rating']";
	final static String webDeleteRating		= "//*[@class = 'star-rating-delete']";


}