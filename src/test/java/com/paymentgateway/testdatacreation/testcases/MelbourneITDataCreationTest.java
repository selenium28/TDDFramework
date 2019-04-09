package com.paymentgateway.testdatacreation.testcases;
import java.awt.AWTException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.braintree.pages.BTFoundTransactionPage;
import com.braintree.pages.BTLoginPage;
import com.braintree.pages.BTMainTabPage;
import com.braintree.pages.BTTransactionsSearchPage;
import com.consoleadmin.pages.CAAccountReferencePage;
import com.consoleadmin.pages.CADomainLevelPage;
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
import com.melbourneitwebsite.pages.MITAccountContactPage;
import com.melbourneitwebsite.pages.MITAccountPage;
import com.melbourneitwebsite.pages.MITAddDomainPrivacyPage;
import com.melbourneitwebsite.pages.MITAddExtrasPage;
import com.melbourneitwebsite.pages.MITAddHostingPage;
import com.melbourneitwebsite.pages.MITBillingPage;
import com.melbourneitwebsite.pages.MITCreditCardsDetailsPage;
import com.melbourneitwebsite.pages.MITDomainSearchPage;
import com.melbourneitwebsite.pages.MITHeaderPage;
import com.melbourneitwebsite.pages.MITHostingAndExtrasPage;
import com.melbourneitwebsite.pages.MITLoginPage;
import com.melbourneitwebsite.pages.MITOnlineOrderPage;
import com.melbourneitwebsite.pages.MITOrderCompletePage;
import com.melbourneitwebsite.pages.MITRegistrantContactPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class MelbourneITDataCreationTest extends TestBase{
	
	//MelbourneIT Customer Portal Pages
	MITOnlineOrderPage mitonlineorderpage;
	MITDomainSearchPage mitdomainsearchpage;
	MITAddDomainPrivacyPage mitadddomainprivacypage;
	MITHostingAndExtrasPage mithostingandextraspage;
	MITAddHostingPage mitaddhostingpage;
	MITAddExtrasPage mitaddextraspage;
	MITAccountContactPage mitaccountcontactpage;
	MITRegistrantContactPage mitregistrantcontactpage;
	MITBillingPage mitbillingpage;
	MITOrderCompletePage mitordercompletepage;
	MITLoginPage mitloginpage;
	MITHeaderPage mitheaderpage;
	MITAccountPage mitaccountpage;
	MITCreditCardsDetailsPage mitcreditcardsdetailspage;
	
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
	CAAccountReferencePage caaccountreferencepage;
	CADomainLevelPage cadomainlevelpage;
	
	//Braintree Pages
	BTLoginPage btloginpage;
	BTMainTabPage btmaintabpage;
	BTTransactionsSearchPage bttransactionssearchpage;
	BTFoundTransactionPage btfoundtransactionpage;
	
	TestUtil testUtil;
	String clienttoken;
	public static ExtentTest logger;
	public Integer countDataMigrationGC = 0;
	public List<String> arrDataMigrationGC = new ArrayList<String>();
	public List<String> arrDataMigrationGCAndCCDetails = new ArrayList<String>();
	
	
	public MelbourneITDataCreationTest() {
		super();
	}

	public void saveGreenCode(String strgreencode) {
		System.out.println("preparing to save greencode");
		arrDataMigrationGC.add(strgreencode) ;
		System.out.println("done saving greencode");
	}
	
	public void saveGreenCodeAndCreditCardDetails(String strgreencode, String strcardnumber, String strcardowner, String strcardexpirymonth, String strcardexpiryyear) {
		System.out.println("preparing to save greencode and card details");
		arrDataMigrationGCAndCCDetails.add(strgreencode+","+strcardnumber+","+'"'+strcardowner+'"'+","+strcardexpirymonth+","+strcardexpiryyear);
		System.out.println("done saving greencode and card details");
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void generateCustomerDataWithDomainRegistrationAndEnableAutoRenew(String environment, Integer iteration) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestMelbourneITNewCustomer" + df.format(d1);
		strDomainName_02 = "TestMelbourneITReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to MelbourneIT search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		initialization(environment, "cart_domainsearchurl_melbourneit");
		
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");
		mitonlineorderpage.tickTld(".net.au");
		mitonlineorderpage.tickTld(".com");	
		mitonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();		
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT New Customer - Domain Reg 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "125";
			mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT New Customer - Domain Reg 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "4578";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT New Customer - Domain Reg 40120026";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "781";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {
			strCardOwnerName = "MelbourneIT New Customer - Domain Reg 40050004";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "569";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("MelbourneIT Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		
		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to MelbourneIT search page and purchase new domain name for returning customer
		initialization(environment, "cart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");	
		mitonlineorderpage.tickTld(".net.au");
		mitonlineorderpage.tickTld(".com");
		mitonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		mitbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT Returning Customer - Domain Reg 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "112";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT Returning Customer - Domain Reg 54545454";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "883";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT Returning Customer - Domain Reg 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "670";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else {
			strCardOwnerName = "MelbourneIT Returning Customer - Domain Reg 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "9910";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("MelbourneIT Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		
		}
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void generateCustomerDataWithMonthlyBillingProduct(String environment, Integer iteration) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		String strWorkflowId_04 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Basic cPanel Hosting";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestMelbourneITNewCustomer" + df.format(d1);
		strDomainName_02 = "TestMelbourneITReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".net";
		}
			
		//Test Step 1: Navigate to MelbourneIT search page, then purchase domain and monthly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithMonthlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_melbourneit");
		
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");	
		mitonlineorderpage.tickTld(".net.au");	
		mitonlineorderpage.tickTld(".com");	
		mitonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();		
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();	
		mithostingandextraspage.clickWebsiteAndHostingLink();
		mitaddhostingpage = mithostingandextraspage.selectBasicCloudHosting();
		//mithostingandextraspage = mitaddhostingpage.clickAddProduct(strProduct);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();		
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT New Customer - Monthly Product 40091881";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "776";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT New Customer - Monthly Product 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "4430";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT New Customer - Monthly Product 45000061";
			strCardType = "Visa";
			strCardNumber = "4500600000000061";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "992";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "MelbourneIT New Customer - Monthly Product 40120026";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "238";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
			
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("MelbourneIT Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
				
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to MelbourneIT search page, then purchase domain and monthly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");		
		mitonlineorderpage.tickTld(".net.au");	
		mitonlineorderpage.tickTld(".com");	
		
		mitonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mithostingandextraspage.clickWebsiteAndHostingLink();
		mitaddhostingpage = mithostingandextraspage.selectBasicCloudHosting();
//		mitaddhostingpage = mithostingandextraspage.clickAddHostingButton();
//		mithostingandextraspage = mitaddhostingpage.clickAddProduct(strProduct);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		mitbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT Returning Customer - Monthly Product 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "545";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT Returning Customer - Monthly Product 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "108";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT Returning Customer - Monthly Product 54545454";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "731";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "MelbourneIT Returning Customer - Monthly Product 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "1065";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);
		System.out.println("MelbourneIT Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithMonthlyBillingProduct");
		}
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void generateCustomerDataWithYearlyBillingProduct(String environment, Integer iteration) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld = null;
		String strWorkflowId_01 = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		String strWorkflowId_04 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		String strProduct = "Business Cloud Hosting";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestMelbourneITNewCustomer" + df.format(d1);
		strDomainName_02 = "TestMelbourneITReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to MelbourneIT search page, then purchase domain and yearly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithYearlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_melbourneit");
		
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");		
		mitonlineorderpage.tickTld(".net.au");		
		mitonlineorderpage.tickTld(".com");		
		mitonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mithostingandextraspage.clickWebsiteAndHostingLink();
		mitaddhostingpage = mithostingandextraspage.selectBasicCloudHostingYearly();
