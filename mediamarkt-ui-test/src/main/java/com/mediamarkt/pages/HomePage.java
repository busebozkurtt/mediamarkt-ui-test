package com.mediamarkt.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePageObject{
	
	private String pageUrl = "https://www.mediamarkt.com.tr/?ref=logo_rh";
	private By cookieLocator = By.xpath("//button[contains(text(), 'Kabul Ediyorum')]");
	private By whiteGoods = By.xpath("//li[@id='vitvaror']//a//div");
	private By accountLocator = By.xpath("//span[@class='ms-dropdown__trigger']");
	private By loginLocator = By.xpath("//span[@data-identifier='log_in_out_label']");
	
	public HomePage(WebDriver driver, Logger log) {
		super(driver,log);
	}
	
	public void openPage() {
		log.info("Opening page: " + pageUrl);
		openUrl(pageUrl);
	}
	
	public void acceptCookie() {
		click(cookieLocator, 10);
	}
	
	public WhiteGoodsPage selectWhiteGoods() {
		scrollPageUntilVisibleElement(whiteGoods);
		click(whiteGoods,10);
		log.info("White goods page opened");
		return new WhiteGoodsPage(driver,log);
	}
	
	public LoginPage login() {
		click(accountLocator, 10);
		click(loginLocator, 10);
		return new LoginPage(driver, log);
	}
}
