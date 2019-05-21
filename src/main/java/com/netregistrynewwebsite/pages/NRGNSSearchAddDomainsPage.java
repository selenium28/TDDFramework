package com.netregistrynewwebsite.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.base.TestBase;


public class NRGNSSearchAddDomainsPage extends TestBase{

	
	//Objects
    @FindBy(how=How.CSS, using = "button.search-btn.green")
    WebElement searchButton;
    
    @FindBy(how=How.XPATH, using = "//body/div[1]/div[2]/div/div/div/div[2]/div[1]/div[1]/form/div[2]/div[1]")
    WebElement domainExtension;
    
    @FindBy(how=How.CSS, using = "button.btn.green")
    WebElement continueButton;
    
    @FindBy(how=How.XPATH, using = "//body/div[1]/div[2]/div/div/div/div[2]/div[2]/div")
    WebElement domainPrivacy;
    
    @FindBy(how=How.XPATH, using = "//form[@class='ng-pristine ng-valid']/div[1]/input")
    WebElement newDomainSearchBox;
    
	
    //Initializing Page Objects
    public NRGNSSearchAddDomainsPage(){
    	PageFactory.initElements(driver, this);
    	 driver.manage().deleteAllCookies();
    }

 
    //Methods
    public void clickSearchButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchButton);
    	searchButton.click();
    	
    	Thread.sleep(10000);
    	
    }
    
    public NRGNSDomainPrivacyPage clickContinueButton() throws InterruptedException {

    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
    	continueButton.click();
    	
    	return new NRGNSDomainPrivacyPage();
    	
    }
    
    public void clickDomainExtension(String strdomainextension) {

    	switch (strdomainextension) {
        case ".com" :
        	System.out.println("Tick .com tld");
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[1]")).click();
            break;
        case ".com.au" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[2]")).click();
        	break;
        case ".melbourne" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[3]")).click();
        	break;
        case ".net" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[4]")).click();
        	break;
        case ".net.au" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[5]")).click();
        	break;
        case ".org" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[6]")).click();
        	break;
        case ".org.au" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[7]")).click();
        	break;
        case ".sdyney" :
        	driver.findElement(By.xpath("//div[@class='tld-select']/div[8]")).click();
        	break;
        default :
        	System.out.println("Invalid domain extension");
      }
    }
    
    public void setDomainNameAndTld(String domainname, String tldname){
    	newDomainSearchBox.clear();
    	newDomainSearchBox.sendKeys(domainname);
    	this.clickDomainExtension(tldname);

		
    }
    
    public void addDomainName(String domainname) {
    	
    	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.partialLinkText(domainname)));
    	WebElement domainName = driver.findElement(By.partialLinkText(domainname));
    	domainName.click();

    }
    
    public void removeDomain(String domainname) {
    	
    	
    }

}
