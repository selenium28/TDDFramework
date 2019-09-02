package com.auprojectregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGEligibilityDetailsPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;


public class TestAUDomainRegistrationForNetregistryInOldCart extends TestBase{
	
	
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;	
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGEligibilityDetailsPage nrgeligibilitydetailspage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	public TestAUDomainRegistrationForNetregistryInOldCart() {
		super();
	}
	
	
	@Parameters({"environment"})
	@Test
	public void testAUDomainRegistrationForNetregistryInOldCart(String environment) throws InterruptedException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strAccountReference = null;
		String strPassword = null;
		String strEligibilityIDType = null;
		String strEligibilityIDNumber = null;
		String strCompanyName = null;
		String strEligibilityType = null;
		String strWorkflowId = null;
		
		
		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if ((environment.equals("dev2"))) {
			strAccountReference = "MEL-6007";
			strPassword = "comein22";
			strTld = "au";
			strEligibilityIDType = "ABN";
			strEligibilityIDNumber = "13080859721";
			strCompanyName = "Netregistry";
			strEligibilityType = "Company";
		}
			
		//Test Step 1: Login to customer portal and place a domain registration order for .au tld
		System.out.println("Start Test: testAUDomainRegistrationForNetregistryInOldCart");
		initialization(environment, "oldcart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, "." + strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		
		/* below code is to add elegibility details page to .au domains
		
		nrgeligibilitydetailspage = nrgregistrantcontactpage.clickContinueButtonForEligibilityDetails();
		nrgeligibilitydetailspage.clickBusinessNumberButton();
		nrgeligibilitydetailspage.setEligibilityDetails(strEligibilityIDType, strEligibilityIDNumber, strCompanyName, strEligibilityType);
		nrgeligibilitydetailspage.tickCertifyDomainHasCloseAndSubstantialConnection();
		nrgeligibilitydetailspage.tickTermsAndConditions();
		
		nrgbillingpage = nrgeligibilitydetailspage.clickContinueButton();
		
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCardOption("Number: 5555********4444 Expiry: 03/2021");
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
		
		//Test Step 3: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		
		*/
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
				
		//Test Step 2: Select existing credit card details and submit the order 
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
				
		//Test Step 3: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");

		driver.close();
		System.out.println("End Test: testAUDomainRegistrationForNetregistryInOldCart");
		}
}