//		mitaddhostingpage = mithostingandextraspage.clickAddHostingButton();
//		mithostingandextraspage = mitaddhostingpage.clickAddProduct(strProduct);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();		
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT New Customer - Yearly Product 40127777";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "943";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT New Customer - Yearly Product 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "6689";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT New Customer - Yearly Product 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "705";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "MelbourneIT New Customer - Yearly Product 40121881";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "185";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01  = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("MelbourneIT Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);
		driver.close();

		//Test Step 5: Set the new password for the account reference
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to MelbourneIT search page, then purchase domain and yearly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_melbourneit");
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");		
		mitonlineorderpage.tickTld(".net.au");		
		mitonlineorderpage.tickTld(".com");		
		mitonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mithostingandextraspage.clickWebsiteAndHostingLink();
		mitaddhostingpage = mithostingandextraspage.selectBasicCloudHostingYearly();
//		mitaddhostingpage = mithostingandextraspage.clickAddHostingButton();
//		mithostingandextraspage = mitaddhostingpage.clickAddProduct(strProduct);
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		mitregistrantcontactpage = mitaccountcontactpage.clickLoginButton();
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		mitbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT Returning Customer - Yearly Product 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "433";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT Returning Customer - Yearly Product 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "331";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT Returning Customer - Yearly Product 54545454";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "400";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "MelbourneIT Returning Customer - Yearly Product 37148431";
			strCardType = "MasterCard";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "7556";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);	
		System.out.println("MelbourneIT Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_03);
		caworkflowadminpage.processFraudCheck();
		
		strWorkflowId_04 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_04);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_04);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithYearlyBillingProduct");
		}
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void generateCustomerDataWithOutstandingInvoice(String environment, Integer iteration) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strDomainName_02 = null;
		String strTld_01 = null;
		String strWorkflowId_01 = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		String strTld_02 = null;
		String strRegistrationPeriod = null;
		String strPaymentMethod = null;
		String strRegistrantDetails = null;
		String strMajorProduct = null;
		String strProductPeriod = null;
		String strWorkflowId_02 = null;
		String strWorkflowId_03 = null;
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestMelbourneITNewCustomer" + df.format(d1);
		strDomainName_02 = "TestMelbourneITReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld_01 = ".com";
			
			//For Sales DB
			strTld_02 = "nz";
			strRegistrationPeriod = "1 x Y AU$70[ AU$0 setup]";
			
			if ((intMinCount % 2)==0) {
				strMajorProduct = "Basic cPanel Hosting";
				strProductPeriod = "1 x M AU$7.95[ AU$0 setup]";
			}
			else {
				strMajorProduct = "Basic cPanel Hosting";
				strProductPeriod = "1 x Y AU$155.4[ AU$0 setup]";
			}
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Payment Gateway Test";			
		}
			
		//Test Step 1: Navigate to MelbourneIT search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithOutstandingInvoice");
		initialization(environment, "cart_domainsearchurl_melbourneit");
		
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");		
		mitonlineorderpage.tickTld(".net.au");		
		mitonlineorderpage.tickTld(".com");	
		mitonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld_01);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();		
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT New Customer - With Outstanding Invoice 42171119";
			strCardType = "Visa";
			strCardNumber = "4217651111111119";
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "443";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT New Customer - With Outstanding Invoice 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "7621";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT New Customer - With Outstanding Invoice 40121881";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "11";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "730";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else {
			strCardOwnerName = "MelbourneIT New Customer - With Outstanding Invoice 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "12";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "782";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("MelbourneIT Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_01);
		caworkflowadminpage.processFraudCheck();
		//caworkflowadminpage.processDelegateDomain();

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Login to Sales DB page, then purchase a domain with monthly or yearly product and pay through invoice
		initialization(environment, "salesdburl");
		csloginpage = new CSLoginPage();
		csloginpage.setDefaultLoginDetails(environment);
		csnrcrmpage = csloginpage.clickLoginButton();
		csnrcrmpage.setGreenCode(strAccountReference);
		cscreatedomainwindowpage = csnrcrmpage.clickNewDomainNPSButton();
		cscreatedomainwindowpage.setDomainandMajorProductDetails(strDomainName_02, strTld_02, strRegistrationPeriod, strMajorProduct, strProductPeriod, strPaymentMethod);
		System.out.println("MelbourneIT Test Payment Method: " + strPaymentMethod);
		System.out.println("MelbourneIT Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		
		
		csregistrantdetailspage = csnrcrmpage.clickRegistrantDetails(strDomainName_02, "Update Details");
		csnrcrmpage = csregistrantdetailspage.setRegistrantDetails(strRegistrantDetails);
		csshowdomainservicespage = csnrcrmpage.clickShowDomainServices(strDomainName_02);
		csworkflownotificationpage = csshowdomainservicespage.clickConfirmAllServices();
		strWorkflowId_02 = csworkflownotificationpage.getWorkflowID();
		csworkflownotificationpage.clickOKButton();
		driver.close();
			
		//Test Step 7: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId_02);
		
		strWorkflowId_03 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_03);
		driver.close();
	
		System.out.println("End Test: generateCustomerDataWithOutstandingInvoice");
		
		}
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void generateCustomerDataWithDefaultCreditCard(String environment, Integer iteration) throws InterruptedException, AWTException{
	
		// Initialization (Test Data Creation and Assignment)
		String strDomainName_01 = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		String strAccountReferenceNewPassword = "comein22";
		
		String strCardOwnerName = null;
		String strCardType = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
		
		
		Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {

		// Generate name for first and second domain
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		strDomainName_01 = "TestMelbourneITNewCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to MelbourneIT search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithDefaultCreditCard");
		initialization(environment, "cart_domainsearchurl_melbourneit");
		
		mitonlineorderpage = new MITOnlineOrderPage();
		mitonlineorderpage.tickTld(".com.au");		
		mitonlineorderpage.tickTld(".net.au");	
		mitonlineorderpage.tickTld(".com");	
		mitonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		mitdomainsearchpage = mitonlineorderpage.clickNewDomainSearchButton();
		mitadddomainprivacypage = mitdomainsearchpage.clickContinueToCheckout();
		
		mithostingandextraspage= mitadddomainprivacypage.clickNoThanks();
		mitaccountcontactpage= mithostingandextraspage.clickContinueButton();
		mitaccountcontactpage.setCustomerDefaultInformation();
		mitregistrantcontactpage = mitaccountcontactpage.clickContinueButton();		
		mitbillingpage = mitregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT New Customer 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "714";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT New Customer 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "2167";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT New Customer 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "4438";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "MelbourneIT New Customer 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "966";
		    mitbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		mitbillingpage.tickTermsAndConditions();
		mitordercompletepage = mitbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(mitordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = mitordercompletepage.getSingleReferenceID();
		strAccountReference = mitordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("MelbourneIT Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistrationWF(strWorkflowId);
		caworkflowadminpage.processFraudCheck();
		
		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to MelbourneIT search page and purchase new domain name for returning customer
		initialization(environment, "customerportalurl_melbourneit");
		mitloginpage = new MITLoginPage();
		mitloginpage.setLoginDetails(strAccountReference, strAccountReferenceNewPassword);
		mitheaderpage = mitloginpage.clickLoginButton();
		mitaccountpage = mitheaderpage.clickAccountTab();
		mitcreditcardsdetailspage = mitaccountpage.clickEditCreditCardsOnFile();
		
		if (intMinCount == 4) {
			strCardOwnerName = "MelbourneIT Returning Customer - Default Credit Card 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "122";	
			mitcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "MelbourneIT Returning Customer - Default Credit Card 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "178";	
			mitcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "MelbourneIT Returning Customer - Default Credit Card 40127777";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "144";	
			mitcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {	
			strCardOwnerName = "MelbourneIT Returning Customer - Default Credit Card 40050004";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "811";	
		    mitcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		System.out.println("MelbourneIT Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		mitcreditcardsdetailspage.tickMakeCreditCardAsDefaultPayment();
		mitcreditcardsdetailspage.clickAddCreditCard();
		
		//Test Step 7: Verify if adding new card is successful
		Assert.assertTrue(mitcreditcardsdetailspage.isNewCreditCardAdded(), "New Credit Card is not added");
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		System.out.println("End Test: generateCustomerDataWithDefaultCreditCard");
		
		}
	}
	
	@Parameters({"environment"})
	@Test
	public void createTextFileForGreencodeAndCreditCardDetails (String environment) throws InterruptedException, IOException{
		
		Integer intMaxCount = arrDataMigrationGCAndCCDetails.size();
		Integer intMinCount = null;
		
		// Generate unique file name for migration test data
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		
		PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "\\QuestTestData\\MelbourneIT\\GreencodeAndCreditCardDetails_" + df.format(d1) + ".txt", "UTF-8");
		writer.println("green_code,card_digits,card_owner,card_expire_month,card_expire_year");
		
		
		for(intMinCount = 0; intMinCount<intMaxCount; intMinCount++) {
			writer.println(arrDataMigrationGCAndCCDetails.get(intMinCount));
		}	
		writer.close();
		System.out.println("Done with Text File Creation");
		
		}
	
	@Parameters({"environment"})
	@Test
	public void createPropertyFileForDecryptedDataVerification (String environment) throws InterruptedException, IOException{
		
		Integer intMaxCount = arrDataMigrationGC.size();
		Integer intMinCount = null;
		
		// Generate unique file name for migration test data
		DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
		Date d1 = new Date();
		
		StringBuffer strDataMigrationGC = new StringBuffer("'"+arrDataMigrationGC.get(0)+"'");
		
		for(intMinCount = 1; intMinCount<intMaxCount; intMinCount++) {
		
		
		
			strDataMigrationGC.append(","+"'"+ arrDataMigrationGC.get(intMinCount)+"'"); 
			
		}		
		System.out.println(strDataMigrationGC);
		
		Properties properties = new Properties();
		properties.setProperty("db.server.name", "console-db-1.staging.mit.net");
		properties.setProperty("db.port.number", "20005");
		properties.setProperty("db.database.name", "automation");
		properties.setProperty("db.user", "automation");
		properties.setProperty("db.password", "{n0P41nN0G41n}");
		properties.setProperty("sql", "SELECT * FROM public.v_companybilling_bt WHERE cm_greencode IN ("+ strDataMigrationGC +") AND card_digits IS NOT NULL AND braintree_migrated = false;");
		properties.setProperty("output.file", "output.csv");	
	
		File file = new File(System.getProperty("user.dir") + "\\QuestTestData\\MelbourneIT\\migration_" + df.format(d1) + ".properties");
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, "DB");
		fileOut.close();
		System.out.println("Done File Creation");

		}
	
}


