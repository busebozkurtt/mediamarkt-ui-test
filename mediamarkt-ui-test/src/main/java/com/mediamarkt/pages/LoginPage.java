package com.mediamarkt.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePageObject{
	
	private By loginEmailLocator = By.xpath("//input[@id='login-email']");
	private By loginPasswordLocator = By.xpath("//input[@id='login-password']");
	private By loginButtonLocator = By.xpath("//button[@type='submit']/span[contains(text(),'Giriş')]");
    private By errorMessageLocator = By.xpath("//section[@class='error-box']");
	public LoginPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	public void enterUserInfo (String email, String password) {
		scrollPageUntilVisibleElement(loginEmailLocator);
		setText(loginEmailLocator, email , 10);
		setText(loginPasswordLocator, password , 10);
		click(loginButtonLocator, 10);
	}
	
	public Boolean isErrorMessageAppear() {
        return isElementAvailable(errorMessageLocator, 10);
	}
}
