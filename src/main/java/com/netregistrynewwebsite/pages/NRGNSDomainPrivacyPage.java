package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;


public class NRGNSDomainPrivacyPage extends TestBase{

	
	//Objects    
    @FindBy(how=How.XPATH, using = "//div[@class='privacy-domains']/div")
	
    public static WebElement checkBox;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
   public  static WebElement continueButton;
    
    
    //Initializing Page Objects
    public NRGNSDomainPrivacyPage(){
    	PageFactory.initElements(driver, this);
    }

 
    //Methods
    public void clickCheckBox() {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkBox);
    	checkBox.click();
    	
    }
    
    
    public NRGNSEmailAndOffice365PackagesPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(3000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSEmailAndOffice365PackagesPage();
    	
    }
    
    //today
  
    public Boolean ischeckboxdisplayed(WebElement webElement) throws InterruptedException {
		boolean flag=false;
		try {
			// b=driver.findElement(By.xpath("//div[@class='privacy-domains']/div")).isDisplayed();
			if(webElement.equals(checkBox)) {
			flag=NRGNSDomainPrivacyPage.checkBox.isDisplayed();
			}else {
				flag=NRGNSDomainPrivacyPage.continueButton.isDisplayed();
			}
			if(flag) {
				test.log(LogStatus.PASS, "PASSED");
			}else {
				test.log(LogStatus.FAIL, "FAILED");
			}
		}catch(Exception e) {
			test.log(LogStatus.FAIL, "FAILED");
		}
		
		return flag;
    	
    	
    	}
    }


