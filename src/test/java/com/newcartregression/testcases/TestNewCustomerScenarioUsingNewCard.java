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

public class TestNewCustomerScenarioUsingNewCard extends TestBase{
	
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

	public TestNewCustomerScenarioUsingNewCard() {
		super();
	}
			
	@Parameters({"environment", "iteration"})
	@Test
	public void testNewCustomerScenarioUsingNewCard (String environment, Integer iteration) throws Exception{
		
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
			strDomainName = "TestNewCartRegression" + df.format(d);
			
			if (environment.equals("dev2")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			if (environment.equals("uat1")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			else if (environment.equals("ote")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			else if (environment.equals("prod")) {
				strTld = ".com";
				strCardOwnerName = "Test New Customer New Card";
				strCardNumber = "5555555555554444";
			    strCardExpiryMonth = "10";
			    strCardExpiryYear = "2026";
			    strCardSecurityCode = "123";
			}
			
			System.out.println("Start Test: testNewCustomerScenarioUsingNewCard");
					
			//Test Step 1: Navigate to domain search page of new shopping cart and place an order for a test domain
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			
			//Test Step 2: Process the order without any product included
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			
			//Test Step 3: Input default customer details  
			nrgnsaboutyoupage.setDefaultCustomerDetails();
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickContinueButton();
			nrgnsregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			
			//Test Step 4: Input customer credit card details and complete the order
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			
			//Test Step 5: Verify if recaptcha challenge is dislayed 
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
			
			driver.close();
			System.out.println("End Test: testNewCustomerScenarioUsingNewCard");
		}	
	}
	
	
}
