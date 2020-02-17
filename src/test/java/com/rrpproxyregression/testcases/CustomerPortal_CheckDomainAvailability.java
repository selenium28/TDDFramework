package com.rrpproxyregression.testcases;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.util.TestUtil;

public class CustomerPortal_CheckDomainAvailability extends TestBase{
	
	TPPLoginPage tppLoginPage;
	TPPDomainSearchPage tppDomainSearchPage;
	TPPHeaderPage tppOverviewPage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	
	public CustomerPortal_CheckDomainAvailability() {
		super();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	
	public void verifyDomainNameAvailableForRegistrationNotPremium(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = "com";

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppOverviewPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to overview page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppOverviewPage.setDomainNameAndTld(strDomainName, "." + strTld);
		/*tppRegisterADomainPage = tppTabPage.clickRegisterLink();
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + namespace);

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertEquals(tppRegisterADomainPage.getSearchResultsMessage(),
				"The domain " + strDomainName + "." + namespace + " is available for registration",
				"Checking if domain is avilable");
		System.out.println("End Test: verifyDomainRegistrationOrderForNewCustomerInCustomerPortal");
		driver.quit();*/

	}
	
	/*@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyDomainNameNotAvailableForRegistration(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		strDomainName = "netregistry";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainNameNotAvailableForRegistration");
		initialization(environment, "resellerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppTabPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppTabPage.clickDomainsTab();
		tppRegisterADomainPage = tppTabPage.clickRegisterLink();
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + namespace);

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertEquals(tppRegisterADomainPage.getSearchResultsMessage(),
				"The domain " + strDomainName + "." + namespace + " has already been registered",
				"Checking if domain is not available");
		System.out.println("End Test: verifyDomainNameNotAvailableForRegistration");
		driver.quit();

	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyDomainNameAvailableForRegistrationAndPremiumn(String environment, String namespace,
			String accountReference) throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		
		strDomainName = "lol";
		strTld = "sydney";

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainNameAvailableForRegistrationAndPremiumn");
		initialization(environment, "resellerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppTabPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppTabPage.clickDomainsTab();
		tppRegisterADomainPage = tppTabPage.clickRegisterLink();
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + strTld);

		// Test Step 3: Verify search result message
		test.log(LogStatus.INFO, "Verify search result message");
		Assert.assertEquals(tppRegisterADomainPage.getSearchResultsMessage(),
				"The domain " + strDomainName + "." + strTld + " is a premium domain. Please contact us for pricing",
				"Checking if domain is available and premium");
		System.out.println("End Test: verifyDomainNameAvailableForRegistrationAndPremiumn");
		driver.quit();

	}*/

}
