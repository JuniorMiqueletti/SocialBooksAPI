package br.com.juniormiqueletti.socialbooks.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.juniormiqueletti.socialbooks.domain.dto.AuthorDTO;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerIT {
    
    private static final String AUTHOR_URL = "/api/v1/author";
    private static final int STATUS_CODE_UNAUTHORIZED = 401;
    private static final int STATUS_NOT_FOUND = 404;
    private static final int STATUS_CODE_OK = 200;
    private static final int STATUS_CREATED = 201;
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String LOCATION_HEADER = "location";
    private static final String APPLICATION_JSON = "application/json";
    static final String LOCATION_PATTERN = ".*"+ AUTHOR_URL + "/[a-zA-Z0-9]+";
    
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
    public void getNotFoundExceptionHandlerTest() {
        given()
            .port(port)
            .auth()
                .basic(BASIC_AUTH_USER, BASIC_AUTH_PASS)
            .get(AUTHOR_URL + "/333")
        .then()
            .statusCode(STATUS_NOT_FOUND)
            .body("title", equalTo("The author has not been found!"))
            .body("developerMessage", equalTo("http://error.socialbooks.com/404"));
    }

    @Test
    public void postAuthorTest() throws ParseException {
        
        AuthorDTO author = new AuthorDTO();
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

        assertEquals(STATUS_CREATED, statusCode);
        assertTrue(headerLocation.matches(LOCATION_PATTERN));
    }
    
    @Test
    public void postAuthorSmokeTest() throws ParseException {

        AuthorDTO author = new AuthorDTO();
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
        
        assertEquals(STATUS_CREATED, statusCode);
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

        AuthorDTO authorResponse = getResponse.body().as(AuthorDTO.class);
        
        assertEquals(author.getName(), authorResponse.getName());
        assertEquals(author.getNationality(), authorResponse.getNationality());
    }
}
