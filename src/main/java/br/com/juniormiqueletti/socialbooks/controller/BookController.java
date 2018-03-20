package br.com.juniormiqueletti.socialbooks.controller;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.domain.Comment;
import br.com.juniormiqueletti.socialbooks.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Book>> list(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.list());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> save(@Validated @RequestBody Book book){
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
    public ResponseEntity<Book> find(@PathVariable("id") Long id){
        Book book = bookService.find(id);

        CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(cacheControl)
                    .body(book);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){

        bookService.delete(id);

        return ResponseEntity
                .noContent()
                    .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Validated @RequestBody Book book, @PathVariable("id") Long id){
        book.setId(id);

        bookService.update(book);

        return ResponseEntity
                .noContent()
                    .build();
    }

    @RequestMapping(value ="/{id}/comment", method = RequestMethod.POST )
    public ResponseEntity<Void> addComment(@PathVariable("id") Long bookId,@Validated @RequestBody Comment comment){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        comment.setUser(authentication.getName());
        
        bookService.saveComment(bookId, comment);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                        .build().toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listComments(@PathVariable("id") Long bookId){
        List<Comment> comments = bookService.listComments(bookId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }
}
