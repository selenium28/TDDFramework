package com.melbourneitwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class MITCreditCardsDetailsPage extends TestBase{

	//Objects
//    @FindBy(how=How.XPATH, using = ".//*[@id='sidebar']/div/div[2]/ul/li[5]/a")
//    WebElement cancelServicesLink;
	
	@FindBy(how=How.XPATH, using = ".//form[@id='creditCardForm']/div[@class='control-group'][1]/label[@class='checkbox']/input")
	WebElement makeDefaultCreditCard;
	
	@FindBy(how=How.XPATH, using = ".//form[@id='creditCardForm']/div[@class='control-group'][2]/div[@class='controls']/input[@id='createCreditCardSubmit']")
	WebElement addCardButton;
	
	@FindBy(how=How.XPATH, using = "//div[@class='success-box']/p")
	WebElement addCardStatus; 

	//Initializing Page Objects
	public MITCreditCardsDetailsPage(){
        PageFactory.initElements(driver, this);
    }

    //Methods
//    public ConsoleClientCancelServicesPage clickCancelServicesLink() throws InterruptedException {
//
//    	System.out.println("clicking cancel services");
//    	if(cancelServicesLink.isDisplayed()||cancelServicesLink.isEnabled()) {
//    		cancelServicesLink.click();
//    	}
//		else {
//			System.out.println("element not found");
//		}
//
//    	return new ConsoleClientCancelServicesPage(driver);
//    	
//    }
	
	  public void setQuestFormCreditCardDetails(String cardowner, String cardtype, String cardnumber, String cardexpirymonth, String cardexpiryyear, String cardsecuritycode){
		    driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][1]/div[@class='controls']/input")).sendKeys(cardowner);
	    	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group select'][1]/div[@class='controls']/select")).sendKeys(cardtype);
	    	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][2]/div[@class='controls']/input")).sendKeys(cardnumber);
	    	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group select'][2]/div[@class='controls']/select[@id='selectExpMonth']")).sendKeys(cardexpirymonth);
	    	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group select'][2]/div[@class='controls']/select[@id='selectExpYear']")).sendKeys(cardexpiryyear);
	    	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][3]/div[@class='controls']/input[@id='inputCode']")).sendKeys(cardsecuritycode);   
	  }
	  
	  
	  public void setBTFormCreditCardDetails(String cardowner, String cardnumber, String cardexpirymonth, String cardexpiryyear, String cardsecuritycode){
		  	driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][1]/div[@class='controls']/input[@id='btCreditCard.owner']")).clear();
		    driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][1]/div[@class='controls']/input[@id='btCreditCard.owner']")).sendKeys(cardowner);
		    driver.switchTo().frame(driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][2]/div[@class='controls']/div[@id='btCreditCard.number']/iframe")));
		    
		    driver.findElement(By.xpath("//form/input")).clear();
	    	driver.findElement(By.xpath("//form/input")).sendKeys(cardnumber);  
	    	driver.switchTo().defaultContent();
		    
	    	driver.switchTo().frame(driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group select']/div[@class='controls']/div[@id='btCreditCard.expirationMonth']/iframe")));
	    	driver.findElement(By.xpath("//form/select")).sendKeys(cardexpirymonth);
	    	driver.switchTo().defaultContent();
	    	
	    	driver.switchTo().frame(driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group select']/div[@class='controls']/div[@id='btCreditCard.expirationYear']/iframe")));
	    	driver.findElement(By.xpath("//form/select")).sendKeys(cardexpiryyear);
	    	driver.switchTo().defaultContent();
	    	
	    	
	    	driver.switchTo().frame(driver.findElement(By.xpath("//form[@id='creditCardForm']/div[@class='ccNew cc-form form-horizontal']/div[@class='control-group'][3]/div[@class='controls']/div[@id='btCreditCard.cvv']/iframe")));
	    	driver.findElement(By.xpath("//form/input")).clear();
	    	driver.findElement(By.xpath("//form/input")).sendKeys(cardsecuritycode); 
	    	driver.switchTo().defaultContent();
	  
	  }

	  
	  public void tickMakeCreditCardAsDefaultPayment() {
	       	System.out.println("tick make credit card the default payment menthod for ALL billing services");
	       	if(makeDefaultCreditCard.isDisplayed()||makeDefaultCreditCard.isEnabled()) {
	       		makeDefaultCreditCard.click();
	       	}
	    	else {
	    		System.out.println("element not found");
	    	}
	  }

	  
	  public void clickAddCreditCard() {
		  
	       	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addCardButton);
	       	System.out.println("clicking add card button");
	       	if(addCardButton.isDisplayed()||addCardButton.isEnabled()) {
	       		addCardButton.click();
	       	}
	    	else {
	    		System.out.println("element not found");
	    	}       	

	  }
	  
	  public Boolean isNewCreditCardAdded() throws InterruptedException{
	    	Boolean flag = false;
	    	
	    	Thread.sleep(5000);
	    	if (addCardStatus.getText().contentEquals("New credit card has been added")) {
	    		System.out.println("New credit card has been added");
	    		flag = true;
	    	}
	    	return flag;
	  }
	  
	  
}
