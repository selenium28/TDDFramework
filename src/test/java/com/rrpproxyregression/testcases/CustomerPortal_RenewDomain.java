package com.rrpproxyregression.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.relevantcodes.extentreports.LogStatus;
import com.rrpproxypage.java.RRPDomainsPage;
import com.rrpproxypage.java.RRPMyDomainsPage;
import com.rrpproxypage.java.RRPProxyLoginPage;
import com.rrpproxypage.java.RRPTabPage;
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPHostingAndExtrasPage;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppcustomerportal.pages.TPPOrderPage;
import com.tppcustomerportal.pages.TPPRegistrantContactPage;
import com.tppcustomerportal.pages.TPPRenewDomainPage;
import com.tppcustomerportal.pages.TPPSummaryOfAllDomainsPage;

public class CustomerPortal_RenewDomain extends TestBase {

	// Console pages
	TPPLoginPage tppLoginPage;
	TPPDomainSearchPage tppDomainSearchPage;
	TPPHeaderPage tppHeaderPage;
	TPPSummaryOfAllDomainsPage tppSummaryOfAllDomainsPage;
	TPPOrderPage tppOrderPage;
	TPPHostingAndExtrasPage tpphostingandextraspage;
	TPPRenewDomainPage tppRenewDomainPage;
	TPPAccountContactPage tppaccountcontactpage;
	TPPRegistrantContactPage tppregistrantcontactpage;
	TPPBillingPage tppbillingpage;
	TPPOrderCompletePage tppordercompletepage;

	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	RRPProxyLoginPage rrpproxyloginpage;
	RRPDomainsPage rrpdomainspage;
	RRPTabPage rrptabpage;
	RRPMyDomainsPage rrpmydomainspage;
	CADomainLevelPage cadomainlevelpage;

	// Variables
	String expiryDateBeforeRenewal;
	String existingPaymentMethod;
	String renewedDomainName;
	String rrpStrUsername;
	String rrpStrPassword;
	String strDomainName;
	String strWorkflowId;
	String renewedWorkflowId;
	String strWorkflowStatus;

	boolean flag = true;

	public CustomerPortal_RenewDomain() {
		super();
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void registerADomainInCustomerPortal(String environment, String namespace, String accountReference)
			throws Exception {

		String strAccountReference = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: registerADomainInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails("ARQ-46", "comein22");
		tppHeaderPage = tppLoginPage.clickLoginButton();
		tppOrderPage = tppHeaderPage.clickOrderTab();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to Domains then search ans register a domain");
		tppOrderPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppDomainSearchPage = tppOrderPage.clickNewDomainSearchButton();
		tpphostingandextraspage = tppDomainSearchPage.clickContinueToCheckoutWithoutDomainPrivacy();
		tppregistrantcontactpage = tpphostingandextraspage.clickContinueButtonWithoutAccountContact();
		tppbillingpage = tppregistrantcontactpage.clickContinueButton();

		// Test Step 3: Select existing credit card details and submit the order
		tppbillingpage.selectExistingCreditCardOption("Number: 4111********1111 Expiry: 08/2021");
		tppbillingpage.tickTermsAndConditions();
		tppordercompletepage = tppbillingpage.clickContinueButton();

		// Test Step 4: Verify if order is completed and get the Order Reference ID
		Assert.assertTrue(tppordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = tppordercompletepage.getSingleReferenceID();
		strAccountReference = tppordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);
		System.out.println("Reference ID[0]:" + strWorkflowId);
		driver.close();

		// Test Step 5: Process the domain registration order in console admin
		test.log(LogStatus.INFO, "Process the domain registration order in console admin");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, namespace);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + namespace);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
				|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

		driver.quit();
		System.out.println("End Test: registerADomainInCustomerPortal");

	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test(dependsOnMethods = { "registerADomainInCustomerPortal" })
	public void renewADomainInCustomerPortal(String environment, String namespace, String accountReference)
			throws Exception {

		// Test Step 1: Login to customer portal
		test.log(LogStatus.INFO, "Login to Customer portal");
		System.out.println("Start Test: renewADomainInCustomerPortal");
		initialization(environment, "customerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails("ARQ-46", "comein22");
		tppHeaderPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to order page to register domain
		test.log(LogStatus.INFO, "Navigate to all Domains then renew the domain");
		tppSummaryOfAllDomainsPage = tppHeaderPage.clickAllDomainsLink();
		tppSummaryOfAllDomainsPage.tickDomainNameCheckbox(strDomainName + "." + namespace);
		tppRenewDomainPage = tppSummaryOfAllDomainsPage.clickRenewSelectedButton();

		// Test Step 3: Select existing credit card details and submit the order
		test.log(LogStatus.INFO, "Select existing credit card and submit the order");
		tppRenewDomainPage.tickExistingPaymentMethod();
		tppRenewDomainPage
				.selectExistingCard("Number: 4111********1111 Expiry: 08/2021");
		tppRenewDomainPage.tickTermsAndConditions();
		tppRenewDomainPage.clickCompleteOrder();

		// Test Step 4: Get order ID
		test.log(LogStatus.INFO, "Verify if order is completed and get the order ID if it is");
		Assert.assertTrue(
				tppRenewDomainPage.isOrderComplete("Renewal job has successsfully been lodged"),
				"Order is not completed");
		renewedWorkflowId = tppRenewDomainPage.getOrderID();
		System.out.println("Reference ID:" + renewedWorkflowId);
		driver.quit();

		// Test Step 5: Process the domain renewal workflow in console admin
		test.log(LogStatus.INFO, "Process the domain registration order in console admin");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		caworkflowadminpage.clickOnWorkflowId();
		caworkflowadminpage.processRenewal2Workflow();
	

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(renewedWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("renewal2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("renewed"));
		driver.quit();
		System.out.println("End Test: renewADomainInCustomerPortal");

	}

}
