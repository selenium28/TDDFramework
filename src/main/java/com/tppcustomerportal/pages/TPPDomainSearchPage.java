package com.tppcustomerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPDomainSearchPage extends TestBase {

	// Objects

	@FindBy(how = How.XPATH, using = "//div[@class='orderBoxWrapper domainSearchTable']//table//tbody/tr[2]/td[2]")
	WebElement domainStatus;

	@FindBy(how = How.ID, using = "continueCart")
	WebElement continueToCheckoutButton;

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

	public TPPHostingAndExtrasPage clickContinueToCheckoutWithoutDomainPrivacy() {

		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
		System.out.println("clicking continue to checkout");
		if (continueToCheckoutButton.isDisplayed() || continueToCheckoutButton.isEnabled()) {
			continueToCheckoutButton.click();
		} else {
			System.out.println("element not found");
		}
		return new TPPHostingAndExtrasPage();
	}

	public TPPAddDomainPrivacyPage clickContinueToCheckoutWithDomainPrivacy() {

		new WebDriverWait(driver, 30)
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#shopping-cart .order")));
		System.out.println("clicking continue to checkout");
		if (continueToCheckoutButton.isDisplayed() || continueToCheckoutButton.isEnabled()) {
			continueToCheckoutButton.click();
		} else {
			System.out.println("element not found");
		}
		return new TPPAddDomainPrivacyPage();
	}
}
