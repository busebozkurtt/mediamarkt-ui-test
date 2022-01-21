package com.mediamarkt;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.mediamarkt.pages.HomePage;
import com.mediamarkt.pages.LoginPage;

public class LoginTests extends BaseTest{
	@Parameters({ "email", "password"})
	@Test(groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String email, String password) {

		System.out.println("Starting negativeLoginTest with email: " + email + " password: " + password);

		//Open home page
		HomePage homePageInstance = new HomePage(driver, log);
		homePageInstance.openPage();
		
		// Open login page.
		LoginPage loginPageInstance = homePageInstance.login();
		log.info("Opened was login page");
		
		// enter incorrect user name and password
		loginPageInstance.enterUserInfo(email, password);
		
		// verifications
		Boolean errorMessage = loginPageInstance.isErrorMessageAppear();
		Assert.assertTrue(errorMessage, "Error message didn't appear!!");
	}
}
