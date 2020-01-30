package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPTransferDomainsPage extends TestBase {

	//Objects
	@FindBy(how = How.NAME, using = "domainName")
	WebElement enterDomainName;
	
	@FindBy(how = How.NAME, using = "domainTld")
	WebElement selectDomainNamespace;
	
	@FindBy(how = How.NAME, using = "domainPassword")
	WebElement enterDomainAuthCode;
	
	@FindBy(how = How.NAME, using = "addSingleSelection")
	WebElement clickOnAdd;
	
	@FindBy(how = How.XPATH, using = "//*[@id='domainsTransferList']/table/tbody/tr[2]/td[2]")
	WebElement getStatusOfDomain;
	
	//Initializing Page Objects
	public TPPTransferDomainsPage(){
		PageFactory.initElements(driver, this);
	}
	
	//Methods
	public void enterDomainPrefix(String domainprefix) throws InterruptedException{
		Thread.sleep(2000);
		enterDomainName.sendKeys(domainprefix);
		Thread.sleep(2000);
	}
	
	public void selectDomainNamespace(String namespace) throws InterruptedException{
		Thread.sleep(2000);
		selectDomainNamespace.sendKeys(namespace);
	}
	
	public void enterAuthCode(String authCode) throws InterruptedException{
		Thread.sleep(2000);
		enterDomainAuthCode.sendKeys(authCode);
	}
	
	public void clickOnAddLink() throws InterruptedException{
		clickOnAdd.click();
	}
	
	public String getDomainStatus() throws InterruptedException{
		Thread.sleep(2000);
		String statusOfDomain = getStatusOfDomain.getText();
		return statusOfDomain;
	}
}
