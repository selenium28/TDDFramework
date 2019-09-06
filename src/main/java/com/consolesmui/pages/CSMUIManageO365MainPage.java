package com.consolesmui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class CSMUIManageO365MainPage extends TestBase {

	// Objects
	@FindBy(how = How.CSS, using = ".advance_portal td:nth-of-type(1)")
	WebElement adminEmailAddress;

	@FindBy(how = How.CSS, using = ".advance_portal a[href^=\"#\"]")
	WebElement adminUpdateLink;

	@FindBy(how = How.CSS, using = ".advance_portal a[href*=\"portal\"]")
	WebElement adminLoginLink;

	// Initializing Page Objects
	public CSMUIManageO365MainPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyAdminEmail(String domainName) {
		
		System.out.println("Verify admin email address");
		return adminEmailAddress.getText().equals("o365-admin@" + domainName);
	}

	public CSMUIUpdateAdminAccountPage clickAdminUpdateLink() throws InterruptedException {

		System.out.println("Click update link");
		adminUpdateLink.click();
		Thread.sleep(3000);
		return new CSMUIUpdateAdminAccountPage();
	}

	public void clickAdminLoginLink() {

		System.out.println("Click login link");
		adminLoginLink.click();
	}

}
