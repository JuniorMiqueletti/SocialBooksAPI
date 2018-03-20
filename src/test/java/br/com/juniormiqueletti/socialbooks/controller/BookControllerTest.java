package br.com.juniormiqueletti.socialbooks.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.domain.Book;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
	
	private static final String BOOK_URL = "/book";
	private static final int STATUS_CODE_UNAUTHORIZED = 401;
	private static final int STATUS_NOT_FOUND = 404;
	private static final int STATUS_CODE_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final int STATUS_NO_CONTENT = 204;
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String LOCATION_HEADER = "location";
	private static final String APPLICATION_JSON = "application/json";
	static final String LOCATION_PATTERN = ".*"+ BOOK_URL + "/[0-9]+";
	
	private static final String BASIC_AUTH_USER = "juniormiqueletti";
	private static final String BASIC_AUTH_PASS = "p4ssw0rd";
	
	@LocalServerPort
    int port;

	@Test
	public void getWithoutAuthenticationTest() {
		given()
			.port(port)
			.get(BOOK_URL)
		.then()
			.statusCode(STATUS_CODE_UNAUTHORIZED);
	}

	@Test
	public void getWithAuthenticationTest() {
		given()
			.port(port)
			.auth()
				.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			.get(BOOK_URL)
		.then()
			.statusCode(STATUS_CODE_OK);
	}

	@Test
	public void getNotFoundExceptionHandlerTest() {
		given()
			.port(port)
			.auth()
				.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			.get(BOOK_URL + "/333")
		.then()
			.statusCode(STATUS_NOT_FOUND)
			.body("title", equalTo("The book has not been found!"))
			.body("developerMessage", equalTo("http://error.socialbooks.com/404"));
	}
	
	@Test
	public void postAuthorTest() throws ParseException {
		
		Book book = createBookSample();
		
		Response response = 
				given()
				  .port(port)
				  .auth()
				    .basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			      .body(book)
			      .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
			    .post(BOOK_URL)
				    .andReturn();

		int statusCode = response.getStatusCode();
		String headerLocation = response.getHeader(LOCATION_HEADER);

		assertEquals(STATUS_CREATED, statusCode);
		assertTrue(headerLocation.matches(LOCATION_PATTERN));
	}

	@Test
	public void postAuthorSmokeTest() throws ParseException {
		
		Book book = createBookSample();
		
		Response response = 
				given()
					.port(port)
					.auth()
						.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
					.body(book)
						.header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
				.post(BOOK_URL)
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
		
		Book bookResponse = getResponse.body().as(Book.class);
		
		assertEquals(book.getName(), bookResponse.getName());
		assertEquals(book.getPublishingCompany(), bookResponse.getPublishingCompany());
	}
	
	@Test
	public void deleteNotFoundExceptionHandlerTest() {
		given()
			.port(port)
			.auth()
				.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			.delete(BOOK_URL + "/333")
		.then()
			.statusCode(STATUS_NOT_FOUND)
			.body("title", equalTo("The book has not been found!"))
			.body("developerMessage", equalTo("http://error.socialbooks.com/404"));
	}
	
	@Test
	public void deleteTest() throws ParseException {
		
		Book book = createBookSample();
		
		Response response = 
				given()
					.port(port)
					.auth()
						.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
					.body(book)
						.header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
				.post(BOOK_URL)
					.andReturn();
		
		int statusCode = response.getStatusCode();
		String headerLocation = response.getHeader(LOCATION_HEADER);
		
		assertEquals(statusCode, STATUS_CREATED);
		assertTrue(headerLocation.matches(LOCATION_PATTERN));
		
		given()
			.port(port)
			.auth()
				.basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
			.delete(headerLocation)
		.then()
			.statusCode(STATUS_NO_CONTENT);
	}
	
	private Book createBookSample() throws ParseException {
		Author author = new Author();
		author.setId(1L);
		author.setName("Junior");
		author.setNationality("Unknown");
		
		Book book = new Book();
		book.setAuthor(author);
		book.setName("Book of Life");
		book.setPublication(new SimpleDateFormat("dd/mm/yyyy").parse("19/03/2018"));
		book.setPublishingCompany("Miqueletti Sa");
		book.setSummary("Book with resume of my life");
		return book;
	}
}
