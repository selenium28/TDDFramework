package com.base;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources(
	"file:src/main/java/com/config/${env}.properties" 
)

public interface Environment extends Config{
	
	String carturl();
	String cartloginurl();
	
	String customerportalurl_domainz();
	String customerportalurl_netregistry();
	String customerportalurl_melbourneit();
	
	String cart_domainsearchurl_domainz();
	String cart_domainsearchurl_netregistry();
	String cart_domainsearchurl_melbourneit();
	
	String carturl_domainz();
	String salesdburl();
	String consoleadminurl();
	String username();
	String password();
	String browser();
	String braintreeurl();
	
	@Key("db.hostname")
	String getDBHostname();
	
	@Key("db.port")
	int getDBPort();
	
	@Key("db.username")
	String getDBUsername();
	
	@Key("db.password")
    String getDBPassword();
	
}
