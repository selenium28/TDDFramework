package com.rrpproxyregression.testcases;

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
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.util.TestUtil;

public class ResellerPortal_RegisterDomain extends TestBase {

	// Reseller portal [ages
	TPPLoginPage tppLoginPage;
	TPPTabPage tppTabPage;
	TPPRegisterADomainPage tppRegisterADomainPage;

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;

	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public ResellerPortal_RegisterDomain() {
		super();
	}

	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyDomainRegistrationInResellerPortal(String environment, String namespace, String accountReference)
			throws Exception {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strWorkflowId = null;
		String strWorkflowStatus = null;

		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);

		// Test Step 1: Login to reseller portal
		test.log(LogStatus.INFO, "Login to Reseller portal");
		System.out.println("Start Test: verifyDomainRegistrationInResellerPortal");
		initialization(environment, "resellerportalurl_tpp");
		tppLoginPage = new TPPLoginPage();
		tppLoginPage.setLoginDetails(accountReference, "comein22");
		tppTabPage = tppLoginPage.clickLoginButton();

		// Test Step 2: Navigate to Register Domain
		test.log(LogStatus.INFO, "Navigate to Domains then Register and search for a domain");
		tppTabPage.clickDomainsTab();
		tppRegisterADomainPage = tppTabPage.clickRegisterLink();

		// Test Step 3: Register a domain
		test.log(LogStatus.INFO, "Verify search result message");
		tppRegisterADomainPage.setDomainNameAndTld(strDomainName, "." + namespace);
		tppRegisterADomainPage.selectExistingCustomer();
		tppRegisterADomainPage.selectRegistranContact("James Cooper");

		switch (namespace) {
		case "com.au":
		case "net.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Company");
			break;
		case "org.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Non-profit Organisation");
			break;
		case "id.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForIdAu("ARQ GROUP WHOLESALE PTY LTD","Citizen/Resident");
			break;
		case "asn.au":
			System.out.println("This is the namespace " + namespace + ". Eligibility details is requied.");
			tppRegisterADomainPage.provideEligibilityDetailsForAu("ABN","54 109 565 095","ARQ GROUP WHOLESALE PTY LTD","Incorporated Association");
			break;
		default:
			System.out.println("Au eligibility is not required for this namespace");
		}

		tppRegisterADomainPage.tickNameServerOptions("Choose your nameservers");
		tppRegisterADomainPage.inputNameServerFields("ns1.partnerconsole.net", "ns2.partnerconsole.net");
		tppRegisterADomainPage.tickTermsAndConditions();
		tppRegisterADomainPage.clickRegisterDomainButton();

		// Test Step 4: Get the Order Reference ID
		test.log(LogStatus.INFO, "Verify if order is completed and get the reference ID if it is");
		Assert.assertTrue(tppRegisterADomainPage.isOrderComplete(
				"domain order for " + strDomainName + "." + namespace + " created."), "Order is not completed");
		strWorkflowId = tppRegisterADomainPage.getSingleReferenceID();
		System.out.println("Reference ID:" + strWorkflowId);
		driver.quit();

		// Test Step 5: Process the domain registration order in console admin
		test.log(LogStatus.INFO, "Process the domain registration order in console admin");
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, namespace);

		// Test Step 6: Verify if domain registration workflow is completed
		test.log(LogStatus.INFO, "Verify if domain registration workflow is completed");
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		strWorkflowStatus = caworkflowadminpage.getWorkflowStatus("domainregistration2");
		Assert.assertTrue(strWorkflowStatus.equalsIgnoreCase("domain registration completed")
				|| strWorkflowStatus.equalsIgnoreCase("update star rating"));

		driver.quit();
		System.out.println("End Test: verifyDomainRegistrationInResellerPortal");

	}

}
