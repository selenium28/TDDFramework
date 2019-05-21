package com.base;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.util.TestUtil;
import com.util.WebEventListener;

public class TestBase{

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	static Environment testEnvironment;
	
	public TestBase(){

	}
	
	public static void initialization(String environment, String entrypoint){
		
		ConfigFactory.setProperty("env", environment);
		testEnvironment = ConfigFactory.create(Environment.class);
		System.out.println("Environment: "+ environment);
		String browserName = testEnvironment.browser();
		
		if(browserName.equals("chrome")){
			
			System.setProperty("webdriver.chrome.driver", "seleniumwebdriver/chromedriver/chromedriver.exe");
//
//	        ChromeOptions options = new ChromeOptions();
//	       
//	        options.addArguments("--disable-gpu");
//	        options.addArguments("--disable-browser-side-navigation");
//	        
//	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//	        capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//	        driver = new ChromeDriver(options);
			driver = new ChromeDriver(); 
	        driver.manage().deleteAllCookies();
		}
		else if(browserName.equals("firefox")){
			
			System.setProperty("webdriver.gecko.driver", "seleniumwebdriver/firefoxdriver/geckodriver.exe");	
			driver = new FirefoxDriver(); 
		}
		else if(browserName.equals("edge")){
			
			System.setProperty("webdriver.edge.driver", "seleniumwebdriver/edgedriver/MicrosoftWebDriver.exe");	
			driver = new EdgeDriver();
		}
		

		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		//Sales DB
		if(entrypoint.equals("salesdburl")){
			driver.get(testEnvironment.salesdburl());
		}
		
		//Console Admin
		else if(entrypoint.equals("consoleadmin")){
			driver.get(testEnvironment.consoleadminurl());
		}
		
		//Old Shopping Cart
		else if(entrypoint.equals("cart_domainsearchurl_domainz")){
			driver.get(testEnvironment.cart_domainsearchurl_domainz());
		}
		else if(entrypoint.equals("cart_domainsearchurl_netregistry")){
			driver.get(testEnvironment.cart_domainsearchurl_netregistry());
		}
		else if(entrypoint.equals("cart_domainsearchurl_melbourneit")){
			driver.get(testEnvironment.cart_domainsearchurl_melbourneit());
		}
		
		//New Shopping Cart
		else if(entrypoint.equals("newcart_domainsearchurl_netregistry")){
			driver.get(testEnvironment.newcart_domainsearchurl_netregistry());
		}
		
		//Customer Portal
		else if(entrypoint.equals("customerportalurl_domainz")){
			driver.get(testEnvironment.customerportalurl_domainz());
		}
		else if(entrypoint.equals("customerportalurl_netregistry")){
			driver.get(testEnvironment.customerportalurl_netregistry());
		}
		else if(entrypoint.equals("customerportalurl_melbourneit")){
			driver.get(testEnvironment.customerportalurl_melbourneit());
		}
		
		//Payment Gateway
		else if(entrypoint.equals("braintree")){
			driver.get(testEnvironment.braintreeurl());
		}
		
		//Others
		else if(entrypoint.equals("cart")){
			driver.get(testEnvironment.carturl());
		}
		else if(entrypoint.equals("cartlogin")){
			driver.get(testEnvironment.cartloginurl());
		}
		else{
			/* for any url */
		}
	}
}
