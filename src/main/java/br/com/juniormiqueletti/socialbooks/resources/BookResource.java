package br.com.juniormiqueletti.socialbooks.resources;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookResource {

    @RequestMapping(value = "books",method = RequestMethod.GET)
    public List<Book> list(){

        Book book1 = new Book("Git Step-by-step");
        Book book2 = new Book("RestFull");

        Book[] books = {book1, book2};

        return Arrays.asList(books);
    }
}
