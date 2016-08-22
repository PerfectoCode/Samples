public class PageObjects {

    //Galaxy S7 - First Test Xpaths
    final static String mainPageMenu    = "//*[@id = 'global-nav-mobile-trigger']";
    final static String menuNBA         = "//*[@class = 'link-text' and text() = 'NBA']";
    final static String NBATeams        = "//*[@class = 'link-text' and text() = 'Teams']";
    final static String SelectLakers    = "//*[text() = 'Los Angeles Lakers']";

    //Galaxy S7 - Second Test Xpaths
    final static String LoginButton     = "//*[text() = 'Log In']";
    final static String Email           = "//input[@type='email']";
    final static String password        = "//input[@type='password']";
    final static String submit          = "//button[@type='submit']";
    final static String userPlace       = "//*[@id = 'global-user-trigger']";
    final static String logOut          = "/html[1]/body[1]/div[6]/div[3]/ul[1]/li[7]/a[1]"; // <- Ugly path because there's 2 logout elements with the same id and class .

}
