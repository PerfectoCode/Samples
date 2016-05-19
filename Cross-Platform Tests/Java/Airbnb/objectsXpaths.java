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

/*public static By signUp = By.xpath("//a[ancestor::*[@id='new-header'] and contains(text(),'Sign Up')]");
public static By signUpWithEmail = By.xpath("//*[@id='create_using_email_button']");
public static By signUpWithEmailPopupWin = By.xpath("//div[ancestor::*[@id='signup-modal-content'] and @class='panel-padding panel-body signup-login-form__extra-panel-body first']");
public static By userFirstNameId = By.id("signup_first_name");
public static By userLastNameId = By.id("signup_last_name");
public static By userEmailAttName = By.name("user[email]");
public static By userPassId = By.id("user_password");
public static By userBDMonthId = By.id("user_birthday_month");
public static By userBDMonth = By.xpath("//option[ancestor::*[@id='user_birthday_month'] and text()='May']");
public static By userBDDayId = By.id("user_birthday_day");
public static By userBDYearId = By.id("user_birthday_year");
public static By promotionEmailsId = By.id("user_profile_info_receive_promotional_email");
public static By signUpButton = By.xpath("//button[ancestor::*[@id='user_new'] and contains(text(),'Sign up')]");
public static By infoConfirmIntroWin = By.xpath("//div[@class='activation-image-panel activation-image-panel--groups-welcome']");
public static By infoConfirmGetStarted = By.xpath("//button[child::span[text()='Get Started']]");
public static By survey = By.xpath("//div[@class='panel-footer' and child::*[text()='Skip']]");
public static By surveySkip = By.xpath("//button[text()='Skip']");
public static By profilePhotoWin = By.xpath("//div[@class='activation-step-panel__body' and contains(@data-reactid,'photo_with_face')]");
public static By profileSkipId = By.id("startDate");
public static By profilePhotoSkip = By.xpath("//span[contains(@data-reactid,'profile-photo-panel') and text()='Skip for now']");
public static By profilePhoneWin = By.xpath("//div[@class='activation-step-panel__body' and contains(@data-reactid, 'phone_verified')]");
public static By profilePhoneId = By.id("activation_phone_number_0");
public static By setNonUSACountry = By.xpath("//*[@class='change-country-select']");
public static By setNonUSACountry2Israel = By.xpath("//option[text()='Israel']");
public static By confirmPhoneButton = By.xpath("//button[@class='btn btn-primary btn-large' and contains(@data-reactid, 'phone_verified')]");
public static By codeSent2MobileId = By.id("activation_phone_verification_code_0");
public static By allSetWin = By.xpath("//div[@class='activation-image-panel activation-image-panel--success' and contains(@data-reactid,'success')]");
public static By allSetStartButton = By.xpath("//button[contains(@data-reactid,'success')]");
public static By ImIn = By.xpath("//span[ancestor::*[@id='new-header'] and @class='value_name margin-right--tiny hide-md' and text()='Dorit']");
//public static By userIcon = By.xpath("//span[ancestor::*[@id='new-header'] and text()='Dorit'] and contains(@class,'margin-right--tiny hide-md'");
public static By userAccountSettings = By.xpath("//div[text()='Account Settings']");
public static By accountSettings = By.xpath("//a[@class='sidenav-item' and text()='Settings']");
public static By cancelAccountBtnId = By.id("cancel-account-button");
public static By confirmCancelAccountBtn = By.xpath("//button[ancestor::*[@id='cancel-account-form'] and @type='submit']");*/

//mobile XPaths and IDs
public static By msgMainTitle = By.xpath("//*[contains(@label,'Messages')]");
public static By msgBackButton = By.xpath("//*[contains(@label,'Back')");
public static By lastAirbnbMsg = By.xpath("//UIAStaticText[contains(@label,'Airbnb security code:')]");
//public static By lastAirbnbMsg = By.xpath("//UIAStaticText[@label='732-873, Unread, 7:25 AM, Airbnb security code: 3280. Use this to finish verification.']");
}