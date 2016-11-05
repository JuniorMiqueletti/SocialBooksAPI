package br.com.juniormiqueletti.socialbooks.services.exceptions;

public class AuthorFoundException extends RuntimeException{

    private static final long serialVersionUID = 1984984198122L;

    public AuthorFoundException(String message){
        super(message);
    }

    public AuthorFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
