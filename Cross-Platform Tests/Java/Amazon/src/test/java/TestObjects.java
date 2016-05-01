
/**
 * @author Daniel.
 * All TestObjects where taken from amazon.co.uk and amazon APP (uk ver) on mobile.
 * Some of the XPaths generated to match iOS and Android platform (Same XPath).
 */
public class TestObjects{
	/**iOS Objects**/
	//Amazon Application menu + login buttons and fields.
	public static final String AppMenu 			= "//*[@name = 'Show Navigation Menu' or @resource-id='com.amazon.mShop.android:id/action_bar_burger_icon']";
	public static final String Sign_in 			= "//*[@label = 'Sign in' or @resource-id='gw-mobile-greeting-bar']";
	public static final String userName 		= "//*[@value = 'E-mail (phone for mobile accounts)' or @resource-id='ap_email']";
	public static final String password 		= "//*[@value = 'Amazon password' or @resource-id='ap_password']";
	public static final String login_button 	= "//*[@name  = 'Sign in'][2]";
	public static final String Android_loginBTN = "//*[@resource-id='signInSubmit']";
	//Search scenario buttons and fields.
	public static final String Search			= "//*[@value = 'What are you looking for?' or @text = 'Amazon.co.uk']";
	public static final String AndroidSearch2 	= "//*[@text = 'Search in All Departments']";
	public static final String CDsAndVinyl		= "//*[@name = 'in CDs & Vinyl' or @text='in CDs & Vinyl']";
	public static final String DarkSideCD		= "//*[@name = 'The Dark Side Of The Moon' or @text='The Dark Side Of The Moon']";
	public static final String AddtoBasket		= "//*[@name = 'Add to Basket']";
	public static final String Android_AddtoBasket = "//*[@resource-id='add-to-cart-button']";
	//End scenario and sign out.
	public static final String SignOut				= "//*[@name = 'Not Daniel? Sign out' or @text= 'Not Daniel? Sign out']";
	public static final String PopUpSignOut 		= "//*[@name = 'Sign Out'][2]";
	public static final String Android_PopUpSignOut = "//*[@resource-id='android:id/button2']";
	
	/**Selenium web driver objects (Desktop test)**/
	//First page + Login session objects.
	public static final String desktop_login_id 	= "nav-link-yourAccount";
	public static final String desktop_username_id 	= "ap_email"; 
	public static final String desktop_password_id	= "ap_password";
	public static final String desktop_loginBTN_id	= "signInSubmit";
	//Go Cart and checkout.
	public static final String desktop_cart_id			= "nav-cart-count";
	public static final String desktop_checkout_xpath 	= "//*[@type = 'submit' and @name = 'proceedToCheckout']";
	//Delivery address page.
	public static final String desktop_full_name_id 	= "enterAddressFullName";
	public static final String desktop_address_line1_id	= "enterAddressAddressLine1";
	public static final String dekstop_city_id 			= "enterAddressCity";
	public static final String desktop_country1_id		= "enterAddressStateOrRegion";
	public static final String desktop_post_code_id		= "enterAddressPostalCode";
	public static final String desktop_phone_id			= "enterAddressPhoneNumber";
	public static final String desktop_address_type_id  = "AddressType";
	public static final String desktop_CountrySelect_id = "enterAddressCountryCode";
	public static final String desktop_Continue_xpath 	= "//*[@name = 'shipToThisAddress']";
	//delete item.
	public static final String desktop_deleteItem_xpath = "//*[@type = 'submit' and @value = 'Delete']";
	

	
	
}