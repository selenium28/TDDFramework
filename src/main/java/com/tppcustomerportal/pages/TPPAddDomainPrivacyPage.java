package com.tppcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;

public class TPPAddDomainPrivacyPage extends TestBase{

	
	//Objects 
	 @FindBy(how=How.XPATH, using = "//div[@class='buttonRow']/input[@value='No thanks']")
	    WebElement noThanksButton;
	 
	//Initializing Page Objects
	    public TPPAddDomainPrivacyPage(){
	        PageFactory.initElements(driver, this);
	    }
	    
	    //Methods
	    public TPPHostingAndExtrasPage clickNoThanks(){
	    	try {
				System.out.println("clicking No Thanks");
				if(noThanksButton.isDisplayed()||noThanksButton.isEnabled()) {
					noThanksButton.click();
				}
				else {
					System.out.println("element not found");
				}
			} catch (Exception e) {
				System.out.println();
			}
	    	return new TPPHostingAndExtrasPage();
	    }
}
