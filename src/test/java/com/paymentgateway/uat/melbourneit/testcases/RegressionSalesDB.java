package com.paymentgateway.uat.melbourneit.testcases;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CAInvoicesPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CATaxInvoicePage;
import com.consoleadmin.pages.CAViewCreditCardsPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSAccountPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSProcessTransactionPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionSalesDB extends TestBase{
			
		//Console Sales DB Pages
		CSLoginPage csloginpage;
		CSNrCRMPage csnrcrmpage;
		CSCreateDomainWindowPage cscreatedomainwindowpage;
		CSRegistrantDetailsPage csregistrantdetailspage;
		CSShowDomainServicesPage csshowdomainservicespage;
		CSWorkflowNotificationPage csworkflownotificationpage;
		CSAUEligibilityPage csaueligibilitypage;
		CSAccountPage csaccountpage;
		CSProcessTransactionPage csprocesstransactionpage;
		
		//Console Admin Pages
		CALoginPage caloginpage;
		CAHeaderPage caheaderpage;
		CAWorkflowAdminPage caworkflowadminpage;
		CAAccountReferencePage caaccountreferencepage;
		CADomainLevelPage cadomainlevelpage;
		CAViewCreditCardsPage caviewcreditcardspage;
		CAInvoicesPage cainvoicespage;
		CATaxInvoicePage cataxinvoicepage;
		
		TestUtil testUtil;
		public static ExtentTest logger;
		
		public String strVirtualization = "MelbourneIT";
		public String strWorkflowId_01 = null;
		public String strDomainName_01 = null;
		public String strTld_01 = null;
		public String strAccountReference = null;
		public String strRegistrationPeriod = null;
		public String strPaymentMethod = null;
		public String strRegistrantDetails = null;
		public String strRegistrantType = null;
		public String strRegistrantNumber = null;
		public String strMajorProduct = null;
		public String strProductPeriod = null;
		public String strInvoiceNumber = null;
		public String strAmount = null;
		public String strTransactionType= null;
		public String strExpiryMonth = null;
		public String strExpiryYear =  null;

		
		public RegressionSalesDB() {
			super();
		}


		@Parameters({"environment", "paymentgateway"})
		@Test
		public void testCreateDomainAndMajorProductOrderInSalesDB(String environment, String paymentgateway) 
				throws InterruptedException, IOException{

				// Generate test name for domain
				DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
				Date d1 = new Date();
				strDomainName_01 = "testpgregression" + df.format(d1);
		
				if ((environment.equals("uat1"))&&(paymentgateway.equals("quest"))) {
					strAccountReference = "MEL-6005";
					strTld_01 = "com";
					strRegistrationPeriod = "1 x Y";
					strMajorProduct = "Basic Cloud Hosting";
					strProductPeriod = "1 x M";
					strPaymentMethod = "Visa";
					strRegistrantDetails = "Payment Gateway Test";	
					strRegistrantType = "ABN";
					strRegistrantNumber = "13080859721";
				}
				else if ((environment.equals("uat1"))&&(paymentgateway.equals("braintree"))) {
					strAccountReference = "MIT-3302";
					strTld_01 = "com";
					strRegistrationPeriod = "1 x Y";
					strMajorProduct = "Basic Cloud Hosting";
					strProductPeriod = "1 x M";
					strPaymentMethod = "Visa";
					strRegistrantDetails = "Payment Gateway Test";	
					strRegistrantType = "ABN";
					strRegistrantNumber = "13080859721";
				}
					
				//Test Step 1: Login to Sales DB page, then create an order for domain and product 
				initialization(environment, "salesdburl");
				csloginpage = new CSLoginPage();
				csloginpage.setDefaultLoginDetails(environment);
				csnrcrmpage = csloginpage.clickLoginButton();
				csnrcrmpage.setGreenCode(strAccountReference);
				cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
				cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_01, strTld_01, strRegistrationPeriod, 
						strMajorProduct, strProductPeriod, strPaymentMethod);
					
		        if (strTld_01=="com.au") {
				
					// AU Eligibility code-  Added on: 13-11-2018
					System.out.println("Method: setContactAndEligibilityDetails");
					csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName_01, "Update Details");
					csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strRegistrantType, 
						strRegistrantNumber);
				}
		        else {
		        	
					//For com, net, nz, org, info tlds
					csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_01, "Update Details");
					csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		        }	
			 
				csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_01);
				csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
				
				//Test Step 2: Verify if the services are successfully confirmed
				Assert.assertEquals(csworkflownotificationpage.getNotificationMessage(), "Services are successfully confirmed", 
						"Domain purchased successfully");
				strWorkflowId_01 = csworkflownotificationpage.getWorkflowID();
				
				TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest01");
				csworkflownotificationpage.clickOKButton();
				driver.close();
			
		}
		
		
		@Parameters({"environment", "paymentgateway", "obsidian"})
		@Test
		public void testDomainRegistration2WorkflowInConsoleAdmin(String environment, String paymentgateway, String obsidian)
				throws InterruptedException, IOException{

				//Test Step 1: Login to console admin, then process domainregistration2 workflow		
				initialization(environment, "consoleadmin");
				caloginpage = new CALoginPage();
				caheaderpage = caloginpage.setDefaultLoginDetails(environment);
				
				if (obsidian.equals("enabled")) {
					
					//Wait for workflow to be processed
					Thread.sleep(15000);
				}
				else {
					caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
					caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, strTld_01);
				}
				
				//Test Step 2: Verify if domain registration workflow status is completed
				caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
				Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", 
						caworkflowadminpage.getWorkflowStatus("domainregistration2"));		
				
				TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest02");
				}
		
		
		@Parameters({"environment", "paymentgateway", "obsidian"})
		@Test
		public void testProductSetup2WorkflowInConsoleAdmin(String environment, String paymentgateway, String obsidian) 
				throws InterruptedException, IOException{
		
				//Test Step 1: Process the productsetup2 workflow in console admin
				if (obsidian.equals("enabled")) {
				
					//Wait for workflow to be processed
					Thread.sleep(15000);
				}
				else {
					caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
					caworkflowadminpage.processProductSetup2();
				}
			
				//Test Step 2: Verify if productsetup2 workflow is approved
				caworkflowadminpage = caheaderpage.searchWorkflow(strDomainName_01 + "." + strTld_01);
				Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", 
						caworkflowadminpage.getWorkflowStatus("productsetup2"));
				
				TestUtil.takeScreenshotAtEndOfTest(paymentgateway + strVirtualization + "PGTest03");
				driver.close();
		}
		
		/* Invoice scenarios not applicable in MIT */
		
}