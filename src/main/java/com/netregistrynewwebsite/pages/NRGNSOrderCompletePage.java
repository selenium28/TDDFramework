package com.netregistrynewwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSOrderCompletePage extends TestBase{

	//Objects   
	@FindBy(how=How.XPATH, using = "//div[@class='pg-body ng-scope']/div[1]/h1")
	WebElement orderStatus; 
	
	@FindBy(how=How.XPATH, using = "//div[@class='pg-body ng-scope']/div[1]/p")
	WebElement orderCompleteMessage; 
	
	//Initializing Page Objects
	public NRGNSOrderCompletePage(){
		PageFactory.initElements(driver, this);
	}

	//Methods
    public Boolean isOrderComplete() throws InterruptedException{
    	Boolean flag = false;
    	System.out.println("Now in order complete page");
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", orderStatus);
    	
    	
    	Thread.sleep(5000);
    	if (orderStatus.getText().contentEquals("Thank you for your order")) {
    		System.out.println("Order Complete Status");
    		if (orderCompleteMessage.getText().contentEquals("Your order has been submitted successfully. An email with your order details has been sent to your registration email address. You can also print this page as a reference. The Reference IDs for your domains are listed below.")) {
    			System.out.println(orderCompleteMessage.getText());
    			flag = true;
    		}
    	}
    	return flag;
    }
    
    public String getAccountReferenceID() throws InterruptedException{
		String accountReference = null;
		
		Thread.sleep(5000);
		
    	WebElement accountReferenceElement = driver.findElement(By.xpath("//div[@class='summary-table']/h3/b"));
		if (accountReferenceElement.isDisplayed()) {
			accountReference = accountReferenceElement.getText();
    	}
    	return accountReference;
    }
    	
	public String getSingleReferenceID() throws InterruptedException{
		String referenceIDNumber = null;
		
		Thread.sleep(5000);
		
			
    	WebElement referenceIdNumberElement = driver.findElement(By.xpath("//div[@class='row row-domain']/div[1]"));
		if (referenceIdNumberElement.isDisplayed()) {
			referenceIDNumber = referenceIdNumberElement.getText(); 
    	}
    	return referenceIDNumber;
    }
	

}
