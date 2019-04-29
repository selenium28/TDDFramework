package com.consolesalesdb.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSLoginPage extends TestBase{
	
	//Objects
    @FindBy(how=How.ID, using = "logon-username-field")
    WebElement userName;

    @FindBy(how=How.ID, using = "logon-password-field")
    WebElement password;
    
    @FindBy(how=How.ID, using = "ext-gen14")
    WebElement loginButton;
	
    //Initializing Page Objects
    public CSLoginPage(){
    	PageFactory.initElements(driver, this);
    }

    //Methods
    public void setDefaultLoginDetails(String environment) {
		
		if(environment.equalsIgnoreCase("stagingdev-5")) {
			
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
	    	password.sendKeys("Stocks008");
		}
    }
    
    public CSNrCRMPage clickLoginButton() {
    	System.out.println("clicking submit button");
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		loginButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new CSNrCRMPage();
    }
    
}
