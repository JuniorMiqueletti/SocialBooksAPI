package br.com.juniormiqueletti.socialbooks.services.exceptions;

public class BookNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1984984198191L;

    public BookNotFoundException(String message){
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
