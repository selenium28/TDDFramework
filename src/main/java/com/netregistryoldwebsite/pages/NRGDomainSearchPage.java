package com.netregistryoldwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;


public class NRGDomainSearchPage extends TestBase{

	//Objects     
    @FindBy(how=How.ID, using = "continueCart")
    WebElement continueToCheckoutButton;
	
	//Initializing Page Objects
    public NRGDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
    public NRGAddDomainPrivacyPage clickContinueToCheckout(){
    	
		new WebDriverWait(driver, 30)
		.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGAddDomainPrivacyPage();
    }
    
    public NRGHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy() throws InterruptedException{
    	Thread.sleep(5000);
    	new WebDriverWait(driver, 30)
		.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
    	System.out.println("clicking continue to checkout");
    	if(continueToCheckoutButton.isDisplayed()||continueToCheckoutButton.isEnabled()) {
    		continueToCheckoutButton.click();
    	}
		else {
			System.out.println("element not found");
		}
    	return new NRGHostingAndExtrasPage();
    }
    
}
