package com.newcartregression.testcases;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
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

public class TestExistingCustomerScenarioUsingExistingCard extends TestBase {

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

	public TestExistingCustomerScenarioUsingExistingCard() {
		super();
	}

	/*
	 * @BeforeMethod public void registerMethod(Method method) {
	 * test=report.startTest(method.getName()); test.log(LogStatus.INFO,
	 * "Test"+method.getName()+" has been started"); }
	 */

	@Parameters({ "environment", "iteration" })
	@Test
	public void testExistingCustomerScenarioUsingExistingCard(String environment, Integer iteration) throws Exception {

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

		for (intMinCount = 1; intMinCount <= intMaxCount; intMinCount++) {

			// Generate test domain name
			DateFormat df = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date d = new Date();
			strDomainName = "TestConsoleRegression" + df.format(d);

			if (environment.equals("dev2")) {
				strTld = ".com";
				strMaskedCardNumber = "************4444";

				strCustomerAccountReference = "TES-2168";
				strCustomerPassword = "comein22";
			}
			if (environment.equals("uat1")) {

				strTld = ".com";
				strMaskedCardNumber = "************4444";

				strCustomerAccountReference = "TES-2168";
				strCustomerPassword = "comein22";

			} else if (environment.equals("ote")) {

				strTld = ".com";
				strMaskedCardNumber = "************1053";

				strCustomerAccountReference = "MEL-6007";
				strCustomerPassword = "comein22";
			} else if (environment.equals("prod")) {

				strTld = ".com";
				strMaskedCardNumber = "************1053";

				strCustomerAccountReference = "MEL-6007";
				strCustomerPassword = "comein22";
			}

			System.out.println("Start Test: testExistingCustomerScenarioUsingExistingCard");
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
			test.log(LogStatus.INFO, "Process the order page  -COMPLETED");

			// Test Step 3: Login as returning or existing netregistry customer
			test.log(LogStatus.INFO, "Login as returning or existing netregistry customer -STARTED");
			nrgnsaboutyoupage.setReturningCustomerContacts(strCustomerAccountReference, strCustomerPassword);
			nrgnsregistrantcontactpage = nrgnsaboutyoupage.clickLoginButton();
			testStepResultVerification(NRGNSRegistrantContactPage.selectButton);
			test.log(LogStatus.INFO, "Login as returning or existing netregistry customer  -COMPLETED");
			nrgnsregistrantcontactpage
					.clickDomainInformation("Have a business idea and reserving a domain for the future");
			nrgnsreviewandpaymentpage = nrgnsregistrantcontactpage.clickSelectButton();

			// Test Step 4: Select existing credit card and complete the order
			test.log(LogStatus.INFO, "Select existing credit card -STARTED");
			nrgnsreviewandpaymentpage.selectExistingCreditCard(strMaskedCardNumber);
			nrgnsreviewandpaymentpage.tickTermsAndConditions();
			testStepResultVerification(NRGNSReviewAndPaymentPage.completeOrderButton);
			test.log(LogStatus.INFO, "Select existing credit card  -COMPLETED");

			if (environment.equals("uat1") || environment.equals("dev2")) {
				nrgnsordercompletepage = nrgnsreviewandpaymentpage.clickCompleteOrder();			

				// Test Step 5: Verify if the order is completed, get workflow id and account
				// reference.
				test.log(LogStatus.INFO, "Verify if the order is completed -STARTED");
				Assert.assertTrue(nrgnsordercompletepage.isOrderComplete(), "Order is not completed");
				strWorkflowId = nrgnsordercompletepage.getSingleReferenceID();
				testStepResultVerification(NRGNSOrderCompletePage.accountReferenceElement);
				test.log(LogStatus.INFO, "Verify if the order is completed  -COMPLETED");

				strAccountReference = nrgnsordercompletepage.getAccountReferenceID();
				System.out.println("Account Reference:" + strAccountReference);
				System.out.println("Reference ID[0]:" + strWorkflowId);

			}
			
			driver.quit();
			System.out.println("End Test: testExistingCustomerScenarioUsingExistingCard");
			
		}
	}
}
