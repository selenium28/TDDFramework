package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSEmailAndOffice365PackagesPage extends TestBase{

	
	//Objects    
    @FindBy(how=How.XPATH, using = "//body/div[1]/div[2]/div/div/div/div[2]/div[1]/div[3]/div[2]")
    WebElement businessEssentials;
    
    @FindBy(how=How.XPATH, using = "//div[@class='table']/div/div[@class='continue-btn-container']/button")
    WebElement continueButton;
	
    
    //Initializing Page Objects
    public NRGNSEmailAndOffice365PackagesPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods
    public NRGNSOffice365LicenseQuantityPage clickAddBusinessEssensials() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessEssentials);
    	businessEssentials.click();

    	return new NRGNSOffice365LicenseQuantityPage();
    	
    }
    
    public NRGNSAddServicesToYourDomainPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSAddServicesToYourDomainPage();
    	
    }
    
}
