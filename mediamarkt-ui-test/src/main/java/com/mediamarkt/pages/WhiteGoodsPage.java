package com.mediamarkt.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WhiteGoodsPage extends BasePageObject{
	
	private By refrigeratorButtonLocator = By.xpath("//ul[@class='categories-flat-descendants']/li/a[contains(text(),'Buzdolabı')]");
	private By pageInfoLocator = By.xpath("//ul[@class='breadcrumbs']/li[contains(text(),'Beyaz Eşya')]");

	public WhiteGoodsPage(WebDriver driver, Logger log) {
		super(driver, log);
	}
	
	public String getPageInfoText () {
		return getText(pageInfoLocator);
	}
	
	public RefrigeratorPage selectRefrigerator() {
		scrollPageUntilVisibleElement(refrigeratorButtonLocator);
		click(refrigeratorButtonLocator, 10);
		return new RefrigeratorPage(driver, log);
	}
	
	public String getCurrentUrl() {
		return currentUrl();
	}
}
