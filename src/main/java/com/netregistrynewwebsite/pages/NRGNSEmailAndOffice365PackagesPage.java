package com.netregistrynewwebsite.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSEmailAndOffice365PackagesPage extends TestBase{

	@FindBy(how=How.CSS, using = "button.btn.green")
    WebElement continueButton;
	
    
    //Initializing Page Objects
    public NRGNSEmailAndOffice365PackagesPage(){
    	PageFactory.initElements(driver, this);
    }

    
    //Methods
    public void addOffice365Product(String office365Product) throws Exception {

    	List<WebElement> office365Products = driver.findElements(By.cssSelector(".o365office-box"));
    	
    	for (WebElement element : office365Products) {
    		
    		String productName = element.findElement(By.tagName("h4")).getText();
    		if (productName.equalsIgnoreCase(office365Product)) {
    			element.findElement(By.tagName("button")).click();
    			return;
    		}
    		
    	}
    	
    	throw new Exception("Office 365 product is not available");
    	
    }
    
    public NRGNSAddServicesToYourDomainPage clickContinueButton() throws InterruptedException {

    	Thread.sleep(5000);
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSAddServicesToYourDomainPage();
    	
    }
    
}
