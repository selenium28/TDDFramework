package com.tppcustomerportal.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPDomainSearchPage extends TestBase {

	// Objects

	@FindBy(how = How.XPATH, using = "//div[@class='orderBoxWrapper domainSearchTable']//table//tbody/tr[2]/td[2]")
	WebElement domainStatus;

	// Initializing Page Objects
	public TPPDomainSearchPage() {
		PageFactory.initElements(driver, this);
	}
	// Methods

	public Boolean checkStatus() throws InterruptedException {
		Boolean flag = false;
		Thread.sleep(1000);
		if (domainStatus.isDisplayed() || domainStatus.isEnabled()) {
			System.out.println(domainStatus.getText());
			flag = true;

		} else {
			System.out.println("element not found");
		}

		return flag;
	}
}
