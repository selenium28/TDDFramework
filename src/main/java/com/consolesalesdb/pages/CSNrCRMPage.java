package com.consolesalesdb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

public class CSNrCRMPage extends TestBase {

	// Objects
	@FindBy(how = How.ID, using = "Greencode-searchbox")
	WebElement greenCode;

	@FindBy(how = How.XPATH, using = "//div[@id='Domain Details']/div/div[1]/div/table/tbody/tr/td[3]/table/tbody/tr/td[2]/em/button")
	WebElement newDomainNPS;

	@FindBy(how = How.ID, using = "eligibility-form-name-field-0")
	WebElement registrantName;

	// Initializing Page Objects
	public CSNrCRMPage() {
		PageFactory.initElements(driver, this);
	}

	// Methods
	public void setGreenCode(String strGreencode) throws InterruptedException {
		// Thread.sleep(3000);
		Thread.sleep(1000);
		greenCode.sendKeys(strGreencode);
		// Thread.sleep(2000);
		greenCode.sendKeys(Keys.ENTER);
	}

	public CSWorkflowNotificationPage clickConfirmDomain(String strDomainName) throws InterruptedException {
		Thread.sleep(3000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(
				By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='" + strDomainName + "']")))
				.doubleClick().build().perform();
		driver.findElement(
				By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Confirm Domain (via workflow)')]")).click();
		Thread.sleep(2000);
		return new CSWorkflowNotificationPage();
	}

	public CSUpgradeServiceWindowPage clickUpgradeHosting(String strDomainName) throws InterruptedException {
		Thread.sleep(3000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(
				By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='" + strDomainName + "']")))
				.doubleClick().build().perform();
		driver.findElement(
				By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Upgrade Hosting (via workflow)')]"))
				.click();
		Thread.sleep(2000);
		return new CSUpgradeServiceWindowPage();
	}

	public CSShowDomainServicesPage clickShowDomainServices(String strDomainName) throws InterruptedException {
		String strDomainNameLowerCase = strDomainName.toLowerCase();

		//Thread.sleep(3000);
		Thread.sleep(1000);
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(
				By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='" + strDomainNameLowerCase + "']")))
				.doubleClick().build().perform();
		driver.findElement(By.xpath("//li/a[contains(text(),'(New Pricing Structure) : Show Domain Services')]"))
				.click();
		//Thread.sleep(2000);
		Thread.sleep(1000);
		return new CSShowDomainServicesPage();
	}

	public CSRegistrantDetailsPage clickRegistrantDetails(String strDomainName, String strRegistrantDetails)
			throws InterruptedException {
		String strDomainNameLowerCase = strDomainName.toLowerCase();

		//Thread.sleep(10000);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='" + strDomainNameLowerCase
				+ "']/parent::td/parent::tr/td[11]/div/a/b[text()='" + strRegistrantDetails + "']")).click();
		Thread.sleep(2000);
		return new CSRegistrantDetailsPage();
	}

	public CSAUEligibilityPage clickUpdateDetails(String strDomainName, String strRegistrantDetails)
			throws InterruptedException {
		String strDomainNameLowerCase = strDomainName.toLowerCase();

		//Thread.sleep(10000);
		driver.findElement(By.xpath("//div[@class='x-grid3-cell-inner x-grid3-col-1'][text()='" + strDomainNameLowerCase
				+ "']/parent::td/parent::tr/td[11]/div/a/b[text()='" + strRegistrantDetails + "']")).click();
		//Thread.sleep(2000);

		return new CSAUEligibilityPage();

	}

	public CSCreateDomainWindowPage clickNewDomainNPSButton() throws InterruptedException {
		// Thread.sleep(20000);
		Thread.sleep(5000);
		System.out.println("searching for new domain (new price system) button");
		if (newDomainNPS.isDisplayed() || newDomainNPS.isEnabled()) {
			newDomainNPS.click();
			System.out.println("clicked new domain (new price system) button");
		} else {
			System.out.println("element not found");
		}
		// Thread.sleep(2000);
		new WebDriverWait(driver, 20)
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".x-resizable-pinned"))));
		return new CSCreateDomainWindowPage();
	}

	public String getBillingAccount() throws InterruptedException {

		String billingAccountDetails;
		Thread.sleep(10000);
		billingAccountDetails = driver.findElement(By.xpath("//table[@class='x-grid3-row-table']/tbody/tr/td[10]/div"))
				.getText();

		return billingAccountDetails;
	}

}
