package com.consoleregression.testcases.production;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class CustomerPortalJourneyTest extends TestBase{

	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CustomerPortalJourneyTest() {
		super();
	}

	@Parameters({"environment"})
	@Test
	public void verifyDomainRegistrationOrderForNewCustomerInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;

		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("prod")) {
			strTld = ".com";
		}
			
		//Test Step 1: Login to customer portal and place an order for domain registration 
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();	
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		nrgbillingpage.setBTFormCreditCardDetails("MELBOURNE IT LTD F SHARED SERVICES", "4715276659101053", "08", "2020", "390");
		nrgbillingpage.tickTermsAndConditions();
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        //Test Step 3: Verify if recaptcha challenge is dislayed 
      	Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
        
        // driver.close();
	}
	
	@Parameters({"environment"})
	@Test
	public void verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = null;
		String strTld = null;
		String strWorkflowId = null;

		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("prod")) {
			strTld = ".com";
		}
			
		//Test Step 1: Login to customer portal and place an order for domain registration and domain privacy
		System.out.println("Start Test: verifyDomainandMultipleProductOrderForReturningCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickAddToCart();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts("MEL-6007", "comein22");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCardOption("Number: 4715********1053 Expiry: 08/2020");	
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		//Test Step 3: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		//driver.close();
	}
	
}
