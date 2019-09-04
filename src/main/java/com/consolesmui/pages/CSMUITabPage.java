package com.consolesmui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUITabPage extends TestBase {

	// Objects
	@FindBy(how = How.CSS, using = "a[href*=\"dashboard\"]")
	WebElement dashboardTab;

	@FindBy(how = How.ID, using = "domainname-tab-link")
	WebElement domainNameTab;

	@FindBy(how = How.ID, using = "zonemanager-tab-link")
	WebElement zoneManagerTab;

	@FindBy(how = How.ID, using = "delegate-domain-tab-link")
	WebElement delegateDomainTab;

	@FindBy(how = How.ID, using = "domain-redirector-tab-link")
	WebElement delegateRedirectorTab;

	@FindBy(how = How.ID, using = "o365-tab-link")
	WebElement office365Tab;

	// Initializing Page Objects
	public CSMUITabPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public CSMUIDashboardPage clickDashoardTab() {

		System.out.println("Clicking Dashboard tab");
		dashboardTab.click();
		return new CSMUIDashboardPage();
	}

	public CSMUIDomainNamePage clickDomainNameTab() {

		System.out.println("Clicking Domain Name tab");
		domainNameTab.click();
		return new CSMUIDomainNamePage();
	}

	public CSMUIZoneManagerPage clickZoneManagerTab() {

		System.out.println("Clicking Zone Manager tab");
		zoneManagerTab.click();
		return new CSMUIZoneManagerPage();
	}

	public CSMUIDelegateDomainPage clickDelegateDomainTab() {

		System.out.println("Clicking Domain Delegation tab");
		delegateDomainTab.click();
		return new CSMUIDelegateDomainPage();
	}

	public CSMUIDelegateRedirectPage clickDelegateRedirectorTab() {

		System.out.println("Clicking Domain Redirector tab");
		delegateRedirectorTab.click();
		return new CSMUIDelegateRedirectPage();
	}

	public CSMUIManageO365MainPage clickOffice365Tab() {

		System.out.println("Clicking Office 365 tab");
		office365Tab.click();
		return new CSMUIManageO365MainPage();
	}

}
