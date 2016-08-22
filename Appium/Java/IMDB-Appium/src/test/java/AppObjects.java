package test.java;

public class AppObjects{

	public static final String Pop_Up_deny 	= "//*[text() = 'Deny' or @label='Don?t Allow']";
	public static final String Search		= "//*[@resource-id='com.imdb.mobile:id/search' or @label='Search']";
	public static final String Android_search_bar = "//*[@resource-id='com.imdb.mobile:id/search_src_text']";
	public static final String MoviePath	= "//*[contains(@name , 'Captain America: Civil War') or @resource-id='com.imdb.mobile:id/label' and @text='Captain America: Civil War?(2016)']";
	public static final String loginIMDB	= "//*[@text = 'IMDb']";
	public static final String watchTrailer = "//*[@label='Watch Trailer']";
	public static final String IOSWatchTrailer = "//*[@value='rows 1 to 6 of 6']/UIATableCell[1]/UIAButton[2]";
	
}