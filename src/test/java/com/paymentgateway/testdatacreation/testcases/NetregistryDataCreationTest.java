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
import com.netregistryoldwebsite.pages.NRGAccountContactPage;
import com.netregistryoldwebsite.pages.NRGAccountPage;
import com.netregistryoldwebsite.pages.NRGAddDomainPrivacyPage;
import com.netregistryoldwebsite.pages.NRGAddExtrasPage;
import com.netregistryoldwebsite.pages.NRGAddHostingPage;
import com.netregistryoldwebsite.pages.NRGBillingPage;
import com.netregistryoldwebsite.pages.NRGCreditCardsDetailsPage;
import com.netregistryoldwebsite.pages.NRGDomainSearchPage;
import com.netregistryoldwebsite.pages.NRGHeaderPage;
import com.netregistryoldwebsite.pages.NRGHostingAndExtrasPage;
import com.netregistryoldwebsite.pages.NRGLoginPage;
import com.netregistryoldwebsite.pages.NRGOnlineOrderPage;
import com.netregistryoldwebsite.pages.NRGOrderCompletePage;
import com.netregistryoldwebsite.pages.NRGRegistrantContactPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class NetregistryDataCreationTest extends TestBase{
	
	//NetRegistry Customer Portal Pages
	NRGOnlineOrderPage nrgonlineorderpage;
	NRGDomainSearchPage nrgdomainsearchpage;
	NRGAddDomainPrivacyPage nrgadddomainprivacypage;
	NRGHostingAndExtrasPage nrghostingandextraspage;
	NRGAddHostingPage nrgaddhostingpage;
	NRGAddExtrasPage nrgaddextraspage;
	NRGAccountContactPage nrgaccountcontactpage;
	NRGRegistrantContactPage nrgregistrantcontactpage;
	NRGBillingPage nrgbillingpage;
	NRGOrderCompletePage nrgordercompletepage;
	NRGLoginPage nrgloginpage;
	NRGHeaderPage nrgheaderpage;
	NRGAccountPage nrgaccountpage;
	NRGCreditCardsDetailsPage nrgcreditcardsdetailspage;
	
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
	
	
	public NetregistryDataCreationTest() {
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
		strDomainName_01 = "TestNetRegistryNewCustomer" + df.format(d1);
		strDomainName_02 = "TestNetRegistryReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to NetRegistry search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithDomainRegistrationAndEnableAutoRenew");
		initialization(environment, "cart_domainsearchurl_netregistry");
		
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");
		nrgonlineorderpage.tickTld(".net.au");
		nrgonlineorderpage.tickTld(".com");	
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry New Customer - Domain Reg 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "125";
			nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry New Customer - Domain Reg 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "4578";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry New Customer - Domain Reg 40120026";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "781";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {
			strCardOwnerName = "NetRegistry New Customer - Domain Reg 40050004";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "569";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("NetRegistry Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, "com");
		caworkflowadminpage.processFraudCheck();
		
		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to NetRegistry search page and purchase new domain name for returning customer
		initialization(environment, "cart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");	
		nrgonlineorderpage.tickTld(".net.au");
		nrgonlineorderpage.tickTld(".com");
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		nrgbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry Returning Customer - Domain Reg 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "112";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry Returning Customer - Domain Reg 54545454";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "883";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry Returning Customer - Domain Reg 22230014";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "670";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);

		}
		else {
			strCardOwnerName = "NetRegistry Returning Customer - Domain Reg 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2021";
		    strCardSecurityCode = "9910";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("NetRegistry Test Migration Data [Customer with domain registration (enable auto-renew)]:" + strAccountReference + "," + strCardNumber + "," + strCardExpiryMonth + "," + strCardExpiryYear);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, "com");
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
		strDomainName_01 = "TestNetRegistryNewCustomer" + df.format(d1);
		strDomainName_02 = "TestNetRegistryReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".net";
		}
			
		//Test Step 1: Navigate to NetRegistry search page, then purchase domain and monthly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithMonthlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_netregistry");
		
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");	
		nrgonlineorderpage.tickTld(".net.au");	
		nrgonlineorderpage.tickTld(".com");	
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();		
		nrgaddhostingpage = nrghostingandextraspage.selectBasicCloudHosting();
		//nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct);
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry New Customer - Monthly Product 40091881";
			strCardType = "Visa";
			strCardNumber = "4009348888881881";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "776";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry New Customer - Monthly Product 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "4430";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry New Customer - Monthly Product 45000061";
			strCardType = "Visa";
			strCardNumber = "4500600000000061";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "992";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "NetRegistry New Customer - Monthly Product 40120026";
			strCardType = "Visa";
			strCardNumber = "4012000033330026";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "238";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
			
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("NetRegistry Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, "net");
		caworkflowadminpage.processFraudCheck();
				
		strWorkflowId_02 = caworkflowadminpage.getProductSetup2WorkflowID();
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_02);
		caworkflowadminpage.processProductSetup2ByWFID(strWorkflowId_02);

		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to NetRegistry search page, then purchase domain and monthly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");		
		nrgonlineorderpage.tickTld(".net.au");	
		nrgonlineorderpage.tickTld(".com");	
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaddhostingpage = nrghostingandextraspage.selectBasicCloudHosting();
//		nrgaddhostingpage = nrghostingandextraspage.clickAddHostingButton();
//		nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct);
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		nrgbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry Returning Customer - Monthly Product 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "545";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry Returning Customer - Monthly Product 22230014";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "108";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry Returning Customer - Monthly Product 54545454";
			strCardType = "MasterCard";
			strCardNumber = "5454545454545454";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "731";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "NetRegistry Returning Customer - Monthly Product 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2022";
		    strCardSecurityCode = "1065";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);
		System.out.println("NetRegistry Test Account Reference [Customer with monthly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_03, "net");
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
		strDomainName_01 = "TestNetRegistryNewCustomer" + df.format(d1);
		strDomainName_02 = "TestNetRegistryReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to NetRegistry search page, then purchase domain and yearly billing product as a new customer
		System.out.println("Start Test: generateCustomerDataWithYearlyBillingProduct");
		initialization(environment, "cart_domainsearchurl_netregistry");
		
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");		
		nrgonlineorderpage.tickTld(".net.au");		
		nrgonlineorderpage.tickTld(".com");		
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaddhostingpage = nrghostingandextraspage.selectBasicCloudHostingYearly();
//		nrgaddhostingpage = nrghostingandextraspage.clickAddHostingButton();
//		nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct);
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry New Customer - Yearly Product 40127777";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "943";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry New Customer - Yearly Product 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "6689";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry New Customer - Yearly Product 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "705";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "NetRegistry New Customer - Yearly Product 40121881";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "185";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01  = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("NetRegistry Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, "com");
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
		
		//Test Step 6: Navigate again to NetRegistry search page, then purchase domain and yearly billing product for returning customer
		initialization(environment, "cart_domainsearchurl_netregistry");
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");		
		nrgonlineorderpage.tickTld(".net.au");		
		nrgonlineorderpage.tickTld(".com");		
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_02, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaddhostingpage = nrghostingandextraspage.selectBasicCloudHostingYearly();
//		nrgaddhostingpage = nrghostingandextraspage.clickAddHostingButton();
//		nrghostingandextraspage = nrgaddhostingpage.clickAddProduct(strProduct);
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setReturningCustomerContacts(strAccountReference, strAccountReferenceNewPassword);
		nrgregistrantcontactpage = nrgaccountcontactpage.clickLoginButton();
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 7: Input credit card details and submit the order 
		nrgbillingpage.selectNewCreditCardOption();
		
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry Returning Customer - Yearly Product 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "433";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry Returning Customer - Yearly Product 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "331";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry Returning Customer - Yearly Product 22230014";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "400";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "NetRegistry Returning Customer - Yearly Product 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2023";
		    strCardSecurityCode = "7556";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}

		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 8: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_03 = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_03);	
		System.out.println("NetRegistry Test Account Reference [Customer with yearly billing product]:" + strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 9: Login to console admin, then process domainregistration2 and productsetup2 workflows
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_03);		
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_03, "com");
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
		strDomainName_01 = "TestNetRegistryNewCustomer" + df.format(d1);
		strDomainName_02 = "TestNetRegistryReturningCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld_01 = ".com";
			
			//For Sales DB
			strTld_02 = "nz";
			strRegistrationPeriod = "1 x Y AU$39[ AU$0 setup]";
			
			if ((intMinCount % 2)==0) {
				strMajorProduct = "Basic cPanel Hosting";
				strProductPeriod = "1 x M AU$7.95[ AU$0 setup]";
			}
			else {
				strMajorProduct = "Basic Cloud Hosting";
				strProductPeriod = "1 x Y AU$164.95[ AU$0 setup]";
			}
			strPaymentMethod = "Invoice";
			strRegistrantDetails = "Payment Gateway Test";			
		}
			
		//Test Step 1: Navigate to NetRegistry search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithOutstandingInvoice");
		initialization(environment, "cart_domainsearchurl_netregistry");
		
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");		
		nrgonlineorderpage.tickTld(".net.au");		
		nrgonlineorderpage.tickTld(".com");	
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld_01);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry New Customer - With Outstanding Invoice 42171119";
			strCardType = "Visa";
			strCardNumber = "4217651111111119";
		    strCardExpiryMonth = "09";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "443";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry New Customer - With Outstanding Invoice 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "10";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "7621";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry New Customer - With Outstanding Invoice 40121881";
			strCardType = "Visa";
			strCardNumber = "4012888888881881";
		    strCardExpiryMonth = "11";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "730";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);	
		}
		else {
			strCardOwnerName = "NetRegistry New Customer - With Outstanding Invoice 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "12";
		    strCardExpiryYear = "2024";
		    strCardSecurityCode = "782";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId_01 = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId_01);	
		System.out.println("NetRegistry Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId_01);		
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_01, "com");
		caworkflowadminpage.processFraudCheck();

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
		System.out.println("NetRegistry Test Payment Method: " + strPaymentMethod);
		System.out.println("NetRegistry Test Account Reference [Customer with outstanding invoice]:" + strAccountReference);
		
		
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
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId_02, "nz");
		
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
		strDomainName_01 = "TestNetRegistryNewCustomer" + df.format(d1);
					
		if (environment.equals("stagingdev-5")) {
			strTld = ".com";
		}
			
		//Test Step 1: Navigate to NetRegistry search page and purchase domain name for new customer
		System.out.println("Start Test: generateCustomerDataWithDefaultCreditCard");
		initialization(environment, "cart_domainsearchurl_netregistry");
		
		nrgonlineorderpage = new NRGOnlineOrderPage();
		nrgonlineorderpage.tickTld(".com.au");		
		nrgonlineorderpage.tickTld(".net.au");	
		nrgonlineorderpage.tickTld(".com");	
		nrgonlineorderpage.setDomainNameAndTld(strDomainName_01, strTld);
		nrgdomainsearchpage = nrgonlineorderpage.clickNewDomainSearchButton();
		nrgadddomainprivacypage = nrgdomainsearchpage.clickContinueToCheckout();
		
		nrghostingandextraspage= nrgadddomainprivacypage.clickNoThanks();
		nrgaccountcontactpage= nrghostingandextraspage.clickContinueButton();
		nrgaccountcontactpage.setCustomerDefaultInformation();
		nrgregistrantcontactpage = nrgaccountcontactpage.clickContinueButton();		
		nrgbillingpage = nrgregistrantcontactpage.clickContinueButton();
		
		//Test Step 2: Input credit card details and submit the order 
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry New Customer 41111111";
			strCardType = "Visa";
			strCardNumber = "4111111111111111";
		    strCardExpiryMonth = "01";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "714";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry New Customer 37820005";
			strCardType = "Amex";
			strCardNumber = "378282246310005";
		    strCardExpiryMonth = "02";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "2167";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry New Customer 37148431";
			strCardType = "Amex";
			strCardNumber = "371449635398431";
		    strCardExpiryMonth = "03";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "4438";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		else {
			strCardOwnerName = "NetRegistry New Customer 22230011";
			strCardType = "MasterCard";
			strCardNumber = "2223000048400011";
		    strCardExpiryMonth = "04";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "966";
		    nrgbillingpage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		nrgbillingpage.tickTermsAndConditions();
		nrgordercompletepage = nrgbillingpage.clickPlaceYourOrder();		
		
		//Test Step 3: Verify if order is completed and get reference id (workflow id) details
		Assert.assertTrue(nrgordercompletepage.isOrderComplete(), "Order is not completed");
		strWorkflowId = nrgordercompletepage.getSingleReferenceID();
		strAccountReference = nrgordercompletepage.getAccountReferenceID();
		System.out.println("Account Reference:" + strAccountReference);	
		System.out.println("Reference ID[0]:" + strWorkflowId);	
		System.out.println("NetRegistry Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		saveGreenCode(strAccountReference);
		saveGreenCodeAndCreditCardDetails(strAccountReference, strCardNumber, strCardOwnerName, strCardExpiryMonth, strCardExpiryYear);
		driver.close();
		
		//Test Step 4: Login to console admin and process domain registration workflow
		initialization(environment, "consoleadmin");
		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		caworkflowadminpage = caheaderpage.searchWorkflow(strWorkflowId);		
		caworkflowadminpage.processDomainRegistration2Workflow(strWorkflowId, "com");
		caworkflowadminpage.processFraudCheck();

		
		//Test Step 5: Set the new password for the account reference
		caheaderpage = new CAHeaderPage();
		caaccountreferencepage = caheaderpage.searchAccountReference(strAccountReference);
		caaccountreferencepage.updatePassword(strAccountReferenceNewPassword);
		driver.close();
		
		//Test Step 6: Navigate again to NetRegistry search page and purchase new domain name for returning customer
		initialization(environment, "customerportalurl_netregistry");
		nrgloginpage = new NRGLoginPage();
		nrgloginpage.setLoginDetails(strAccountReference, strAccountReferenceNewPassword);
		nrgheaderpage = nrgloginpage.clickLoginButton();
		nrgaccountpage = nrgheaderpage.clickAccountTab();
		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();
		
		if (intMinCount == 4) {
			strCardOwnerName = "NetRegistry Returning Customer - Default Credit Card 55554444";
			strCardType = "MasterCard";
			strCardNumber = "5555555555554444";
		    strCardExpiryMonth = "05";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "122";	
			nrgcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 3) {
			strCardOwnerName = "NetRegistry Returning Customer - Default Credit Card 22230014";
			strCardType = "MasterCard";
			strCardNumber = "2223520043560014";
		    strCardExpiryMonth = "06";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "178";	
			nrgcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else if (intMinCount == 2) {
			strCardOwnerName = "NetRegistry Returning Customer - Default Credit Card 40127777";
			strCardType = "Visa";
			strCardNumber = "4012000077777777";
		    strCardExpiryMonth = "07";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "144";	
			nrgcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		
		}
		else {	
			strCardOwnerName = "NetRegistry Returning Customer - Default Credit Card 40050004";
			strCardType = "Visa";
			strCardNumber = "4005519200000004";
		    strCardExpiryMonth = "08";
		    strCardExpiryYear = "2025";
		    strCardSecurityCode = "811";	
		    nrgcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwnerName, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		}
		
		System.out.println("NetRegistry Test Account Reference [Customer with default credit card (testing domain auto renewals)]:" + strAccountReference);
		nrgcreditcardsdetailspage.tickMakeCreditCardAsDefaultPayment();
		nrgcreditcardsdetailspage.clickAddCreditCard();
		
		//Test Step 7: Verify if adding new card is successful
		Assert.assertTrue(nrgcreditcardsdetailspage.isNewCreditCardAdded(), "New Credit Card is not added");
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
		
		//PrintWriter writer = new PrintWriter("GreencodeAndCreditCardDetails" + df.format(d1) + ".txt", "UTF-8");
		PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "\\QuestTestData\\Netregistry\\GreencodeAndCreditCardDetails_" + df.format(d1) + ".txt", "UTF-8");
		
		
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
		properties.setProperty("db.server.name", "console-db-1.staging.netregistry.net");
		properties.setProperty("db.port.number", "20005");
		properties.setProperty("db.database.name", "automation");
		properties.setProperty("db.user", "automation");
		properties.setProperty("db.password", "{n0P41nN0G41n}");
		properties.setProperty("sql", "SELECT * FROM public.v_companybilling_bt WHERE cm_greencode IN ("+ strDataMigrationGC +") AND card_digits IS NOT NULL AND braintree_migrated = false;");
		properties.setProperty("output.file", "output.csv");	
	
		//File file = new File("migration" + df.format(d1) + ".properties");
		File file = new File(System.getProperty("user.dir") + "\\QuestTestData\\Netregistry\\migration_" + df.format(d1) + ".properties");
		FileOutputStream fileOut = new FileOutputStream(file);
		properties.store(fileOut, "DB");
		fileOut.close();
		System.out.println("Done File Creation");

		}
	
}


