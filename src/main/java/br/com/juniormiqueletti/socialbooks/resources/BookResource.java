package br.com.juniormiqueletti.socialbooks.resources;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
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
    private BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> list(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookRepository.findAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Book book){
        bookRepository.save(book);

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
        Book book = bookRepository.findOne(id);
        if(book == null){
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
            bookRepository.delete(id);
        }catch (EmptyResultDataAccessException e){
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
        bookRepository.save(book);

        return ResponseEntity
                .noContent()
                    .build();
    }
}
