//package com.paymentgateway.uat.netregistry.testcases;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.How;
//import org.testng.Assert;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.base.TestBase;
//import com.domainzwebsite.pages.DMZLoginPage;
//import com.netregistryoldwebsite.pages.NRGAccountPage;
//import com.netregistryoldwebsite.pages.NRGBillingPage;
//import com.netregistryoldwebsite.pages.NRGCreditCardsDetailsPage;
//import com.netregistryoldwebsite.pages.NRGHeaderPage;
//import com.netregistryoldwebsite.pages.NRGLoginPage;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.util.TestUtil;
//
//public class RegressionSMUI extends TestBase {
//
//	// Netregistry shopping cart pages
//	NRGLoginPage nrgloginpage;
//	NRGAccountPage nrgaccountpage;
//	NRGBillingPage nrgbillingpage;
//	NRGCreditCardsDetailsPage nrgcreditcardsdetailspage;
//	NRGHeaderPage nrgheaderpage;
//	NRGPrepaidAccountPage nrgprepaidaccountpage;
//
//	// objects
//	@FindBy(how = How.LINK_TEXT, using = "Account")
//	WebElement lnkAccount;
//
//	TestUtil testUtil;
//	public static ExtentTest logger;
//
//	String strAccountReference = null;
//	String strPassword = null;
//	String strCardOwner = null;
//	String strCardType = null;
//	String strCardNumber = null;
//	String strCardExpiryMonth = null;			
//	String strCardExpiryYear = null;
//	String strCardSecurityCode = null;
//	String strAmount = null;
//	
//	public RegressionSMUI() {
//		super();
//	}
//
//	
//	@Parameters({"environment", "paymentgateway"})
//	@Test
//	public void testAddNewCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			strAccountReference = "";
//			strPassword = "";
//			strCardOwner = "";
//			strCardType = "";
//			strCardNumber = "";
//			strCardExpiryMonth = "";
//			strCardExpiryYear = "";
//			strCardSecurityCode = "";		
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			strAccountReference = "DOM-1311";
//			strPassword = "comein22";
//			strCardOwner = "Test Mastercard";
//			strCardType = "MasterCard";
//			strCardNumber = "5555555555554444";
//			strCardExpiryMonth = "05";
//			strCardExpiryYear = "2020";
//			strCardSecurityCode = "123";	
//		}
//		
//		//Test Step 1: Login to customer portal
//		initialization(environment, "customerportalurl_netregistry");
//		nrgloginpage = new NRGLoginPage();
//		nrgloginpage.setLoginDetails(strAccountReference, strPassword);
//		nrgheaderpage = nrgloginpage.clickLoginButton();
//		nrgaccountpage = nrgheaderpage.clickAccountTab();
//		nrgcreditcardsdetailspage = nrgaccountpage.clickEditCreditCardsOnFile();
//		
//		//Test Step 2: Add new credit card
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			//dmzcreditcardsdetailspage.setQuestFormCreditCardDetails(strCardOwner, strCardType, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			nrgcreditcardsdetailspage.setBTFormCreditCardDetails(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear, strCardSecurityCode);
//		}
//
//		nrgbillingpage = nrgcreditcardsdetailspage.clickAddCreditCard();
//		Assert.assertTrue(nrgbillingpage.isReCaptchaChallengeDisplayed(), "Recaptcha Challenge is not displayed");
//		driver.close();
//	}
//
//	
//	@Parameters({"environment", "paymentgateway"})
//	@Test
//	public void testModifyExistingCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			strAccountReference = "DOM-1218";
//			strPassword = "comein22";
//			strCardExpiryMonth = "04";
//			strCardExpiryYear = "2021";
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			strAccountReference = "DOM-1311";
//			strPassword = "comein22";
//			strCardExpiryMonth = "04";
//			strCardExpiryYear = "2021";
//		}
//
//		//Test Step 1: Login to customer portal
//		initialization(environment, "customerportalurl_domainz");
//		dmzloginpage = new DMZLoginPage();
//		dmzloginpage.setLoginDetails(strAccountReference, strPassword);
//		dmzheaderpage = dmzloginpage.clickLoginButton();
//		dmzaccountpage = dmzheaderpage.clickAccountTab();
//		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
//
//		// Modify existing card details
//		dmzcreditcardsdetailspage.clickOnExistingCard();
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			//dmzcreditcardsdetailspage.modifyCreditCardDetails();
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {			
//			dmzcreditcardsdetailspage.modifyCreditCardDetailsBT(strCardExpiryMonth, strCardExpiryYear);
//		}
//		
//		Assert.assertEquals(dmzcreditcardsdetailspage.getConfirmationMessage(), "The credit card has successfully been modified", 
//			dmzcreditcardsdetailspage.getConfirmationMessage());	
//		driver.close();
//	}
//
//	
//	@Parameters({ "environment", "paymentgateway" })
//	@Test
//	public void testMakeCreditCardDefaultInSMUI(String environment, String paymentgateway) throws InterruptedException {
//		
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			strAccountReference = "DOM-1218";
//			strPassword = "comein22";
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			strAccountReference = "DOM-1311";
//			strPassword = "comein22";
//		}
//
//		initialization(environment, "customerportalurl_domainz");
//		dmzloginpage = new DMZLoginPage();
//		dmzloginpage.setLoginDetails(strAccountReference, strPassword);
//		dmzheaderpage = dmzloginpage.clickLoginButton();
//		dmzaccountpage = dmzheaderpage.clickAccountTab();
//		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
//		dmzaccountpage.makeCardDefault();
//		driver.close();
//	}
//	
//
////	@Parameters({ "environment", "paymentgateway" })
////	@Test
////	public void testDeleteCreditCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
////		initialization(environment, "cartlogin");
////		dmzloginpage = new DMZLoginPage();
////		dmzaccountpage = new DMZAccountPage();
////		dmzbillingpage = new DMZBillingPage();
////		dmzcreditcardsdetailspage = new DMZCreditCardsDetailsPage();
////
////		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
////			straccountreference = "DOM-1218";
////			dmzloginpage.setLoginDetails(straccountreference, strpassword);
////		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
////			straccountreference = "DOM-1311";
////			dmzloginpage.setLoginDetails(straccountreference, strpassword);
////		}
////
////		dmzheaderpage = dmzloginpage.clickLoginButton();
////		dmzaccountpage = dmzheaderpage.clickAccountTab();
////		dmzcreditcardsdetailspage = dmzaccountpage.clickEditCreditCardsOnFile();
////		dmzcreditcardsdetailspage.deleteCreditCard();
////
////		driver.close();
////
////	}
//	
//	@Parameters({ "environment", "paymentgateway" })
//	@Test
//	public void testRechargePrepaidUsingExistingCardInSMUI(String environment, String paymentgateway) throws InterruptedException {
//
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			strAccountReference = "DOM-1218";
//			strPassword = "comein22";
//			strCardOwner = "Test Mastercard";
//			strCardType = "MasterCard";
//			strCardNumber = "5454545454545454";
//			strCardExpiryMonth = "02";
//			strCardExpiryYear = "2020";
//			strCardSecurityCode = "123";
//			strAmount = "10";
//
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			strAccountReference = "DOM-1311";
//			strPassword = "comein22";
//			strCardOwner = "Test Mastercard";
//			strCardType = "MasterCard";
//			strCardNumber = "5454545454545454";
//			strCardExpiryMonth = "02";
//			strCardExpiryYear = "2020";
//			strCardSecurityCode = "123";
//			strAmount = "10";			
//		}
//		
//		initialization(environment, "customerportalurl_domainz");
//		dmzloginpage = new DMZLoginPage();
//		dmzloginpage.setLoginDetails(strAccountReference, strPassword);
//		dmzheaderpage = dmzloginpage.clickLoginButton();	
//		dmzbillingpage = dmzheaderpage.clickBillingTab();
//		dmzprepaidaccountpage = dmzbillingpage.clickEditPrepaidAccountLink();
//		dmzprepaidaccountpage.clickRechargeUsingCreditCard();
//		dmzprepaidaccountpage.enterRechargeAmount(strAmount);
//		dmzprepaidaccountpage.clickSubmitButton();
//
//		driver.close();
//	}
//
//	@Parameters({ "environment", "paymentgateway" })
//	@Test
//	public void testRechargePrepaidUsingNewCardInSMUI(String environment, String paymentgateway)
//			throws InterruptedException {
//
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//			strAccountReference = "DOM-1218";
//			strPassword = "comein22";
//			strCardOwner = "Test Mastercard";
//			strCardType = "MasterCard";
//			strCardNumber = "5454545454545454";
//			strCardExpiryMonth = "02";
//			strCardExpiryYear = "2020";
//			strCardSecurityCode = "123";
//			strAmount = "10";
//
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			strAccountReference = "DOM-1311";
//			strPassword = "comein22";
//			strCardOwner = "Test Mastercard";
//			strCardType = "MasterCard";
//			strCardNumber = "5454545454545454";
//			strCardExpiryMonth = "02";
//			strCardExpiryYear = "2020";
//			strCardSecurityCode = "123";
//			strAmount = "10";			
//		}
//		
//		initialization(environment, "customerportalurl_domainz");
//		dmzloginpage = new DMZLoginPage();
//		dmzloginpage.setLoginDetails(strAccountReference, strPassword);
//		dmzheaderpage = dmzloginpage.clickLoginButton();	
//		dmzbillingpage = dmzheaderpage.clickBillingTab();
//		dmzprepaidaccountpage = dmzbillingpage.clickEditPrepaidAccountLink();
//		dmzprepaidaccountpage.clickRechargeUsingCreditCard();
//
//		dmzprepaidaccountpage.clickOnNewCreditCard();
//		if ((environment.equals("uat1")) && (paymentgateway.equals("quest"))) {
//		//	dmzprepaidaccountpage.setNewCreditCardDetailsQuest(cardowner, cardnumber, cardexpirymonth, cardexpiryyear,
//		//			cardsecuritycode);
//		} else if ((environment.equals("uat1")) && (paymentgateway.equals("braintree"))) {
//			dmzprepaidaccountpage.setNewCreditCardDetailsBT(strCardOwner, strCardNumber, strCardExpiryMonth, strCardExpiryYear,
//					strCardSecurityCode);
//		}
//		dmzprepaidaccountpage.enterRechargeAmount(strAmount);
//		dmzprepaidaccountpage.clickSubmitButton();
//
//		driver.close();
//
//	}
//	
//	
//	
//	
//	
//	
//}