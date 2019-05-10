package com.netregistrynewwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.melbourneitwebsite.pages.MITOrderCompletePage;

public class NRGNSReviewAndPaymentPage extends TestBase{

	
	//Objects        
    @FindBy(how=How.ID, using = "autoRenew")
    WebElement automaticRenew;
   
    //Old Path Before Captcha Deployment
    //@FindBy(how=How.XPATH, using = "//div[@class='cart-box cc-box element-table']/div[2]/div/label")
    //New Path After Captcha Deployment
    @FindBy(how=How.XPATH, using = "//div[@class='cart-box']/div[1]/div[2]/div/label")
    WebElement agreeTermsAndConditions;
    
    @FindBy(how=How.XPATH, using = "//div[@class='continue-btn-container']/button[@class='btn green']")
    WebElement completeOrderButton;
    
    @FindBy(how=How.XPATH, using = "//body/input")
    WebElement recaptchaChallenge;
	
    
    //Initializing Page Objects
    public NRGNSReviewAndPaymentPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods    
    public void setBTFormCreditCardDetails(String cardowner, String cardnumber, String cardexpirymonth, String cardexpiryyear, String cardsecuritycode){
    	driver.findElement(By.xpath("//div[@class='panel panel-default bootstrap-basic']/div[@class='table']/div[@class='element-group']/input[@id='name']")).clear();
    	driver.findElement(By.xpath("//div[@class='panel panel-default bootstrap-basic']/div[@class='table']/div[@class='element-group']/input[@id='name']")).sendKeys(cardowner);
    	driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='card-number']/iframe")));
    	
    	System.out.println("switched frame");
    	driver.findElement(By.xpath("//form/input")).clear();
    	driver.findElement(By.xpath("//form/input")).sendKeys(cardnumber);  
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='expiration-month']/iframe[@id='braintree-hosted-field-expirationMonth']")));
    	driver.findElement(By.xpath("//form/select")).sendKeys(cardexpirymonth);
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='expiration-year']/iframe[@id='braintree-hosted-field-expirationYear']")));
    	driver.findElement(By.xpath("//form/select")).sendKeys(cardexpiryyear);
    	driver.switchTo().defaultContent();
    	
    	driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='cvv']/iframe[@id='braintree-hosted-field-cvv']")));
    	driver.findElement(By.xpath("//form/input")).clear();
    	driver.findElement(By.xpath("//form/input")).sendKeys(cardsecuritycode); 
    	driver.switchTo().defaultContent();
    }
    
    public void tickTermsAndConditions(){
    	
       	System.out.println("I agree to the terms outlined in the Terms and Conditions");
       	if(agreeTermsAndConditions.isDisplayed()||agreeTermsAndConditions.isEnabled()) {
       		agreeTermsAndConditions.click();
       	}
    	else {
    		System.out.println("element not found");
    	}
    }
    
    public NRGNSOrderCompletePage clickCompleteOrder() throws InterruptedException{
    	
       	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", completeOrderButton);
       	System.out.println("clicking complete order button");
       	if(completeOrderButton.isDisplayed()||completeOrderButton.isEnabled()) {
       		completeOrderButton.click();
       	}
    	else {
    		System.out.println("element not found");
    	}  
       	
       	Thread.sleep(4000);
       	return new NRGNSOrderCompletePage();  	
    }
    
    
    public Boolean isReCaptchaChallengeDisplayed() throws InterruptedException{
    	
    	Boolean flag = false;
    	String recaptchatoken = null;
    	
    	Thread.sleep(3000);
    	System.out.println("Verify if recaptcha is displayed");
    	driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='recaptcha challenge']")));
    	((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('type', '')", recaptchaChallenge);
    	recaptchatoken = 	recaptchaChallenge.getAttribute("value");
    	    	
    	if(recaptchaChallenge != null) {
    		
    		System.out.println("Recaptch Token: " + recaptchatoken);
	    	System.out.println("Recaptcha Challenge Displayed!");
    		flag = true;
       	}
    	
    	return flag;
    }
}

