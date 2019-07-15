package com.consoleadmin.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.Environment;
import com.base.TestBase;


public class CALoginPage extends TestBase{
		
	//Objects
    @FindBy(how=How.NAME, using = "login")
    WebElement userName;

    @FindBy(how=How.NAME, using = "password")
    WebElement password;
    
    @FindBy(how=How.NAME, using = "submit")
    WebElement submitButton;
	    
    //Initializing Page Objects
    public CALoginPage(){
    	PageFactory.initElements(driver, this);
    }
	    
    //Methods
    public boolean validateSubmitButtonExists(){
    	return submitButton.isDisplayed();
    }
    
    public CAHeaderPage setDefaultLoginDetails(String environment) throws InterruptedException {
    	
		if(environment.equalsIgnoreCase("stagingdev-5")||environment.equalsIgnoreCase("stagingdev-8")) {
			
	    	userName.sendKeys("erwin.sukarna");
	    	password.sendKeys("comein22");
		}
		else if(environment.equalsIgnoreCase("uat1")||environment.equalsIgnoreCase("uat2")) {
					
			userName.sendKeys("erwin.sukarna");
	    	password.sendKeys("comein22");
		}
		else if (environment.equalsIgnoreCase("ote")) {
			
			userName.sendKeys("roy.alcantara");
	    	password.sendKeys("");
		}
		else if (environment.equalsIgnoreCase("prod")) {
			
			userName.sendKeys("roy.alcantara");
	    	password.sendKeys("Stocks009");
		}
		
		submitButton.click();
		return new CAHeaderPage();
    }
    
    public CAHeaderPage login(String strusername, String strpassword){
    	
    	userName.sendKeys(strusername);
    	password.sendKeys(strpassword);
    	submitButton.click();
    	return new CAHeaderPage();
    }
}
