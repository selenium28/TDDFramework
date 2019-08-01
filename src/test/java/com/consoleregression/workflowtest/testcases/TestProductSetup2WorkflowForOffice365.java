package com.consoleregression.workflowtest.testcases;

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
import com.netregistryoldwebsite.pages.NRGOnlineOrderAccountDetailsPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.netregistryoldwebsite.pages.NRGWebHostingPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;


public class TestProductSetup2WorkflowForOffice365 extends TestBase {

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
	NRGOnlineOrderAccountDetailsPage nrgonlineorderaccountdetailspage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	TestUtil testUtil;
	public static ExtentTest logger;

	public TestProductSetup2WorkflowForOffice365() {
		super();
	}


	
	@Parameters({"environment", "virtualization"}) 
	@Test
	public void testProductSetup2WorkflowForOffice365(String environment, String virtualization) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strAccountReference = null;
		String strPassword = null;
		String strWorkflowId = null;
		String strPeriod = null;
		String strTypeOffice365 = null;

		
		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");	
		Date d = new Date();
		strDomainName = "TestConsoleRegression" + df.format(d);
		
		if ((environment.equals("uat1"))&&(virtualization.equals("netregistry"))) {
			strAccountReference = "TES-2168";
			strPassword = "comein22";
			strTld = "com";
			strPeriod = "1 Month";
			strTypeOffice365 ="o365Business";
		}
		
		//Test Step 1: Login to customer portal and place a domain registration order for .com tld
		System.out.println("Start Test: testProductSetup2WorkflowForOffice365");
		initialization(environment, "customerportalurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.clearDefaultTldSelections();
		nrgonlineorderpage.setDomainNameAndTld(strDomainName, "." + strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		
		//Test Step 2: Purchase an office 365 product
		nrghostingandextraspage.clickEmailHostingRadioButton();
		nrghostingandextraspage.selectOffice365(strTypeOffice365, strPeriod); //Method only accepts the following Office365 types: o365EmailEssentials, o365BusinessEssentials, o365BusinessPremium or o365Business products
				
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
				
	    //Test Step 3: Select existing credit card details and submit the order 
		nrgbillingpage.selectExistingCreditCardOption("Visa");	
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickContinueButton();
				
	    //Test Step 4: Verify if order is completed
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		driver.close();
				
	    //Test Step 5: Process the domain registration order in console admin
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, strTld);
		
	    //Test Step 6: Verify if domain registration workflow is completed
		caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
					
	    //Test Step 7: Process the productsetup2 workflow in console admin
		caworkflowadminpage.processProductSetup2();

	    //Test Step 8: Verify if productsetup2 workflow is approved
   	    caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
  	    Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
  	            
	    //Test Step 9: Process the productSetupOffice365 workflow in console admin
		//caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName + "." + strTld);
		//caworkflowadminpage.processProductSetup2O365();
		        
	    //Test Step 10: Verify if productSetupOffice365 workflow status is "Office365 order completed"
        Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetupOffice365"), "Office365 order completed", caworkflowadminpage.getWorkflowStatus("o365Provisioning"));
		      
		//driver.close();
		System.out.println("End Test: testProductSetup2WorkflowForOffice365");   
								
   }
	
}
