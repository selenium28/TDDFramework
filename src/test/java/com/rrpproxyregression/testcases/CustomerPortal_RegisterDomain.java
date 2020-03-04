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
import com.tppcustomerportal.pages.TPPAccountContactPage;
import com.tppcustomerportal.pages.TPPAddExtrasPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHostingAndExtrasPage;
import com.tppcustomerportal.pages.TPPLoginPage;
import com.tppcustomerportal.pages.TPPOnlineOrderPage;
import com.tppcustomerportal.pages.TPPOrderCompletePage;
import com.tppcustomerportal.pages.TPPRegistrantContactPage;
import com.util.TestUtil;

public class CustomerPortal_RegisterDomain extends TestBase {

	
	TPPOnlineOrderPage tpponlineorderpage;
	TPPDomainSearchPage tppdomainsearchpage;	
	TPPLoginPage tpploginpage;
	/*TPPAddDomainPrivacyPage tppadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGWebHostingPage nrgwebhostingpage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;*/
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	public CustomerPortal_RegisterDomain() {
		super();
	}
	
	@Parameters({ "environment", "namespace", "accountReference" })
	@Test
	public void verifyDomainRegistrationInCustomerPortal(String environment, String namespace, String accountReference)
			throws Exception {
		
		// Initialization (Test Data Creation and Assignment)
				String strDomainName = null;
				String strTld = null;
				String strAccountReference = null;
				String strPassword = null;
				String strWorkflowId = null;
				
				DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
				Date d = new Date();
				strDomainName = "TestConsoleRegression" + df.format(d);
				
				//Test Step 1: Login to customer portal and place a domain registration order for .com tld
				System.out.println("Start Test: testDomainRegistration2WorkflowForCom");
				initialization(environment, "customerportalurl_tpp");
				
				tpploginpage = new TPPLoginPage();
				tpploginpage.setLoginDetails(accountReference, "comein22");
				
		/*
		 * tpponlineorderpage = new TPPOnlineOrderPage();
		 * tpponlineorderpage.clearDefaultTldSelections();
		 * tpponlineorderpage.setDomainNameAndTld(strDomainName, "." + strTld);
		 */
}
}
