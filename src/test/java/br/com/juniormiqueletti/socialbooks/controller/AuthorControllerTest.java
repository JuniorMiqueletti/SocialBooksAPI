package br.com.juniormiqueletti.socialbooks.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {
	
	private static final String AUTHOR_URL = "/author";
	private static final int STATUS_CODE_UNAUTHORIZED = 401;
	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String LOCATION_HEADER = "location";
	private static final String APPLICATION_JSON = "application/json";
	static final String LOCATION_PATTERN = ".*"+ AUTHOR_URL + "/[0-9]+";
	
	private static final String BASIC_AUTH_USER = "juniormiqueletti";
	private static final String BASIC_AUTH_PASS = "p4ssw0rd";
	
	@LocalServerPort
    int port;

	@Test
	public void getWithoutAuthenticationTest() {
		given()
			.port(port)
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_UNAUTHORIZED);
	}

	@Test
	public void getWithAuthenticationTest() {
		given()
			.port(port)
			.auth()
				.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			.get(AUTHOR_URL)
		.then()
			.statusCode(STATUS_CODE_OK);
	}

	@Test
	public void postAuthorTest() throws ParseException {
		
		Author author = new Author();
		author.setName("Junior");
		author.setNationality("Unknown");
		
		Response response = 
				given()
				  .port(port)
				  .auth()
				    .basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			      .body(author)
			      .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
			    .post(AUTHOR_URL)
				    .andReturn();

		int statusCode = response.getStatusCode();
		String headerLocation = response.getHeader(LOCATION_HEADER);

		assertEquals(statusCode, STATUS_CREATED);
		assertTrue(headerLocation.matches(LOCATION_PATTERN));
	}
	
	@Test
	public void postAuthorSmokeTest() throws ParseException {
		
		Author author = new Author();
		author.setName("Junior");
		author.setNationality("Unknown");
		
		Response response = 
				given()
					.port(port)
					.auth()
						.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
					.body(author)
						.header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
				.post(AUTHOR_URL)
					.andReturn();
		
		int statusCode = response.getStatusCode();
		String headerLocation = response.getHeader(LOCATION_HEADER);
		
		assertEquals(statusCode, STATUS_CREATED);
		assertTrue(headerLocation.matches(LOCATION_PATTERN));
		
		Response getResponse = 
				given()
					.port(port)
					.auth()
						.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
				.get(headerLocation)
		.then()
			.statusCode(STATUS_CODE_OK)
		.extract()
		.response();
		
		Author authorResponse = getResponse.body().as(Author.class);
		
		assertEquals(author.getName(), authorResponse.getName());
		assertEquals(author.getNationality(), authorResponse.getNationality());
	}
}
