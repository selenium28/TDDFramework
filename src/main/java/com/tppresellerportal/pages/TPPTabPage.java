package com.tppresellerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPTabPage extends TestBase {

	// Objects
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/h3")
	WebElement domainsTab;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[2]/h3")
	WebElement productsTab;
	
	/* Domains Tab List */
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/ul/li[1]/a")
	WebElement registerLink;
	
	@FindBy(how = How.XPATH, using = "//*[@id=\"menu\"]/ul/li[1]/ul/li[2]/a")
	WebElement bulkRegisterLink;
	


	// Initializing Page Objects
	public TPPTabPage() {
		PageFactory.initElements(driver, this);
	}

	
	// Methods
	public void clickDomainsTab() throws InterruptedException {

		Thread.sleep(3000);
		System.out.println("clicking domains tab");
		if (domainsTab.isDisplayed() || domainsTab.isEnabled()) {
			domainsTab.click();
		} else {
			System.out.println("element not found");
		}
	
	}
	
	public TPPRegisterADomainPage clickRegisterLink() throws InterruptedException {

		Thread.sleep(3000);
		System.out.println("clicking register link");
		if (registerLink.isDisplayed() || registerLink.isEnabled()) {
			registerLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPRegisterADomainPage();
	
	}
	
	public TPPBulkRegisterPage clickBulkRegisterLink() throws InterruptedException {

		Thread.sleep(3000);
		System.out.println("clicking bulk register link");
		if (bulkRegisterLink.isDisplayed() || bulkRegisterLink.isEnabled()) {
			bulkRegisterLink.click();
		} else {
			System.out.println("element not found");
		}
		
		return new TPPBulkRegisterPage();
	
	}



}
