package com.mediamarkt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	 protected Logger log;
	 protected WebDriver driver;
	 
	 @BeforeMethod
	 public void setup() {
		 System.setProperty("webdriver.chrome.driver", "chromedriver");
		 
		 log = LogManager.getLogger();
		 driver = new ChromeDriver();
		 
		 log.info("Created driver is starting");
	     driver.manage().window().maximize();
	 }
	 
	 @AfterMethod
	 public void tearnDown() {
		 log.info("Created driver quit");
		 driver.quit();
	 }
}
