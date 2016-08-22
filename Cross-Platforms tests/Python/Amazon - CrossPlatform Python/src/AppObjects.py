
class Locators(object):
    
    #Login locators
    Sign_in                 = "//*[@label = 'Sign in' or @resource-id = 'gw-mobile-greeting-bar']"
    hello_sign_in           = "//*[@label= 'Hello. Sign In']"
    userName                = "//*[@value = 'E-mail (phone for mobile accounts)' or @resource-id='ap_email']"
    password                = "//*[@value = 'Amazon password' or @resource-id='ap_password']"
    login_btn_ios           = "//*[@name = 'Sign in'][2]"
    login_button_Android    = "//*[@resource-id = 'signInSubmit']"
    
    #LogOut locators
    AppMenu             = "//*[@name = 'Show Navigation Menu' or @resource-id='com.amazon.mShop.android:id/action_bar_burger_icon']"
    Sign_out_popup_ios  = "//*[@name = 'Sign Out'][2]"
    
    #Search session locators
    search_box      = "//*[@value = 'What are you looking for?' or @text = 'Amazon.co.uk']"
    search_result   = "//*[@name = 'in CDs & Vinyl' or @text='in CDs & Vinyl']"
    darkSideAlbum   = "//*[@name = 'The Dark Side Of The Moon' or @text='The Dark Side Of The Moon']"
    AddtoBasket     = "//*[@name = 'Add to Basket']"
    
    #Website locators Sign in
    desktop_login_id     = 'nav-link-yourAccount'
    desktop_username_id  = "ap_email"
    desktop_password_id  = "ap_password"
    desktop_loginBTN_id  = "signInSubmit"
    
    #Web locators cart
    desktop_cart_id            = "nav-cart-count"
    desktop_checkout_xpath     = "//*[@type = 'submit' and @name = 'proceedToCheckout']"

    #desktop checkout information
    desktop_full_name_id        = "enterAddressFullName"
    desktop_address_line1_id    = "enterAddressAddressLine1"
    dekstop_city_id             = "enterAddressCity"
    desktop_country1_id         = "enterAddressStateOrRegion"
    desktop_post_code_id        = "enterAddressPostalCode"
    desktop_phone_id            = "enterAddressPhoneNumber"
    desktop_address_type_id     = "AddressType"
    desktop_CountrySelect_id    = "enterAddressCountryCode"
    desktop_Continue_xpath      = "//*[@name = 'shipToThisAddress']"

    desktop_deleteItem_xpath = "//*[@type = 'submit' and @value = 'Delete']"
    
    