package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPEligibilityDetailsPage extends TestBase {

	//Objects 
	@FindBy(how=How.ID, using = "comAuOwner-btn-by-number")
	WebElement businessNumberButton;
	
	@FindBy(how=How.ID, using = "comAuOwner-btn-by-name")
	WebElement businessNameButton;
	
	@FindBy(how=How.NAME, using = "agreePolicy['.com.au']")
	WebElement certifyDomainHasCloseAndSubstantialConnection;
	
	@FindBy(how=How.NAME, using = "auAgreeTOS['.com.au']")
	WebElement agreeTermsAndConditions;
	
	@FindBy(how=How.NAME, using = "saveEligibility")
	WebElement continueButton;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantidtype")
	WebElement eligibilityIdType;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantidnumber")
	WebElement eligibilityIdNumber;
	
	@FindBy(how=How.NAME, using = "comAuOwner.registrantname")
	WebElement companyName;
	
	@FindBy(how=How.NAME, using = "comAuOwner.eligibilitytype")
	WebElement eligibilityType;


    //Initializing Page Objects
    public TPPEligibilityDetailsPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods    
    public void clickBusinessNumberButton(){
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessNumberButton);
    	businessNumberButton.click();
    	    	
    }
    
    public void clickBusinessNameButton(){
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", businessNameButton);
    	businessNameButton.click();
    	    	
    }
    
    public void setEligibilityDetails(String eligibilityidtype, String eligibilityidnumber, String companyname, String eligibilitytype){
    	
    	eligibilityIdType.sendKeys(eligibilityidtype);
    	eligibilityIdNumber.sendKeys(eligibilityidnumber);
    	companyName.sendKeys(companyname);
    	eligibilityType.sendKeys(eligibilitytype);
    }
    
    public void tickCertifyDomainHasCloseAndSubstantialConnection(){
       	System.out.println("tick certify domain has close and substantial connection");
       	if(certifyDomainHasCloseAndSubstantialConnection.isDisplayed()||certifyDomainHasCloseAndSubstantialConnection.isEnabled()) {
       		certifyDomainHasCloseAndSubstantialConnection.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public void tickTermsAndConditions(){
       	System.out.println("tick agree to terms and conditions");
       	if(agreeTermsAndConditions.isDisplayed()||agreeTermsAndConditions.isEnabled()) {
       		agreeTermsAndConditions.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public TPPBillingPage clickContinueButton(){
    	System.out.println("clicking continue button");
    	if(continueButton.isDisplayed()||continueButton.isEnabled()) {
    		continueButton.click();
    	}
		else {
			System.out.println("element not found");
		}    	
    	return new TPPBillingPage();
    }
    

	
}
