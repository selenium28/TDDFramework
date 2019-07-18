package com.newcartregression.testcases;

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

public class TestExistingCustomerScenarioUsingExistingCard extends TestBase{
	
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

	public TestExistingCustomerScenarioUsingExistingCard() {
		super();
	}
			

	@Parameters({"environment", "iteration"})
	@Test
	public void testExistingCustomerScenarioUsingExistingCard (String environment, Integer iteration) throws InterruptedException{
		
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
				
				strCustomerAccountReference = "MEL-6007";
				strCustomerPassword = "comein22";
			    
			}else if (environment.equals("uat1")) {
				
				strTld = ".com";			    
				strMaskedCardNumber = "************4444";
				
			    strCustomerAccountReference = "TES-2168";
			    strCustomerPassword = "comein22";	
			}
			else if (environment.equals("prod")) {
				
				strTld = ".com";			    
				strMaskedCardNumber = "************4444";
				
			    strCustomerAccountReference = "CAP-1059";
			    strCustomerPassword = "comein22";	
			}
			
			System.out.println("Start Test: testCaptchaForExistingCustomerUsingExistingCard");
					
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
			
			//Special Case: Wait for 10s and refresh page before continuing (Issue is raised to Developers to investigae why page is not loading quickly)
			nrgnsregistrantcontactpage.refreshRegistrantPage();
			
			nrgnsregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
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
			
			driver.close();
			System.out.println("End Test: testCaptchaForExistingCustomerUsingExistingCard");
		}	
	}
	
}
