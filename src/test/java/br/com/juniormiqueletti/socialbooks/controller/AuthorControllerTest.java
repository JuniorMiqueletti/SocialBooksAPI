package br.com.juniormiqueletti.socialbooks.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {
	
	private static final String AUTHOR_URL = "/author";
	private static final int STATUS_CODE_UNAUTHORIZED = 401;
	private static final int STATUS_CODE_OK = 200;
	
	@LocalServerPort
    int port;

	@Test
	public void getWithoutAuthentication() {
		given()
			.port(port)
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_UNAUTHORIZED);
	}

	@Test
	public void getWithAuthentication() {
		given()
			.port(port)
			.auth()
				.basic("juniormiqueletti", "p4ssw0rd")
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_OK);
	}
}
