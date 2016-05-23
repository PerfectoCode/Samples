//package com.perfectomobile.pm.testng.selenium;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;

public class objectsXpaths {
//desktop web app XPaths and IDs
public static By loginLink = By.xpath("//a[ancestor::*[@id='new-header'] and contains(text(),'Log In')]");	
public static By loginWin = By.xpath("//*[parent::*[@id='login-modal-content'] and contains(@class,'signup-login-form')]");
public static By emailId = By.id("signin_email");
public static By pswdId = By.id("signin_password");
public static By loginBtnId = By.id("user-login-btn");
public static By userIcon = By.xpath("//span[ancestor::*[@id='new-header'] and text()='Dorit' and contains(@class,'margin-right--tiny hide-md')]");
public static By editProfile = By.xpath("//div[text()='Edit Profile']");
public static By addCellNo = By.xpath("//span[ancestor::*[@id='update_form'] and contains(text(),'Add a phone number')]");
public static By CellCountryId = By.id("phone_country");
public static By cellNoId = By.id("phone_number");
public static By verifyViaSMSBtn = By.xpath("//*[@id='update_form']/div[2]/div[2]/div[6]/div/div[1]/div/div[2]/div[1]/div[2]/a[1]");
public static By codeViaSMS = By.xpath("//*[preceding-sibling::*[contains(text(),'We sent a verification code to')] and @id='phone_number_verification']");
public static By verifyBtn = By.xpath("//*[@id='update_form']/div[2]/div[2]/div[6]/div/div[1]/div/div[2]/div[2]/div/a[1]");
public static By saveBtn = By.xpath("//*[@id='update_form']/button");

//mobile XPaths and IDs
public static By msgMainTitle = By.xpath("//*[contains(@label,'Messages')]");
public static By msgBackButton = By.xpath("//*[contains(@label,'Back')");
public static By lastAirbnbMsg = By.xpath("//UIAStaticText[contains(@label,'Airbnb security code:')]");
//public static By lastAirbnbMsg = By.xpath("//UIAStaticText[@label='732-873, Unread, 7:25 AM, Airbnb security code: 3280. Use this to finish verification.']");
}
