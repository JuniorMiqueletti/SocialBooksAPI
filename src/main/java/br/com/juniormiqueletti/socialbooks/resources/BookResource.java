package br.com.juniormiqueletti.socialbooks.resources;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
import br.com.juniormiqueletti.socialbooks.services.BookService;
import br.com.juniormiqueletti.socialbooks.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> list(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.list());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Book book){
        bookService.save(book);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(book.getId())
                            .toUri();

        return ResponseEntity
                .created(uri)
                    .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity find(@PathVariable("id") Long id){
        Book book = null;

        try {
            book = bookService.find(id);
        }catch (BookNotFoundException e){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                    .body(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id){

        try{
            bookService.delete(id);
        }catch (BookNotFoundException e){
            return ResponseEntity
                    .notFound()
                        .build();
        }
        return ResponseEntity
                .noContent()
                    .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Book book, @PathVariable("id") Long id){
        book.setId(id);
        try{
            bookService.save(book);
        }catch (BookNotFoundException e){
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .noContent()
                    .build();
    }
}
