package com.paymentgateway.stage.netregistry.testcases;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.Environment;
import com.base.TestBase;
import com.netregistrynewwebsite.pages.NRGNSAboutYouPage;
import com.netregistrynewwebsite.pages.NRGNSAddServicesToYourDomainPage;
import com.netregistrynewwebsite.pages.NRGNSDomainPrivacyPage;
import com.netregistrynewwebsite.pages.NRGNSEmailAndOffice365PackagesPage;
import com.netregistrynewwebsite.pages.NRGNSOffice365LicenseQuantityPage;
import com.netregistrynewwebsite.pages.NRGNSOrderCompletePage;
import com.netregistrynewwebsite.pages.NRGNSRegistrantContactPage;
import com.netregistrynewwebsite.pages.NRGNSReviewAndPaymentPage;
import com.netregistrynewwebsite.pages.NRGNSSearchAddDomainsPage;
import com.netregistrynewwebsite.pages.NRGNSSearchFieldPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class RegressionNewShoppingCartTest extends TestBase{
	
	//Netregistry New Shopping Cart Pages
	NRGNSAboutYouPage nrgnsaboutyoupage;
	NRGNSAddServicesToYourDomainPage nrgnsaddservicestoyourdomainpage;
	NRGNSDomainPrivacyPage nrgnsdomainprivacypage;
	NRGNSEmailAndOffice365PackagesPage nrgnsemailandoffice365packagespage;
	NRGNSOffice365LicenseQuantityPage nrgnsoffice365licensequantitypage;
	NRGNSRegistrantContactPage nrgnsregistrantcontactpage;
	NRGNSReviewAndPaymentPage nrgnsreviewandpaymentpage;
	NRGNSSearchAddDomainsPage nrgnssearchadddomainspage;
	NRGNSSearchFieldPage nrgnssearchfieldpage;
	NRGNSOrderCompletePage nrgnsordercompletepage;
	
	TestUtil testUtil;
	static Environment testenvironment;
	public static ExtentTest logger;

	public RegressionNewShoppingCartTest() {
		super();
	}
			
	@Parameters({"environment", "iteration"})
	@Test
	public void testDomainOrderInNewShoppingCartForNewBTCustomerUsingNewCard (String environment, Integer iteration) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		String strCardOwnerName = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
				
	    Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {
	    
		    //Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);
			
			if (environment.equals("stagingdev-5")) {
				strTld = ".com";
				strCardOwnerName = "Test Braintree New Customer";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			else if (environment.equals("prod")) {
				strTld = ".com";
				strCardOwnerName = "MELBOURNE IT LTD";
				strCardNumber = "4715276659601714";
			    strCardExpiryMonth = "06";
			    strCardExpiryYear = "2021";
			    strCardSecurityCode = "557";
			}
			
			System.out.println("Start Test: testDomainOrderInNewShoppingCartForNewBTCustomerUsingNewCard");
					
			//Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			
			//Test Step 2: Process the order without any product included
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			
			//Test Step 3: Input default customer details  
			nrgnsaboutyoupage.setDefaultCustomerDetails();
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickContinueButton();
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			
			//Test Step 4: Input customer credit card details and complete the order
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			
			//Test Step 5: Verify if recaptcha challenge is dislayed 
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			
			//driver.close();
			System.out.println("End Test: testDomainOrderInNewShoppingCartForNewBTCustomerUsingNewCard");
		}	
	}
	

	@Parameters({"environment", "iteration"})
	@Test
	public void testDomainOrderInNewShoppingCartForExistingBTCustomerUsingNewCard (String environment, Integer iteration) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		
		String strCardOwnerName = null;
		String strCardNumber = null;
	    String strCardExpiryMonth = null;
	    String strCardExpiryYear = null;
	    String strCardSecurityCode = null;
	    
	    String strCustomerAccountReference = null;
	    String strCustomerPassword = null;
		
		
	    Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {
	    
		    //Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);
			
			if (environment.equals("stagingdev-5")) {
				
				strTld = ".com";			    
			    strCardOwnerName = "Test Braintree Existing Customer";
				strCardNumber = "4111111111111111";
			    strCardExpiryMonth = "05";
			    strCardExpiryYear = "2028";
			    strCardSecurityCode = "331";
			    
//			    strCustomerAccountReference = "NET-1273";
//			    strCustomerPassword = "comein22";
			    
				strCustomerAccountReference = "NET-1290";
				strCustomerPassword = "mjj6hrex8";
			    
			}
			else if (environment.equals("prod")) {
				
			    strTld = ".com";			    
			    strCardOwnerName = "MELBOURNE IT LTD F SHARED SERVICES";
				strCardNumber = "4715276659101053";
			    strCardExpiryMonth = "08";
			    strCardExpiryYear = "2020";
			    strCardSecurityCode = "390";

			    strCustomerAccountReference = "CAP-1059";
			    strCustomerPassword = "comein22";
			}
			
			System.out.println("Start Test: testDomainOrderInNewShoppingCartForExistingBTCustomerUsingNewCard");
					
			//Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			
			//Test Step 2: Process the order without any product included
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			
			//Test Step 3: Login as returning or existing netregistry customer 
			nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			
			//Test Step 4: Input new credit card details and complete the order
			nrgnsreviewandpaymentpage.selectNewCreditCardOption();
		    nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
		    nrgnsreviewandpaymentpage.tickTermsAndConditions();
		    nrgnsreviewandpaymentpage.clickCompleteOrder();
		
			//Test Step 5: Verify if recaptcha challenge is dislayed 
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			
			//driver.close();
			System.out.println("End Test: testDomainOrderInNewShoppingCartForExistingBTCustomerUsingNewCard");
		}	
	}
	
	@Parameters({"environment", "iteration"})
	@Test
	public void testDomainOrderInNewShoppingCartForExistingBTCustomerUsingExistingCard (String environment, Integer iteration) throws InterruptedException{
		
		// Initialization (Test Data Creation and Assignment)
		String strDomainName = null;
		String strTld = null;
		String strWorkflowId = null;
		String strAccountReference = null;
		
		String strMaskedCardNumber = null;
	    String strCustomerAccountReference = null;
	    String strCustomerPassword = null;
		
		
	    Integer intMaxCount = iteration;
		Integer intMinCount = null;
		for(intMinCount = 1; intMinCount<=intMaxCount; intMinCount++) {
	    
		    //Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);
			
			if (environment.equals("stagingdev-5")) {
				
				strTld = ".com";			    
				strMaskedCardNumber = "************4444";
				
//			    strCustomerAccountReference = "NET-1273";
//			    strCustomerPassword = "comein22";
			   
				strCustomerAccountReference = "NET-1290";
				strCustomerPassword = "mjj6hrex8";
			    
			}
			else if (environment.equals("prod")) {
				
				strTld = ".com";			    
				strMaskedCardNumber = "************4444";
				
			    strCustomerAccountReference = "CAP-1059";
			    strCustomerPassword = "comein22";	
			}
			
			System.out.println("Start Test: testDomainOrderInNewShoppingCartForExistingBTCustomerUsingExistingCard");
					
			//Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			
			//Test Step 2: Process the order without any product included
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			
			//Test Step 3: Login as returning or existing netregistry customer  
			nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			
			//Test Step 4: Select existing credit card and complete the order
			nrgnsreviewandpaymentpage.selectExistingCreditCard(strMaskedCardNumber);
		    nrgnsreviewandpaymentpage.tickTermsAndConditions();
			nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();
			
			//Test Step 5: Verify if the order is completed, get workflow id and account reference.
			Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
			strWorkflowId = nrgnsordercompletepage.getSingleReferenceID();
			strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
			System.out.println("Account Reference:" + strAccountReference);	
			System.out.println("Reference ID[0]:" + strWorkflowId);	
			
			//driver.close();
			System.out.println("End Test: testDomainOrderInNewShoppingCartForExistingBTCustomerUsingExistingCard");
		}	
	}
	
}
