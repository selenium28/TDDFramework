package com.rrpproxyregression.testcases;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.base.TestBase;
import com.tppresellerportal.pages.TPPDomainTransferPage;
import com.tppresellerportal.pages.TPPLoginPage;
import com.tppresellerportal.pages.TPPTabPage;

public class ResellerPortal extends TestBase {
	
	//console tpp pages
	TPPLoginPage tpploginpage;
	TPPTabPage tpptabpage;
	TPPDomainTransferPage tppdomaintransferpage;
	
	String strPassword = null;
	String domainPrefix = null;
	String authCode = null;

	public ResellerPortal(){
		super();
	}
	
	@Parameters({"environment", "namespace", "accountReference"})
	@Test
	public void checkDomainTransferInResellerPortal(String environment, String namespace, String accountReference) throws InterruptedException, IOException, AWTException {
		
		//Test Step 1: Login to tpp reseller portal, navigate to domain transfer page and verify domain transfer status
		initialization(environment, "tppresellerloginurl");
		
		 strPassword = "comein22";
		 domainPrefix = "test-domain-check48";
		 authCode = "C076%h14$V90";
		
		tpploginpage = new TPPLoginPage();
		tpploginpage.setLoginDetails(accountReference, strPassword);
		tpptabpage = new TPPTabPage();
		tpptabpage = tpploginpage.clickLoginButton();
		tpptabpage.clickDomainsTab();
		tppdomaintransferpage = new TPPDomainTransferPage();
		tppdomaintransferpage = tpptabpage.clickDomainTransferLink();
		
		tppdomaintransferpage.enterDomainPrefix(domainPrefix);
		tppdomaintransferpage.selectDomainNamespace(namespace);
		tppdomaintransferpage.enterAuthCode(authCode);
		tppdomaintransferpage.clickOnAddLink();
		tppdomaintransferpage.verifyDomainStatus();
		driver.close();
	}
}
