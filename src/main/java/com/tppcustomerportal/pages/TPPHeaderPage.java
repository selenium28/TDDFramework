package com.tppcustomerportal.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPHeaderPage extends TestBase{
	
	//Objects
    @FindBy(how=How.NAME, using = "domainNames")
    WebElement registerDomain;

	//Initializing Page Objects
		public TPPHeaderPage(){
	        PageFactory.initElements(driver, this);
	    }
		
		//Methods
		public void setDomainNameAndTld(String domainname, String tldname) throws Exception {

			registerDomain.clear();
			registerDomain.sendKeys(domainname);
			
			
				
	    }

}
