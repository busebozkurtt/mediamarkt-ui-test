package com.mediamarkt;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mediamarkt.pages.HomePage;
import com.mediamarkt.pages.RefrigeratorPage;
import com.mediamarkt.pages.WhiteGoodsPage;
public class FilterTest extends BaseTest{
	
	@Test(priority = 1)
	public void filterTest() {
		
		String whiteGoodsUrl = "https://www.mediamarkt.com.tr/tr/category/_beyaz-e%C5%9Fya-465707.html";
		// Open home page
		log.info("Starting filter test.");
		HomePage homePageInstance = new HomePage(driver, log);
		homePageInstance.openPage();

		// Accept cookies.
		log.info("Accept Cookies.");
		homePageInstance.acceptCookie();
		
		// Open white goods page
		WhiteGoodsPage whiteGoodsPageInstance = homePageInstance.selectWhiteGoods();
		
		Assert.assertEquals(whiteGoodsPageInstance.getCurrentUrl(), whiteGoodsUrl, "Page url is not correct \nexpected page url: " + whiteGoodsUrl
				+ "\nactual url information: " + whiteGoodsPageInstance.getCurrentUrl());

		// Open refrigerator page
		RefrigeratorPage refrigeratorPageInstance = whiteGoodsPageInstance.selectRefrigerator();
		log.info("Refrigerator was selected!");
		
		// Select filters
		refrigeratorPageInstance.getDropDownOption();
		refrigeratorPageInstance.selectCheckBoxes();

		// Enter values of filters.
		refrigeratorPageInstance.fillRangeValues("PriceMin", "7000", "PriceMax", "15000");
		refrigeratorPageInstance.fillRangeValues("TotalVolumeMin", "520", "TotalVolumeMax", "685");
		
        // verifications
		Assert.assertTrue(refrigeratorPageInstance.controlFiltersValues(), "Filters are not the same as selected filters!!");
		Assert.assertTrue(refrigeratorPageInstance.controlProducts(), "Products do not contain selected filters!!");
		
	}
	
	@Test (priority = 2)
	public void filterTestwithWrongValues() {
		
		log.info("Starting filter test with wron values");
		// Open home page
		HomePage homePageInstance = new HomePage(driver, log);
		homePageInstance.openPage();

		// Accept cookies.
		log.info("Accept Cookies.");
		homePageInstance.acceptCookie();
		
		// Open white goods page
		WhiteGoodsPage whiteGoodsPageInstance = homePageInstance.selectWhiteGoods();
		
		// Open refrigerator page
		RefrigeratorPage refrigeratorPageInstance = whiteGoodsPageInstance.selectRefrigerator();
		log.info("Refrigerator was selected!");
		
        // verifications
        refrigeratorPageInstance.fillWrongRangeValues("PriceMin", "min-value", "PriceMax", " ");
        Boolean errorMessage1 = refrigeratorPageInstance.isErrorMessageVisibility();
		Assert.assertTrue(errorMessage1, "Error message didn't appear!!");
		
		refrigeratorPageInstance.fillWrongRangeValues("PriceMin", "15000", "PriceMax", "7000");
		Boolean errorMessage2 = refrigeratorPageInstance.isErrorMessageVisibility();
		Assert.assertTrue(errorMessage2, "Error message didn't appear!!");
	}
}
