package com.netregistrynewwebsite.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class NRGNSRegistrantContactPage extends TestBase{

	
	//Objects        
    @FindBy(how=How.XPATH, using = "//body/div[1]/div[2]/div/div/div/div/div[1]/div/div[2]/div")
    WebElement selectButton;
	
    
    //Initializing Page Objects
    public NRGNSRegistrantContactPage() throws InterruptedException{
   	

    	PageFactory.initElements(driver, this);

//        try{
//        	Thread.sleep(8000);
//        	driver.navigate().refresh();
//        	driver.get(driver.getCurrentUrl());
//            
//        }
//        catch(Exception er){       
//          
//        	er.printStackTrace();
//        } 
    }


    //Methods    
    public NRGNSReviewAndPaymentPage clickSelectButton() throws InterruptedException {

    	Thread.sleep(5000);
    	driver.navigate().refresh(); 
    	Thread.sleep(5000);
    	driver.get(driver.getCurrentUrl());
    	Thread.sleep(3000);
    	driver.get(driver.getCurrentUrl());
    	
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectButton);
    	selectButton.click();
    	
    	return new NRGNSReviewAndPaymentPage();
    	
    }
    
}

