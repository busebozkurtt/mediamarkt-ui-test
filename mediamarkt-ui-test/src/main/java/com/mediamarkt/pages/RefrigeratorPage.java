package com.mediamarkt.pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RefrigeratorPage extends BasePageObject{
	
	//filter locators by color and brand
	private By [] checkBoxes = {By.xpath("//li/label/span[contains(text(), 'BOSCH')]"),
			By.xpath("//li//label/span[@class='facet-text'][contains(text(),'Beyaz')]")};
	
	//energy filter dropdown locator
	private By dropDownLocator = By.id("filter-energyefficiencyclass");
	
	//error message locator when wrong value is entered
	private By errorMessageLocator = By.xpath("//fieldset/div/a[contains(text(), 'Fiyat')]/parent::div//following-sibling::div/div[@class='error-msg']");
	
	//price and total volume locators
	private Map<String, By> min_max_values= Map.ofEntries(
              Map.entry("PriceMin", By.xpath("//fieldset/div/a[contains(text(), 'Fiyat')]/parent::div//following-sibling::div/div/input[@class='price-from']")),
              Map.entry("PriceMax", By.xpath("//fieldset/div/a[contains(text(), 'Fiyat')]/parent::div//following-sibling::div/div/input[@class='price-to']")),
              Map.entry("TotalVolumeMin", By.xpath("//fieldset/div/a[contains(text(), 'Toplam Hacim')]/parent::div//following-sibling::div/div/input[@class='price-from']")),
              Map.entry("TotalVolumeMax", By.xpath("//fieldset/div/a[contains(text(), 'Toplam Hacim')]/parent::div//following-sibling::div/div/input[@class='price-to']"))); 
	
	//common locator of all products
	private By productsLocator = By.xpath("//ul[@class='products-list']//li/following-sibling::li/div/div/h2");
	
	//locators of filter results
	private By [] filterArray = {By.xpath("//ul[@class='filters']/li[contains(text(),'Marka')]"),
			By.xpath("//ul[@class='filters']/li[contains(text(),'Fiyat')]"),
			By.xpath("//ul[@class='filters']/li[contains(text(),'Enerji')]"),
			By.xpath("//ul[@class='filters']/li[contains(text(),'Renk')]"),
			By.xpath("//ul[@class='filters']/li[contains(text(),'Toplam Hacim')]")};
	
	//filters such as color, energy and brand are stored
	private List<String> choices_string = new ArrayList<String>();
	
	//filters with range such as price, total volume are stored
	private List<String> choices_range = new ArrayList<String>();

	public RefrigeratorPage(WebDriver driver, Logger log) {
		super(driver, log);
	}

	//energy is selected in dropdown
	public void getDropDownOption() {
		selectOptioninDropDown(dropDownLocator, 3);
		choices_string.add(getSelectedOption(dropDownLocator).split(" ")[0]+ " ".toUpperCase());
	}
	
	//checkboxes are marked
	public void selectCheckBoxes() {
		for(int i=0; i<checkBoxes.length; i++) {
		    WebElement element = find(checkBoxes[i], 10);
		    scrollPageUntilVisibleElement(checkBoxes[i]);
		    choices_string.add(getText(checkBoxes[i]).split(" ")[0]+ " ");
			click(checkBoxes[i], 10);
			log.info("Checkbox was selected.");
			refreshed(element, 10);
		}
	}
	
	//price and total volume values ​​are filled
	public void fillRangeValues(String key_min, String value_min, String key_max, String value_max) {
		scrollPageUntilVisibleElement(min_max_values.get(key_min));
		WebElement element = find(min_max_values.get(key_min), 10);
	    //Since text is not empty at the beginning, the old value is deleted to enter a new value
		deleteText(min_max_values.get(key_min));
		setText(min_max_values.get(key_min),value_min,5);
		deleteText(min_max_values.get(key_max));
		setText(min_max_values.get(key_max),value_max,5);
		enterKey(find(min_max_values.get(key_max), 10));
		log.info(key_min + " = " + value_min + ",  " + key_max + " = " + value_max);
		choices_range.add(value_min.toString() + " - " + value_max.toString());
		refreshed(element, 10);
	}
	
	//wrong values ​​are entered in the price filter
	public void fillWrongRangeValues(String key_min, String value_min, String key_max, String value_max) {
		scrollPageUntilVisibleElement(min_max_values.get(key_min));
		deleteText(min_max_values.get(key_min));
		setText(min_max_values.get(key_min),value_min,5);
		deleteText(min_max_values.get(key_max));
		setText(min_max_values.get(key_max),value_max,5);
		enterKey(find(min_max_values.get(key_max), 10));
		log.info(key_min + " = " + value_min + ",  " + key_max + " = " + value_max);
		choices_range.add(value_min.toString() + " - " + value_max.toString());
	}
	
	//It is checked whether the selected filters and incoming filters are the same
	public Boolean controlFiltersValues() {

		//if the value is a range of numbers it will be stored in the list of choices_range
		for (int i=0; i<filterArray.length; i++) {
			scrollPageUntilVisibleElement(filterArray[i]);
			String [] filter = getText(filterArray[i]).split(":")[1].split(" ");
			if(filter.length>=3) {
				if(!choices_range.contains(filter[1] + " - " + filter[3])) {
						return false;}
			}
			else {
				if(!choices_string.contains(filter[1] + " ")) {
					return false;}
			}
		}
		return true;
	}
	
	//It is checked whether the products brought are suitable for the selected filters
	public Boolean controlProducts() {
		List<WebElement> products = findAll(productsLocator, 10);
		for(int i = 0; i<products.size(); i++) {
			String title = getTextwithElement(products.get(i)) + " ";
			for (int j = 0; j<choices_string.size(); j++) {
				if(!title.contains(choices_string.get(j)))
					return false;
			}
		}
		return true;
	}
	
	//It is checked whether the error message is received when the wrong value is entered
	public Boolean isErrorMessageVisibility() {
		scrollPageUntilVisibleElement(errorMessageLocator);
		return isElementAvailable(errorMessageLocator, 10);
	}
}
