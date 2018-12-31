package br.com.juniormiqueletti.socialbooks.handler;

import br.com.juniormiqueletti.socialbooks.domain.ErrorDetail;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorFoundException;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorNotFoundException;
import br.com.juniormiqueletti.socialbooks.services.exceptions.BookNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetail> handlerBookNotFoundException(
        final BookNotFoundException exception,
        final HttpServletRequest request
    ){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(404l);
        errorDetail.setTitle("The book has not been found!");
        errorDetail.setDeveloperMessage("http://error.socialbooks.com/404");
        errorDetail.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
            .status(NOT_FOUND)
            .body(errorDetail);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorDetail> handlerAuthorNotFoundException(
        AuthorNotFoundException exception,
        HttpServletRequest request
    ){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(404l);
        errorDetail.setTitle("The author has not been found!");
        errorDetail.setDeveloperMessage("http://error.socialbooks.com/404");
        errorDetail.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
            .status(NOT_FOUND)
            .body(errorDetail);
    }

    @ExceptionHandler(AuthorFoundException.class)
    public ResponseEntity<ErrorDetail> handlerAuthorFoundException(
        AuthorFoundException exception,
        HttpServletRequest request
    ){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(409l);
        errorDetail.setTitle("The author has been found!");
        errorDetail.setDeveloperMessage("http://error.socialbooks.com/409");
        errorDetail.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
            .status(CONFLICT)
            .body(errorDetail);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetail> handlerDataIntegrityViolationException(
        DataIntegrityViolationException exception,
        HttpServletRequest request
    ){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(400l);
        errorDetail.setTitle("Invalid Request!");
        errorDetail.setDeveloperMessage("http://error.socialbooks.com/400");
        errorDetail.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
            .status(BAD_REQUEST)
            .body(errorDetail);
    }
}
