package com.o365smui.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.consoleadmin.pages.CADomainLevelPage;
import com.consoleadmin.pages.CAHeaderPage;
import com.consoleadmin.pages.CALoginPage;
import com.consoleadmin.pages.CAWorkflowAdminPage;
import com.consolesmui.pages.CSMUIManageO365MainPage;
import com.consolesmui.pages.CSMUITabPage;
import com.consolesmui.pages.CSMUIUpdateAdminAccountPage;
import com.relevantcodes.extentreports.ExtentTest;
import com.util.TestUtil;

public class TestOffice365UserAccountManagementInSMUI extends TestBase {

	// Console Admin Pages
	CALoginPage caloginpage;
	CAHeaderPage caheaderpage;
	CAWorkflowAdminPage caworkflowadminpage;
	CADomainLevelPage cadomainlevelpage;
	CSMUITabPage csmuitabpage;
	CSMUIManageO365MainPage csmuimanageo365mainpage;
	CSMUIUpdateAdminAccountPage csmuiupdateadminaccountpage;

	TestUtil testUtil;
	public static ExtentTest logger;

	public TestOffice365UserAccountManagementInSMUI() {
		super();
	}

	@Parameters({ "environment" })
	@Test
	public void testOffice365UserAccountManagementInSMUI(String environment) throws InterruptedException {

		// Initialization (Test Data Creation and Assignment)
		String strDomainName = "testoffice365smui.com";

		// Test Step 1: Login to console and search for domain
		System.out.println("Start Test: TestOffice365UserAccountManagementInSMUI");
		initialization(environment, "consoleadmin");

		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		cadomainlevelpage = caheaderpage.searchDomain(strDomainName);
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();
		csmuimanageo365mainpage = csmuitabpage.clickOffice365Tab();

		// Test Step 7: Verify if username for admin portal is o365-admin@[domainname]
		Assert.assertTrue(csmuimanageo365mainpage.verifyAdminEmail(strDomainName));

		// Test Step 8: Verify if user admin can edit details and reset a new password.
		// Edit details
		csmuiupdateadminaccountpage = csmuimanageo365mainpage.clickAdminUpdateLink();
		csmuiupdateadminaccountpage.updateAdminDetails("newFirstName", "newFirstName", "New Admin Name");
		Assert.assertEquals(csmuiupdateadminaccountpage.getEditDetailsSuccessMessage(),
				"Your changes have been saved successfully.", "Verify successful edit details message.");

		// Reset password
		csmuiupdateadminaccountpage.resetPassword("P@ssw0rd123", "P@ssw0rd123");
		Assert.assertEquals(csmuiupdateadminaccountpage.getResetPasswordSuccessMessage(),
				"Password successfully changed.", "Verify successful password changed message.");

	}
}