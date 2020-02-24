package com.rrpproxypage.java;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class RRPTabPage extends TestBase {

	//Objects
//	@FindBy(how = How.XPATH, using = "//*[@id='mainMenuToggle']")
//	WebElement clickOnMenuIcon;
	
	@FindBy(how = How.CSS, using = "#mainMenuToggle")
	WebElement clickOnMenuIcon;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Menu']")
	WebElement clickOnMenuLink;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Domains']")
	WebElement clickOnDomainsLink;
	
	@FindBy(how = How.XPATH, using = "//*[contains(text(),'gulliver')]")
	WebElement clickOnGulliverLink;
	
	//Initializing Page Objects
	public RRPTabPage(){
		   PageFactory.initElements(driver, this);
	}
	
	//Methods
	public RRPDomainsPage clickOnDomainsLink() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver,30); 
		wait.until(ExpectedConditions.visibilityOfAllElements(clickOnGulliverLink));
		if(clickOnMenuLink.isDisplayed()) {
			clickOnDomainsLink.click();
    	}
		else {
			Thread.sleep(2000);
			clickOnMenuIcon.click();
			Thread.sleep(2000);
			clickOnDomainsLink.click();
		}
		
		return new RRPDomainsPage();
	}
}
