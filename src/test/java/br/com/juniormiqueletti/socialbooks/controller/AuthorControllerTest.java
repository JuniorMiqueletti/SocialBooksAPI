package br.com.juniormiqueletti.socialbooks.controller;

import org.junit.Test;

import static io.restassured.RestAssured.*;


public class AuthorControllerTest {
	
	private static final String AUTHOR_URL = "http://localhost:8080/author";
	private static final int STATUS_CODE_UNAUTHORIZED = 401;
	private static final int STATUS_CODE_OK = 200;

	@Test
	public void getWithoutAuthentication() {
		given()
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_UNAUTHORIZED);
	}

	@Test
	public void getWithAuthentication() {
		given()
			.auth()
				.basic("juniormiqueletti", "p4ssw0rd")
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_OK);
	}
}
