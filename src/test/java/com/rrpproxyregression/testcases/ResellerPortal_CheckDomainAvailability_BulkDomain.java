package com.rrpproxyregression.testcases;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.tppresellerportal.pages.TPPBulkRegisterPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPRegisterADomainPage;
import com.tppresellerportal.pages.TPPTabPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.tppresellerportal.pages.TPPBulkRegisterPage;
import com.util.TestUtil;

public class ResellerPortal_CheckDomainAvailability_BulkDomain extends TestBase{
	
	TPPBulkRegisterPage tppbulkregisterpage;
	TPPLoginPage tpploginpage;
	TPPRegisterADomainPage tppregisterdomainpage;
	TPPTabPage tpptabpage;
	TestUtil testUtil;
	public static ExtentTest logger;

	public ResellerPortal_CheckDomainAvailability_BulkDomain() {
		super();
	}
		@Parameters({ "environment", "accountReference" })
		@Test
		public void verifyDomainNameAvailableForRegistration(String environment, String accountReference)
				throws Exception {
			// Initialization (Test Data Creation and Assignment)
			String[] strDomainName = new String[] { "test1.com.au\n", "test2.com.au\n", "test4.com.au\n" };

			String strAccountreference = "TPP-60053";
			String strPassword = "comein22";

			// Test Step 1: Login to Reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: checkBulkDomainAvailability");
			initialization(environment, "resellerportalurl_tpp");

			tppbulkregisterpage = new TPPBulkRegisterPage();
			tpploginpage = new TPPLoginPage();
			tppregisterdomainpage = new TPPRegisterADomainPage();
			tpptabpage = new TPPTabPage();

			tpploginpage.setLoginDetails(strAccountreference, strPassword);
			tpploginpage.clickLoginButton();

			// Test Step 2: Navigate to Bulk Domain Register
			test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");

			tpptabpage.clickBulkRegisterLink();
			tppbulkregisterpage.setAllDomainNames(strDomainName);

			tppbulkregisterpage.clickSearchButton();

			// Test Step 3: verify domain available for Registraion
			test.log(LogStatus.INFO, "verify domain available for Registraion");

			for (int i = 0; i < strDomainName.length; i++) {
				Assert.assertEquals(tppbulkregisterpage.getSearchAvailabilityMessage(strDomainName[i], i), "Available",
						"Available");
			}

			driver.close();
			System.out.println("End Test: vVerifyDomainNameAvailableForRegistration");
		}

		@Parameters({ "environment","accountReference" })
		@Test
		public void verifyDomainNameNotAvailableForRegistration(String environment,
				String accountReference) throws Exception {
			// Initialization (Test Data Creation and Assignment)
			String[] strDomainName = new String[] { "test1.com\n", "test.com.au\n", "test2.com.au" };

			String strAccountreference = "TPP-60053";
			String strPassword = "comein22";

			// Test Step 1: Login to Reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: checkBulkDomainAvailability");
			initialization(environment, "resellerportalurl_tpp");

			tppbulkregisterpage = new TPPBulkRegisterPage();
			tpploginpage = new TPPLoginPage();
			tppregisterdomainpage = new TPPRegisterADomainPage();
			tpptabpage = new TPPTabPage();

			tpploginpage.setLoginDetails(strAccountreference, strPassword);
			tpploginpage.clickLoginButton();

			// Test Step 2: Navigate to Bulk Domain Register
			test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");
			tpptabpage.clickBulkRegisterLink();
			tppbulkregisterpage.setAllDomainNames(strDomainName);

			tppbulkregisterpage.clickSearchButton();

			// Test Step 3: verify domain is not available for Registration
			test.log(LogStatus.INFO, "verify domain available for Registraion");
			for (int i = 0; i < strDomainName.length; i++) {
				Assert.assertEquals(tppbulkregisterpage.getSearchNotAvailabilityMessage(strDomainName[i], i),
						"Not supported", "	Not supported");
			}

			driver.close();
			System.out.println("End Test: VerifyDomainNameNotAvailableForRegistration");
		}

		@Parameters({ "environment", "accountReference" })
		@Test
		public void verifyDomainNameAvailableForRegistrationAndPremium(String environment,
				String accountReference) throws Exception {
			// Initialization (Test Data Creation and Assignment)
			String[] strDomainName = new String[] { "lol.sydney\n", "test.sydney\n", "herb.melbourne" };

			String strAccountreference = "TPP-60053";
			String strPassword = "comein22";

			// Test Step 1: Login to Reseller portal
			test.log(LogStatus.INFO, "Login to Reseller portal");
			System.out.println("Start Test: checkBulkDomainAvailability");
			initialization(environment, "resellerportalurl_tpp");

			tppbulkregisterpage = new TPPBulkRegisterPage();
			tpploginpage = new TPPLoginPage();
			tppregisterdomainpage = new TPPRegisterADomainPage();
			tpptabpage = new TPPTabPage();

			tpploginpage.setLoginDetails(strAccountreference, strPassword);
			tpploginpage.clickLoginButton();

			// Test Step 2: Navigate to Bulk Domain Register
			test.log(LogStatus.INFO, "Navigate to Bulk Domain Register");

			tpptabpage.clickBulkRegisterLink();
			tppbulkregisterpage.setAllDomainNames(strDomainName);

			tppbulkregisterpage.clickSearchButton();

			// Test Step 3: verify domain is available for Registraion And Premium
			test.log(LogStatus.INFO, "verify domain available for Registraion");

			for (int i = 0; i < strDomainName.length; i++) {
				Assert.assertEquals(tppbulkregisterpage.getSearchPremiumMessage(strDomainName[i], i), "Premium", "Premium");
			}

			driver.close();
			System.out.println("End Test: VerifyDomainNameAvailableForRegistrationAndPremium");

		}

	}
