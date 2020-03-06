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
import com.tppcustomerportal.pages.TPPAddDomainPrivacyPage;
import com.tppcustomerportal.pages.TPPAddExtrasPage;
import com.tppcustomerportal.pages.TPPBillingPage;
import com.tppcustomerportal.pages.TPPDomainSearchPage;
import com.tppcustomerportal.pages.TPPHeaderPage;
import com.tppcustomerportal.pages.TPPOrderPage;
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
	TPPHeaderPage tppheaderpage;
	TPPOrderPage tpporderpage;
	TPPAddDomainPrivacyPage tppadddomainprivacypage;
	TPPHostingAndExtrasPage tpphostingandextraspage;
	TPPAccountContactPage tppaccountcontactpage;
	TPPRegistrantContactPage tppregistrantcontactpage;
	TPPBillingPage tppbillingpage;
	TPPOrderCompletePage tppordercompletepage;
	/*NRGWebHostingPage nrgwebhostingpage;
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
				tpploginpage.setLoginDetails("ARQ-69", "comein22");
				tppheaderpage = tpploginpage.clickLoginButton();
				tpporderpage = tppheaderpage.clickOrderTab();
				tpporderpage.setDomainNameAndTld(strDomainName, "." + namespace);
				tppdomainsearchpage = tpporderpage.clickNewDomainSearchButton();
				tppadddomainprivacypage = tppdomainsearchpage.clickContinueToCheckoutWithoutDomainPrivacy();
				tpphostingandextraspage = tppadddomainprivacypage.clickNoThanks();
				tppregistrantcontactpage = tpphostingandextraspage.clickContinueButtonWithoutAccountContactPage();
				tppbillingpage = tppregistrantcontactpage.clickContinueButton();
				
				//Test Step 2: Select existing credit card details and submit the order 
				tppbillingpage.selectExistingCreditCard("Number: 4111********1111 Expiry: 03/2021");	
				tppbillingpage.tickTermsAndConditions();
				tppordercompletepage = tppbillingpage.clickContinueButton();
				
				//Test Step 3: Verify if order is completed
				Assert.assertTrue(tppordercompletepage.isOrderComplete(), "Order is not completed");
				strWorkflowId = tppordercompletepage.getSingleReferenceID();
				strAccountReference = tppordercompletepage.getAccountReferenceID();
				System.out.println("Account Reference:" + strAccountReference);	
				System.out.println("Reference ID[0]:" + strWorkflowId);	
				driver.close();
				
				//Test Step 4: Process the domain registration order in console admin
				initialization(environment, "consoleadmin");
				caloginpage = new CALoginPage();
				caheaderpage = caloginpage.setDefaultLoginDetails(environment);
				caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
				caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, namespace);
				//caworkflowadminpage.processFraudCheck();
				
				//Test Step 5: Verify if domain registration workflow is completed
				caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
				Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
				
				driver.close();
				System.out.println("End Test: testDomainRegistration2WorkflowForCom");
						
			
		
}
}
