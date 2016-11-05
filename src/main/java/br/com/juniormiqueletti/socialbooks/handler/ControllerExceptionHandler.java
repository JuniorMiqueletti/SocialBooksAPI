package br.com.juniormiqueletti.socialbooks.handler;

import br.com.juniormiqueletti.socialbooks.domain.ErrorDetail;
import br.com.juniormiqueletti.socialbooks.services.exceptions.BookNotFoundException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleBookNotFoundException(BookNotFoundException exception,
                                                            HttpServletRequest request){

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setStatus(404l);
        errorDetail.setTitle("The book has not been found!");
        errorDetail.setDeveloperMessage("http://error.socialbooks.com/404");
        errorDetail.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetail);
    }
}
