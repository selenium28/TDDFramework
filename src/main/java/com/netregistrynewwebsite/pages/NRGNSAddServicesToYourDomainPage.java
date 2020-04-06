package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSAddServicesToYourDomainPage extends TestBase{

	
	//Objects        
    @FindBy(how=How.CSS, using = "button.btn.green")
    public static WebElement continueButton;
	
    
	//Initializing Page Objects
    public NRGNSAddServicesToYourDomainPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods    
    public NRGNSAboutYouPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSAboutYouPage();
    	
    }
    
}
