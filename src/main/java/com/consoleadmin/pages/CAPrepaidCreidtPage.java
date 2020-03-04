package com.consoleadmin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class CAPrepaidCreidtPage extends TestBase{

		
	//Objects
    @FindBy(how=How.ID, using = "creditCardAmount")
    WebElement creditCardAmount;
	
    @FindBy(how=How.ID, using = "purchaseCredit")
    WebElement purchaseCredit;
    
    @FindBy(how=How.NAME, using = "cardOwner")
    WebElement cardOwner;
    
    @FindBy(how=How.NAME, using = "cardNumber")
    WebElement cardNumber;
    
    @FindBy(how=How.NAME, using = "cardType")
    WebElement cardType;
    
    @FindBy(how=How.NAME, using = "expiryMonth")
    WebElement expiryMonth;
    
    @FindBy(how=How.NAME, using = "expiryYear")
    WebElement expiryYear;
    
	//Initializing Page Objects
	public CAPrepaidCreidtPage(){
    	PageFactory.initElements(driver, this);
    }
	
    //Methods
	public void selectExistingCreditCard() {

		System.out.println("Selecting the existing credit card.");
		driver.findElement(By.id("useExistingBilling")).click();
		
	}

	public void enterAmount(String string) {

		System.out.println("Enter credit card amount.");
		creditCardAmount.clear();
		creditCardAmount.sendKeys("20");
		
	}

	public void clickPurchaseCredit() {

		System.out.println("Click Purchase Credit.");
		purchaseCredit.click();
		
	}

	public void confirmPurchase() {

		System.out.println("Confirm credit purchase.");
		driver.switchTo().alert().accept();
		
	}

	public String getSuccessPurchasedMessage() {

		System.out.println("Get success purchase message.");
		String strConfirmationMessage = null;
		strConfirmationMessage = driver.findElement(By.id("msg")).getText();
		return strConfirmationMessage;
	}

	public void setCreditCardDetails(String strCardOwner, String strCardType, String strCardNumber, String strExpiryMonth, String strExpiryYear ) {

		cardOwner.sendKeys(strCardOwner);
		Select cardTypeDropDown = new Select(cardType);
		cardTypeDropDown.selectByValue(strCardType);
		cardNumber.sendKeys(strCardNumber);
		expiryMonth.sendKeys(strExpiryMonth);
		expiryYear.sendKeys(strExpiryYear);
		
		
	}
	

  
}
