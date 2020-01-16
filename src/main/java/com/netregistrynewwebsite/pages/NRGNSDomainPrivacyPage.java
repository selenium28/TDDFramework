package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;


public class NRGNSDomainPrivacyPage extends TestBase{

	
	//Objects    
    @FindBy(how=How.XPATH, using = "//div[@class='privacy-domains']/div")
	
    public static WebElement checkBox;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
   public  static WebElement continueButton;
    
    
    //Initializing Page Objects
    public NRGNSDomainPrivacyPage(){
    	PageFactory.initElements(driver, this);
    }

 
    //Methods
    public void clickCheckBox() {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
    	checkBox.click();
    	
    }
    
    
    public NRGNSEmailAndOffice365PackagesPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSEmailAndOffice365PackagesPage();
    	
    }
    
    }


