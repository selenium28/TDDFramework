package com.consoleregression.workflowtest.testcases;

import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.response.Response;


import org.testng.annotations.Test;



public class RestAssured {
	
	private Response response;
	public RestAssured() {
		super();
	}

	@Test
	public void testRetrieveTXT() throws InterruptedException{
		
		String domainname = "testconsoleregression01082019-125409.com";
		String hostname = "https://cdn.stage.provisioning-api.melbourneit.com.au";
		response = given().auth().preemptive().basic("test", "testpassword").
				   when().get(hostname + "/v1/pm/o365/domain/" + domainname + "/verificationRecord");
		
		System.out.println("Status message: " + response.body().asString());
		
	}


}
