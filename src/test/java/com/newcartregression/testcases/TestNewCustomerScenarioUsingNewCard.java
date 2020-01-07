package com.newcartregression.testcases;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.base.Environment;
import com.base.TestBase;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOffice365LicenseQuantityPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.netregistrynewwebsite.pages.NRGNSSearchFieldPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestNewCustomerScenarioUsingNewCard extends TestBase{
	
	//Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSOffice365LicenseQuantityPage nrgnsoffice365licensequantitypage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	NRGNSSearchFieldPage nrgnssearchfieldpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	
	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;
	
	//SoftAssert softassert;
	
	public TestNewCustomerScenarioUsingNewCard() {
		super();
	}
			
	/*
	 * @BeforeMethod public void registerMethod(Method method) {
	 * test=report.startTest(method.getName()); test.log(LogStatus.INFO, "Test"
	 * +method.getName()+" has been started"); }
	 */
	
	@Parameters({"environment", "iteration"})
	@Test
	public void testNewCustomerScenarioUsingNewCard (String environment, Integer iteration) throws Exception{
	//	softassert=new SoftAssert();
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		String strCardOwnerName = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
				
	    Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {
	    
		    //Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestNewCartRegression" + df.format(d);
			
			if (environment.equals("dev2")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			if (environment.equals("uat1")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			else if (environment.equals("ote")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			else if (environment.equals("prod")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			
			System.out.println("Start Test: testNewCustomerScenarioUsingNewCard");
					
			//Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			
		/*	boolean b=false;
			try {
				 b=driver.findElement(By.xpath("//div[@class='privacy-domains']/div")).isDisplayed();
				if(b) {
					test.log(LogStatus.PASS, "Navigate to domain search page -PASSED");
				}else {
					test.log(LogStatus.FAIL, "Navigate to domain search page -FAILED");
				}
			}catch(Exception e) {
				test.log(LogStatus.FAIL, "Navigate to domain search page -FAILED");
			}
			softassert.assertTrue(b, "Navigate to domain search page  -FAILED");
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");  */
			
			//Test Step 2: Process the order without any product included
			test.log(LogStatus.INFO, "Process the order page -STARTED");
			
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			
		/*	boolean c=false;
			try {
				 c=driver.findElement(By.xpath("//button[@class='btn green']")).isDisplayed();
				if(c) {
					test.log(LogStatus.PASS, "Process the order page -PASSED");
				}else {
					test.log(LogStatus.FAIL, "Process the order page -FAILED");
				}
			}catch(Exception e) {
				test.log(LogStatus.FAIL, "Process the order page -FAILED");
			}
			softassert.assertTrue(b, "Process the order page  -FAILED");
			test.log(LogStatus.INFO, "Process the order page -COMPLETED"); */
			
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			
			
			
			
			//Test Step 3: Input default customer details 
			test.log(LogStatus.INFO, "Input default customer details -STARTED");
			
			nrgnsaboutyoupage.setDefaultBusinessCustomerDetails();
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickContinueButton();
			nrgnsregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			
		/*	boolean f=false;
			try {
				 f=driver.findElement(By.xpath("//div[@class='contact-list cell-content']/div[1]/div[2]/div")).isDisplayed();
				if(f) {
					test.log(LogStatus.PASS, "Input default customer details -PASSED");
				}else {
					test.log(LogStatus.FAIL, "Input default customer details -FAILED");
				}
			}catch(Exception e) {
				test.log(LogStatus.FAIL, "Input default customer details -FAILED");
			}
			softassert.assertTrue(f, "Input default customer details  -FAILED");
			test.log(LogStatus.INFO, "Input default customer details -COMPLETED");  */
			
			
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			
			
			
			//Test Step 4: Input customer credit card details and complete the order
			test.log(LogStatus.INFO, "Input customer credit card details  -STARTED");
			
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			
		/*	boolean g=false;
			try {
				 g=driver.findElement(By.xpath("//div[@class='continue-btn-container']/button[@class='btn green']")).isDisplayed();
				if(g) {
					test.log(LogStatus.PASS, "Input customer credit card details -PASSED");
				}else {
					test.log(LogStatus.FAIL, "Input customer credit card details -FAILED");
				}
			}catch(Exception e) {
				test.log(LogStatus.FAIL, "Input customer credit card details -FAILED");
			}
			softassert.assertTrue(g, "Input customer credit card details  -FAILED");
			test.log(LogStatus.INFO, "Input customer credit card details -COMPLETED");  */
			
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			
			//Test Step 5: Verify if recaptcha challenge is dislayed 
			test.log(LogStatus.INFO, "Verify if recaptcha challenge is dislayed  -STARTED");
							
		/*	boolean h=false;
			try {
				 h=driver.findElement(By.xpath("//iframe[@title='recaptcha challenge']")).isDisplayed();
				if(h) {
					test.log(LogStatus.PASS, "Verify if recaptcha challenge is dislayed -PASSED");
				}else {
					test.log(LogStatus.FAIL, "Verify if recaptcha challenge is dislayed -FAILED");
				}
			}catch(Exception e) {
				test.log(LogStatus.FAIL, "Verify if recaptcha challenge is dislayed -FAILED");
			}
			softassert.assertTrue(h, "Verify if recaptcha challenge is dislayed  -FAILED");
			test.log(LogStatus.INFO, "Verify if recaptcha challenge is dislayed -COMPLETED");  */
			
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			driver.close();
		//	softassert.assertAll();
			System.out.println("End Test: testNewCustomerScenarioUsingNewCard");
		}	
	}
	
	
}
