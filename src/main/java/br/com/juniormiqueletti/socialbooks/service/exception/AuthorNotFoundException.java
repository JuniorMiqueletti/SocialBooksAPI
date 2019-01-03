package br.com.juniormiqueletti.socialbooks.service.exception;

public class AuthorNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1984984198191L;

    public AuthorNotFoundException(String message){
        super(message);
    }

    public AuthorNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
