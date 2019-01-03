package br.com.juniormiqueletti.socialbooks.controller;

import br.com.juniormiqueletti.socialbooks.domain.document.Book;
import br.com.juniormiqueletti.socialbooks.domain.document.Comment;
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
@RequestMapping(value = "/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> list(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.list());
    }

    @PostMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable("id") String id){
        Book book = bookService.find(id);

        CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
                .status(HttpStatus.OK)
                .cacheControl(cacheControl)
                    .body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @Validated @RequestBody Book book,
        @PathVariable("id") String id
    ){
        book.setId(id);

        bookService.update(book);

        return ResponseEntity
                .noContent()
                    .build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(
            @PathVariable("id") String bookId,
            @Validated @RequestBody Comment comment
    ){

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

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comment>> listComments(@PathVariable("id") String bookId){
        List<Comment> comments = bookService.listComments(bookId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(comments);
    }
}
