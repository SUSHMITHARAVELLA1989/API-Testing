package com.rest.currency;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rest.currency.commons.constants.Endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CurrenciesTest {
	@Test
	public void getCurrencyList()
	{
		//sending get request to the server
		Response response = RestAssured
				.given()
				.get(Endpoints.CURRENCY_URL);

		//Verify the status code is 200
		//response validation using RestAssured hamcrest matchers
		response
		.then()
		.assertThat()
		.statusCode(200)
		.and()
		.body("result", Matchers.equalTo("success"));


		//Verify the currency GBP in response body
		//converting json response to string
		String st = response.getBody().asString();
		//Create json object pass parameter as string
		JSONObject json = new JSONObject(st);
		JSONObject currencyJson = json.getJSONObject("conversion_rates");
		boolean isGBPCurrencyExists=currencyJson.has("GBP");
		//Using testng assertions for validating results
		Assert.assertEquals(isGBPCurrencyExists, true);


		//Verify the total number of currencies returned within the response 
		int len=currencyJson.length();
		System.out.println("The total number of currencies returned within the response is  :"+len);
	}
}
