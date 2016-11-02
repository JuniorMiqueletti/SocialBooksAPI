package br.com.juniormiqueletti.socialbooks.resources;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookResource {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "books",method = RequestMethod.GET)
    public List<Book> list(){
        return bookRepository.findAll();
    }
}
