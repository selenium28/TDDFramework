package com.tppresellerportal.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class TPPRegisterADomainPage extends TestBase {

	// Objects

	@FindBy(how = How.ID, using = "domain")
	WebElement newDomainSearchBox;

	@FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div/span")
	WebElement searchResultsTextWithoutDomainPrivacy; 

	@FindBy(how = How.XPATH, using = "//*[@id=\"availability\"]/div[2]/span")
	WebElement searchResultsTextWithDomainPrivacy;

	// Initializing Page Objects
	public TPPRegisterADomainPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setDomainNameAndTld(String domainname, String tldname) throws Exception {

		newDomainSearchBox.clear();
		newDomainSearchBox.sendKeys(domainname);
		clickDomainExtension(tldname);

	}

	public void clickDomainExtension(String strDomainExtension) throws Exception {

		Thread.sleep(2000);
		Select dropdown = new Select(driver.findElement(By.id("tld")));
		Thread.sleep(2000);
		dropdown.selectByValue(strDomainExtension);

	}

	public String getSearchResultsMessage() throws InterruptedException {

		if (driver.findElements(By.className("buyWhoisPrivacy")).size() != 0) {

			System.out.println("Return edit details success message");
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchResultsTextWithDomainPrivacy));
			return searchResultsTextWithDomainPrivacy.getText();
		}

		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(searchResultsTextWithoutDomainPrivacy));
		return searchResultsTextWithoutDomainPrivacy.getText();
	}

	public void tickNameServerOptions(String strnameserverOption) throws InterruptedException {

		Thread.sleep(3000);

		if (strnameserverOption.contentEquals("Use our nameservers")) {
			driver.findElement(By.xpath("//*[@id='nameserverOptions']/tbody/tr/td[1]/label[1]")).click();
		} else if (strnameserverOption.contentEquals("Choose your nameservers")) {
			driver.findElement(By.xpath("//*[@id='nameserverOptions']/tbody/tr/td[1]/label[2]")).click();
		}
		System.out.println("Tick nameserver option");

	}

	public void clearNameServerFields() throws InterruptedException {

		Thread.sleep(3000);

		for (int i = 0; i < 4; i++) {
			driver.findElement(By.name("nameservers[" + i + "]")).clear();
		}

	}

	public void inputNameServerFields(String[] strNameServers) throws InterruptedException {

		Thread.sleep(2000);

		clearNameServerFields();

		Thread.sleep(2000);
		for (int i = 0; i < 4; i++) {
			driver.findElement(By.name("nameservers[" + i + "]")).sendKeys(strNameServers[i]);
		}

	}

}
