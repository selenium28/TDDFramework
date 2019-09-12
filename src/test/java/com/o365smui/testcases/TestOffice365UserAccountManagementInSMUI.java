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
import com.consolesmui.pages.CSMUIUpdateUserAccountPage;
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
	CSMUIUpdateUserAccountPage csmuiupdateuseraccountpage;
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
		String username = "ggg";
		String firstName = "test";
		String lastName = "zzz";
		String displayName = "test";
		String password = "passW0rd";
		String confirmPassword = "passW0rd";
		String strLicenceType = "Business Premium - Email and Office";
		String strUserDetails = username + "@" + strDomainName;

		String eUsername = "ggg1";
		String eFirstName = "test1";
		String eLastName = "zzz1";
		String eDisplayName = "test1";
		String newPassword = "passW0rd1";
		String repeatPassword = "passW0rd1";
		String strNewLicenceType = "Email Essentials - Email Only";

		// Test Step 1: Login to console and search for domain
		System.out.println("Start Test: TestOffice365UserAccountManagementInSMUI");
		initialization(environment, "consoleadmin");

		caloginpage = new CALoginPage();
		caheaderpage = caloginpage.setDefaultLoginDetails(environment);
		cadomainlevelpage = caheaderpage.searchDomain(strDomainName);
		csmuitabpage = cadomainlevelpage.clickloginAsClientLink();

		// Test Step 2: Login to office365 smui
		csmuimanageo365mainpage = csmuitabpage.clickOffice365Tab();

		// Test Step 3: provide user details
		csmuimanageo365mainpage = csmuimanageo365mainpage.clickCreateUser();
		csmuimanageo365mainpage = csmuimanageo365mainpage.setUserAccountDetails(username, firstName, lastName,
				displayName, password, confirmPassword);

		// Test Step 4: Select Licence type
		csmuimanageo365mainpage = csmuimanageo365mainpage.setSelectLicence(strLicenceType);

		// Test Step 5: Verify if New account created successfully
		csmuimanageo365mainpage = csmuimanageo365mainpage.clickCreateEmail();
		Assert.assertTrue(csmuimanageo365mainpage.isAccountCreated());

		// Test step6: Verify if user details updated successfully
		csmuiupdateuseraccountpage = csmuimanageo365mainpage.clickUserUpdateLink(strUserDetails);
		csmuiupdateuseraccountpage.editUserAccountDetails(eUsername, eFirstName, eLastName, eDisplayName);
		csmuiupdateuseraccountpage.clickSave();
		Assert.assertTrue(csmuiupdateuseraccountpage.isAccountUpdated());

		// Test step7: Verify if user pwd details updated successfully
		csmuiupdateuseraccountpage.editUserPwdDetails(newPassword, repeatPassword);
		csmuiupdateuseraccountpage.clickReset();
		Assert.assertTrue(csmuiupdateuseraccountpage.isPwdUpdated());

		// Test step8: Verify if user Licence details updated successfully
		csmuiupdateuseraccountpage.setNewLicence(strNewLicenceType);
		csmuiupdateuseraccountpage.clickLicenceSave();
		Assert.assertTrue(csmuiupdateuseraccountpage.isLicenceUpdated());

		csmuimanageo365mainpage = csmuiupdateuseraccountpage.clickGoBackButton();

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