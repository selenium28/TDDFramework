package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;

public class NRGNSAboutYouPage extends TestBase{

	
    //Objects        
    @FindBy(how=How.ID, using = "organisation")
    WebElement organisation;
    
    @FindBy(how=How.ID, using = "usertype")
    WebElement industry;
    
    @FindBy(how=How.ID, using = "firstname")
    WebElement firstName;
    
    @FindBy(how=How.ID, using = "lastname")
    WebElement lastName;
    
    @FindBy(how=How.ID, using = "address")
    WebElement streetAddress;
    
    @FindBy(how=How.ID, using = "suburb")
    WebElement city;

    @FindBy(how=How.ID, using = "country")
    WebElement country;
    
    @FindBy(how=How.ID, using = "state")
    WebElement state;
    
    @FindBy(how=How.ID, using = "postcode")
    WebElement postcode;
    
    @FindBy(how=How.ID, using = "phone")
    WebElement phoneNumber;

    @FindBy(how=How.ID, using = "email")
    WebElement email;

    @FindBy(how=How.ID, using = "email2")
    WebElement emailConfirmation;

    @FindBy(how=How.CSS, using = "button.btn.green")
    WebElement continueButton;
	
    @FindBy(how=How.ID, using = "login")
    WebElement userName;
    
    @FindBy(how=How.ID, using = "password")
    WebElement password;
    
    @FindBy(how=How.XPATH, using = "//form[@name='loginForm']/div[1]/div[4]/button")
    WebElement loginButton;
    
    
    
    //Initializing Page Objects
    public NRGNSAboutYouPage(){
    	PageFactory.initElements(driver, this);
    }

      
    //Methods    
    public void setDefaultCustomerDetails() {
    	
    	organisation.sendKeys("Netregistry");
    	industry.sendKeys("Automotive");
    	firstName.sendKeys("QA");
    	lastName.sendKeys("Team");
    	streetAddress.sendKeys("505 Lt Collins Street");
    	city.sendKeys("Melbourne");
    	//country.sendKeys("Australia");
    	state.sendKeys("Victoria");
    	postcode.sendKeys("3000");
    	phoneNumber.sendKeys("0386242440");
    	email.sendKeys("_qa-development@arq.group");
    	emailConfirmation.sendKeys("_qa-development@arq.group");
    	
    }
    
    public void setReturningCustomerContacts(String customeraccountreference, String customerpassword){
    	userName.sendKeys(customeraccountreference);
    	password.sendKeys(customerpassword);
    }
    
    public NRGNSRegistrantContactPage clickContinueButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSRegistrantContactPage();
    	
    }
    
    public NRGNSRegistrantContactPage clickLoginButton(){
    	
    	System.out.println("clicking login button");   	
    	if(loginButton.isDisplayed()||loginButton.isEnabled()) {
    		
    		loginButton.click();
    		System.out.println("Login Button was clicked");
    	}
    	else {
 			
    		System.out.println("element not found");
 		}
    	
    	return new NRGNSRegistrantContactPage();
    }
    
 
    
}
