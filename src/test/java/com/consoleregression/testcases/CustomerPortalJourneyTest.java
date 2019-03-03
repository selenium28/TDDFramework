package com.consoleregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTLoginPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
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
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public CustomerPortalJourneyTest() {
		super();
	}

	@Parameters({"environment"})
	@Test(priority=5, enabled = true)
	public void verifyDomainRegistrationOrderForNewBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
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
		nrgbillingpage.setBTFormCreditCardDetails("Test Console Regression", "4111111111111111", "11", "2019", "123");
        nrgbillingpage.tickTermsAndConditions();
        nrgordercompletepage = nrgbillingpage.clickContinueButton();
        
        driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=6, enabled = true)
	public void verifyDomainandMultipleProductOrderForReturningQuestCustomerUsingExistingCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = null;
		String strTld = null;
		String strWorkflowId = null;
				
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
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
		nrgbillingpage.selectExistingCreditCardOption("Visa");	
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		
		driver.close();
	}
	
	
	@Parameters({"environment"})
	@Test(priority=7, enabled = true)
	public void verifyDomainandMultipleProductOrderForReturningBTCustomerUsingExistingCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strAccountReference = null;
		String strTld = null;
		String strWorkflowId = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
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
		nrgaccountcontactpage.setReturningCustomerContacts("TES-2168", "comein22");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCardOption("Visa");	
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		
		driver.close();
				
	}
	
	@Parameters({"environment"})
	@Test(priority=8, enabled = true)
	public void verifyDomainandMultipleProductOrderForReturningQuestCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
				
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
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
		
		//Test Step 2: Input credit card details and submit the order 
		nrgbillingpage.selectNewCreditCardOption();
		
		strCardOwnerName = "Quest Returning Customer";
		strCardType = "Visa";
		strCardNumber = "4005519200000004";
	    strCardExpiryMonth = "08";
	    strCardExpiryYear = "2025";
	    strCardSecurityCode = "811";	
	    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();

		driver.close();
	}
	
	@Parameters({"environment"})
	@Test(priority=9, enabled = true)
	public void verifyDomainandMultipleProductOrderForReturningBTCustomerUsingNewCardInCustomerPortal(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		String strCardOwnerName = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
				
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		
		if (environment.equals("uat1")) {
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
		nrgaccountcontactpage.setReturningCustomerContacts("TES-2168", "comein22");
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		nrgbillingpage.selectNewCreditCardOption();
		
		strCardOwnerName = "Braintree Returning Customer";
		strCardNumber = "5555555555554444";
	    strCardExpiryMonth = "05";
	    strCardExpiryYear = "2028";
	    strCardSecurityCode = "331";
	    nrgbillingpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
	    nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
	    
	    driver.close();
	}
	
}
