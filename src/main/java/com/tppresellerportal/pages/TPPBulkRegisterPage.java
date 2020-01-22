package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPBulkRegisterPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "domain")
	WebElement newDomainSearchBox;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div/span")
	WebElement searchResultsText;
	
	
	// Initializing Page Objects
	public TPPBulkRegisterPage() {
		PageFactory.initElements(driver, this);
	}

	
	// Methods
	public void setAllDomainNames(String domainNames) throws Exception{
    	
		newDomainSearchBox.clear();
    	newDomainSearchBox.sendKeys(domainNames);
   
    }
	
	public String getSearchAvailabilityMessage(String domainName) throws InterruptedException {

		System.out.println("Return search availability message");
		
		//driver.findElement(By.xpath("//*[@id=\"availability\"]/table/tbody/tr[2]/td[1][text()='"+domainName+"']/parent::tr[2]/td[2]")).click();
		//Thread.sleep(2000);
		
		return searchResultsText.getText();

	}
	
	
	
}

