package com.tppcustomerportal.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;

public class TPPSummaryOfAllDomainsPage extends TestBase {

	// Objects
	@FindBy(how = How.NAME, using = "getMultiRenewal")
	WebElement renewSelectedButton;

	public TPPSummaryOfAllDomainsPage() {
		PageFactory.initElements(driver, this);
	}

	public void tickDomainNameCheckbox(String domainNameWithTld) throws Exception {

		System.out.println("Get the list of domains.");
		List<WebElement> domainRows = driver.findElements(By.cssSelector("#alldomains-table tbody tr"));

		for (WebElement domain : domainRows) {

			String strDomainName = domain.findElement(By.className("domain-wrap")).getText();
			System.out.println("Found domain name: " + strDomainName);
			
			if (strDomainName.equalsIgnoreCase(domainNameWithTld)) {

				System.out.println("Found the domain. Tick the checkbox of the domain.");
				domain.findElement(By.name("domainIdsToRenew")).click();
				return;
			}

		}
		throw new Exception("Domain not found");
	}

	public TPPRenewDomainPage clickRenewSelectedButton() {

		System.out.println("Click Renew Selected button.");
		renewSelectedButton.click();
		return new TPPRenewDomainPage();
	}

}
