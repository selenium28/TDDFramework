package com.newcartregression.testcases;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

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
import com.relevantcodes.extentreports.LogStatus;
import com.util.TestUtil;

public class TestExistingCustomerScenarioUsingNewCard extends TestBase {

	// Netregistry New Shopping Cart Pages
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
	
		

	public TestExistingCustomerScenarioUsingNewCard() {
		super();
	}
	
	/*
	 * @BeforeMethod public void registerMethod(Method method) {
	 * test=report.startTest(method.getName()); test.log(LogStatus.INFO, "Test"
	 * +method.getName()+" has been started"); }
	 */

	@Parameters({ "environment", "iteration" })
	@Test
	public void testExistingCustomerScenarioUsingNewCard(String environment, Integer iteration) throws Exception {
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
		for (intMinCount = 1; intMinCount <= intMaxCount; intMinCount++) {

			// Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);

			if (environment.equals("dev2")) {

				strTld = ".com";
				strCardOwnerName = "Test Existing Customer New Card";
				strCardNumber = "5555555555554444";
				strCardExpiryMonth = "10";
				strCardExpiryYear = "2026";
				strCardSecurityCode = "123";

				strCustomerAccountReference = "TES-2168";
				strCustomerPassword = "comein22";

			}
			if (environment.equals("uat1")) {

				strTld = ".com";
				strCardOwnerName = "Test Existing Customer New Card";
				strCardNumber = "5555555555554444";
				strCardExpiryMonth = "10";
				strCardExpiryYear = "2026";
				strCardSecurityCode = "123";

				strCustomerAccountReference = "TES-2168";
				strCustomerPassword = "comein22";

			} else if (environment.equals("ote")) {

				strTld = ".com";
				strCardOwnerName = "Test Existing Customer New Card";
				strCardNumber = "5555555555554444";
				strCardExpiryMonth = "10";
				strCardExpiryYear = "2026";
				strCardSecurityCode = "123";

				strCustomerAccountReference = "MEL-6007";
				strCustomerPassword = "comein22";
			} else if (environment.equals("prod")) {

				strTld = ".com";
				strCardOwnerName = "Test Existing Customer New Card";
				strCardNumber = "5555555555554444";
				strCardExpiryMonth = "10";
				strCardExpiryYear = "2026";
				strCardSecurityCode = "123";

				strCustomerAccountReference = "MEL-6007";
				strCustomerPassword = "comein22";
			}

			System.out.println("Start Test: testExistingCustomerScenarioUsingNewCard");

			// Test Step 1: Navigate to domain search page of new shopping cart and place an
			// order for a test domain
			test.log(LogStatus.INFO, "Navigate to domain search page -STARTED");
			
			initialization(environment, "newcart_domainsearchurl_netregistry");
			nrgnssearchadddomainspage = new NRGNSSearchAddDomainsPage();
			nrgnssearchadddomainspage.setDomainNameAndTld(strDomainName, strTld);
			nrgnssearchadddomainspage.clickSearchButton();
			nrgnssearchadddomainspage.addDomainName(strDomainName, strTld);
			nrgnsdomainprivacypage = nrgnssearchadddomainspage.clickContinueButton();
			testStepResultVerification(NRGNSDomainPrivacyPage.checkBox);
			
			test.log(LogStatus.INFO, "Navigate to domain search page -COMPLETED");

			// Test Step 2: Process the order without any product included
			test.log(LogStatus.INFO, "Process the order page -STARTED");
			
			nrgnsdomainprivacypage.clickCheckBox();
			nrgnsemailandoffice365packagespage = nrgnsdomainprivacypage.clickContinueButton();
			nrgnsaddservicestoyourdomainpage = nrgnsemailandoffice365packagespage.clickContinueButton();
			nrgnsaboutyoupage = nrgnsaddservicestoyourdomainpage.clickContinueButton();
			testStepResultVerification(NRGNSAboutYouPage.loginButton);
			
			test.log(LogStatus.INFO, "Process the order page -COMPLETED");  

			// Test Step 3: Login as returning or existing netregistry customer
			test.log(LogStatus.INFO, " Login as returning customer -STARTED");
			
			nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();

			// Special Case: Wait for 10s and refresh page before continuing (Issue is
			// raised to Developers to investigae why page is not loading quickly)
			//nrgnsregistrantcontactpage.refreshRegistrantPage();

			nrgnsregistrantcontactpage.clickDomainInformation("Have a business idea and reserving a domain for the future");
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();
			testStepResultVerification(NRGNSReviewAndPaymentPage.agreeTermsAndConditions);
			
			test.log(LogStatus.INFO, "Login as returning customer -COMPLETED"); 


			// Test Step 4: Input new credit card details and complete the order
			test.log(LogStatus.INFO, "Input new credit card details -STARTED");
			
			nrgnsreviewandpaymentpage.selectNewCreditCardOption();
			nrgnsreviewandpaymentpage.setBTFormCreditCardDetails(strCardOwnerName, strCardNumber, strCardExpiryMonth,
					strCardExpiryYear, strCardSecurityCode);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			nrgnsreviewandpaymentpage.clickCompleteOrder();
			
			test.log(LogStatus.INFO, "Input new credit card details -COMPLETED"); 


			// Test Step 5: Verify if recaptcha challenge is dislayed
			test.log(LogStatus.INFO, "Verify if recaptcha challenge -STARTED");
			
			Assert.assertTrue(nrgnsreviewandpaymentpage.isReCaptchaChallengeDisplayed(),
			"Recaptcha Challenge is not displayed");
			testStepResultVerification(NRGNSReviewAndPaymentPage.recaptchaChallenge);
			
			test.log(LogStatus.INFO, "Verify if recaptcha challenge -COMPLETED"); 

			driver.close();
		
			System.out.println("End Test: testExistingCustomerScenarioUsingNewCard");
		}
	}

}
