package com.mediamarkt.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

	protected Logger log;
	protected WebDriver driver;
	
	public BasePageObject(WebDriver driver, Logger log) {

		this.driver = driver;
		this.log = log;
	}
	
	protected void openUrl(String url) {
		driver.get(url);
	}
	
	protected WebElement find(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected boolean isElementAvailable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
	}
	
	//Each time a filter is selected, the page is refreshed and a stale element error is received.
	//When the filter is selected, it is waited until the element disappears from the page and then refresh is done. 
	//So the elements come again and the stale element error is resolved
	protected void refreshed(WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
	    wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
	}
	
	protected List<WebElement> findAll(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	protected void click(By locator, int timeout) {
		WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
	}
	
	protected void setText(By locator, String text, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).sendKeys(text);
	}
	
	protected String getText(By locator) {
		return find(locator, 10).getText();
	}
	
	protected String getTextwithElement(WebElement element) {
		return element.getText();
	}
	
	protected void deleteText(By locator) {
		 find(locator, 10).clear();;
	}
	
	protected void enterKey(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}
	
	//The page is scrolled until the element is found on the page.
	protected void scrollPageUntilVisibleElement(By locator) {
		Actions actions = new Actions(driver);
		WebElement element = find(locator, 10);
		actions.moveToElement(element);
		actions.perform();
	}
	
	protected String currentUrl () {
		return driver.getCurrentUrl();
	}
	
	protected void selectOptioninDropDown(By locator, int i) {
		log.info("Selecting option " + i + " from dropdown");
		WebElement dropdownElement = find(locator, 5);
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByIndex(i);
		refreshed(dropdownElement, 10);
	}
	
	protected String getSelectedOption(By locator) {
		scrollPageUntilVisibleElement(locator);
		WebElement dropdownElement = find(locator, 5);
		Select dropdown = new Select(dropdownElement);
		String selectedOption = dropdown.getFirstSelectedOption().getText();
		log.info(selectedOption + " is selected in dropdown");
		return selectedOption;
	}
}
