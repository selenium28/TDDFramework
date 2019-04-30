package com.consoleregression.testcases.production;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTFoundTransactionPage;
import com.braintree.pages.BTLoginPage;
import com.braintree.pages.BTMainTabPage;
import com.braintree.pages.BTTransactionsSearchPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesalesdb.pages.CSAUEligibilityPage;
import com.consolesalesdb.pages.CSCreateDomainWindowPage;
import com.consolesalesdb.pages.CSLoginPage;
import com.consolesalesdb.pages.CSNrCRMPage;
import com.consolesalesdb.pages.CSRegistrantDetailsPage;
import com.consolesalesdb.pages.CSShowDomainServicesPage;
import com.consolesalesdb.pages.CSWorkflowNotificationPage;
import com.domainzwebsite.pages.DMZAccountPage;
import com.domainzwebsite.pages.DMZHeaderPage;
import com.domainzwebsite.pages.DMZLoginPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class SalesDBJourneyTest extends TestBase{

	
	//Console Sales DB Pages
	CSLoginPage csloginpage;
	CSNrCRMPage csnrcrmpage;
	CSCreateDomainWindowPage cscreatedomainwindowpage;
	CSRegistrantDetailsPage csregistrantdetailspage;
	CSShowDomainServicesPage csshowdomainservicespage;
	CSWorkflowNotificationPage csworkflownotificationpage;
	CSAUEligibilityPage csaueligibilitypage;
	
	//Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	//Domainz Cart/Portal Pages
	DMZLoginPage dmzloginpage;
	DMZHeaderPage dmzheaderpage;
	DMZAccountPage dmzaccountpage;
	
	
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;

	public SalesDBJourneyTest() {
		super();
	}
			
	@Parameters({"environment", "pretest"})
	@Test
	public void verify_ComAuDomain_Order_InSalesDB (String environment, String pretest) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "14287125";
		String strRegistrantType = null;
		String strRegistrantNumber = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);
		
		if (environment.equals("prod")) {
			strTld = "com.au";
			strRegistrationPeriod = "2";
			strGreenCode = "MEL-6038";
			strPaymentMethod = "Resellers Default: Visa";
			strRegistrantDetails = "TPP";
			strRegistrantType = "ABN";
			strRegistrantNumber = "13080859721";
		}
		
		//Test Step 1: Login to sales db and place an order for domain registration
		if (pretest.equals("enabled")) {
		
			System.out.println("Start Test: verify_ComAuDomain_Order_InSalesDB");
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setGreenCode(strGreenCode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
			cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
			
			System.out.println("Method: assign values to aueligibilitypage");
			csaueligibilitypage = csnrcrmpage.clickUpdateDetails(strDomainName, "Update Details");
			csnrcrmpage = csaueligibilitypage.setContactAndEligibilityDetails(strRegistrantDetails, strRegistrantType, strRegistrantNumber);
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();
			System.out.println("Workflow ID: " + strWorkflowId);
			System.out.println("Domain Name: " + strDomainName + "." + strTld);
			driver.close();
		}
		else {
		
			//Test Step 2: Verify if domain registration workflow is completed
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
			driver.close();
		}
		
		System.out.println("End Test: verify_ComAuDomain_Order_InSalesDB");
		
	}
	
	@Parameters({"environment", "pretest"})
	@Test
	public void verify_NetDomain_and_DIFM_Order_InSalesDB (String environment, String pretest) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "14287127";
		String strWorkflowEntity = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);

		if (environment.equals("prod")) {
			strTld = "net";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "MEL-6007";
			strMajorProduct = "Done For You Website";
			strProductPeriod = "1 x M";
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Netregistry";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
		if (pretest.equals("enabled")) {
			
			System.out.println("Start Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setGreenCode(strGreenCode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
			cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName, strTld, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
			csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();
			System.out.println("Workflow ID: " + strWorkflowId);
			System.out.println("Domain Name: " + strDomainName + "." + strTld);
			driver.close();
		}
		else {
			
			//Test Step 2: Verify if domain registration workflow is completed
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowEntity = caworkflowadminpage.getWorkflowEntity(strWorkflowId);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
					
			//Test Step 3: Verify if productsetup2 workflow is approved
			System.out.println("Workflow Entity: " + strWorkflowEntity);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowEntity);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
			driver.close();
		}
		
		System.out.println("End Test: verify_NetDomain_and_DIFM_Order_InSalesDB");
	}
	
	
	@Parameters({"environment", "pretest"})
	@Test
	public void verify_ComDomain_and_Office365_Order_InSalesDB (String environment, String pretest) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "14287347";
		String strOffice365Product = null;		
		String strWorkflowEntity = null;
		
		DateFormat df = new SimpleDateFormat("ddMMYYYY-hhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);
		System.out.println("O365 Domain: " + strDomainName);

		if (environment.equals("prod")) {
			
			strTld = "com";
			strRegistrationPeriod = "1";
			strGreenCode = "MEL-6007";
			strPaymentMethod = "Visa: 471527******1714 06/2021";
			strRegistrantDetails = "Netregistry";			
			strOffice365Product = "O365-EESEN-QTY";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)	
		if (pretest.equals("enabled")) {
		
			System.out.println("Start Test: verify_ComDomain_and_Office365_Order_InSalesDB");
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setGreenCode(strGreenCode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
			cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
			csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csshowdomainservicespage.setAddOnProduct(strOffice365Product);
			csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();	
			System.out.println("Workflow ID: " + strWorkflowId);
			System.out.println("Domain Name: " + strDomainName + "." + strTld);
			driver.close();
		}
		else {
			
			//Test Step 2: Verify if domainregistration2 workflow status is "domain registration completed"
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.login("roy.alcantara", "Stocks008");
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			strWorkflowEntity = caworkflowadminpage.getWorkflowEntity(strWorkflowId);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
			
			//Test Step 3: Verify if productsetup2 workflow status is "approved"
			System.out.println("Workflow Entity: " + strWorkflowEntity);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowEntity);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetup2"), "approved", caworkflowadminpage.getWorkflowStatus("productsetup2"));
			
			//Test Step 4: Verify if productSetupOffice365 workflow status is "Office365 order completed"
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("productSetupOffice365"), "Office365 order completed", caworkflowadminpage.getWorkflowStatus("o365Provisioning"));
			driver.close();
		}
	
		System.out.println("End Test: verify_ComDomain_and_Office365_Order_InSalesDB");
	}
	
	@Parameters({"environment", "pretest"})
	@Test
	public void verify_NzDomain_Order_InSalesDB (String environment, String pretest) throws InterruptedException{

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strRegistrationPeriod = null;
		String strGreenCode = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strWorkflowId = "14287161";
		
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d = new Date();
		strDomainName = "testconsoleautomation" + df.format(d);

		if (environment.equals("prod")) {
			strTld = "nz";
			strRegistrationPeriod = "1 x Y";
			strGreenCode = "825-00";
			strPaymentMethod = "Prepaid credit: ";
			strRegistrantDetails = "MelbourneIT";
		}
	
		//Test Step 1: Login to sales db and place an order for domain registration and a single product (e.g. Done For You Website)
		if (pretest.equals("enabled")) {
			
			System.out.println("Start Test: verify_NzDomain_Order_InSalesDB");
			initialization(environment, "salesdburl");
			csloginpage = new CSLoginPage();
			csloginpage.setDefaultLoginDetails(environment);
			csnrcrmpage = csloginpage.clickLoginButton();
			csnrcrmpage.setGreenCode(strGreenCode);
			cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();			
			cscreatedomainwindowpage.setDomainDetails(strDomainName, strTld, strRegistrationPeriod, strPaymentMethod);
			csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName, "Update Details");
			csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
			csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName);
			csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
			strWorkflowId = csworkflownotificationpage.getWorkflowID();
			csworkflownotificationpage.clickOKButton();
			System.out.println("Workflow ID: " + strWorkflowId);
			System.out.println("Domain Name: " + strDomainName + "." + strTld);
			driver.close();
		}
		else {
			
			//Test Step 2: Verify if domain registration workflow is completed
			initialization(environment, "consoleadmin");
			caloginpage = new CALoginPage();
			caheaderpage = caloginpage.setDefaultLoginDetails(environment);
			caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
			Assert.assertEquals(caworkflowadminpage.getWorkflowStatus("domainregistration2"), "domain registration completed", caworkflowadminpage.getWorkflowStatus("domainregistration2"));
			driver.close();		
		}
		
		System.out.println("End Test: verify_NzDomain_Order_InSalesDB");
	}
	
}
