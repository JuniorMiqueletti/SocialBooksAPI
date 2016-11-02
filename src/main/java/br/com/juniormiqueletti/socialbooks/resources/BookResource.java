package br.com.juniormiqueletti.socialbooks.resources;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> list(){
        return bookRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody Book book){
        bookRepository.save(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Book find(@PathVariable("id") Long id){
        return bookRepository.findOne(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id){
        bookRepository.delete(id);
    }
}
