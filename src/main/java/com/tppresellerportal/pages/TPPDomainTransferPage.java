package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

public class TPPDomainTransferPage extends TestBase {

	//Objects
	@FindBy(how = How.NAME, using = "domainName")
	WebElement enterDomainName;
	
	@FindBy(how = How.NAME, using = "domainTld")
	WebElement selectDomainNamespace;
	
	@FindBy(how = How.NAME, using = "domainPassword")
	WebElement enterDomainAuthCode;
	
	@FindBy(how = How.NAME, using = "addSingleSelection")
	WebElement clickOnAdd;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"domainsTransferList\"]/table/tbody/tr[2]/td[2]")
	WebElement getStatusOfDomain;
	
	//Initializing Page Objects
	public TPPDomainTransferPage(){
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
		Select select = new Select(selectDomainNamespace);
		select.selectByValue(namespace);
	}
	
	public void enterAuthCode(String authcode) throws InterruptedException{
		Thread.sleep(2000);
		enterDomainAuthCode.sendKeys(authcode);
	}
	
	public void clickOnAddLink() throws InterruptedException{
		Thread.sleep(2000);
		clickOnAdd.click();
	}
	
	public void verifyDomainStatus() throws InterruptedException{
		Thread.sleep(2000);
		String statusOfDomain = getStatusOfDomain.getText();
		Thread.sleep(2000);
		if(statusOfDomain.equalsIgnoreCase("Ok")){
			System.out.println("Domain is transferrable");
		} else {
			System.out.println("Domain is not transferrable");
		}
	}
}
