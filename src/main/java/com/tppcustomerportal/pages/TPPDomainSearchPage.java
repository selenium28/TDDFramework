package com.tppcustomerportal.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.tppcustomerportal.pages.TPPDomainSearchPage;


import com.base.TestBase;

public class TPPDomainSearchPage extends TestBase {
	
	//Objects
	
	@FindBy(how=How.XPATH, using ="//div[@class='orderBoxWrapper domainSearchTable']//table//tbody/tr[2]/td[2]")
    WebElement domainStatus;
    
	//Initializing Page Objects
	public TPPDomainSearchPage(){
        PageFactory.initElements(driver, this);
    }
	//Methods
	
	/* public String checkStatus()throws InterruptedException{
		
	    	System.out.println("checking domain status");
	    	if(domainStatus.isDisplayed()||domainStatus.isEnabled()) {
	    		return domainStatus.getText();
	    	}
			else
			{
				System.out.println("element not found");
			}
			   	//return domainStatus.getText();
	    }*/
	 
	 public Boolean checkStatus() throws InterruptedException{
	    	Boolean flag = false;
	        Thread.sleep(1000);
	        if(domainStatus.isDisplayed()||domainStatus.isEnabled()) 
	        {
	    			System.out.println(domainStatus.getText());
	    			flag = true;
	    		
	        }
	        else
	        {
	        	System.out.println("element not found");
	        }
	    	
	    	return flag;
	 }
}
