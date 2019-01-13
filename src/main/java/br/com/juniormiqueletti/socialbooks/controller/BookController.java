package br.com.juniormiqueletti.socialbooks.controller;

import br.com.juniormiqueletti.socialbooks.domain.document.Book;
import br.com.juniormiqueletti.socialbooks.domain.document.Comment;
import br.com.juniormiqueletti.socialbooks.domain.dto.BookDTO;
import br.com.juniormiqueletti.socialbooks.domain.dto.CommentDTO;
import br.com.juniormiqueletti.socialbooks.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> list(){
        List<BookDTO> books = bookService.list().parallelStream()
            .map(this::toDTO)
            .collect(toList());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(books);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody BookDTO bookDTO){
        Book savedBook = bookService.save(toEntity(bookDTO));

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedBook.getId())
                .toUri();

        return ResponseEntity
            .created(uri)
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> find(@PathVariable("id") String id){
        Book book = bookService.find(id);

        CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity
            .status(HttpStatus.OK)
            .cacheControl(cacheControl)
            .body(toDTO(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        bookService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @Validated @RequestBody BookDTO bookDTO,
        @PathVariable("id") String id
    ){
        bookDTO.setId(id);

        bookService.update(toEntity(bookDTO));

        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> addComment(
            @PathVariable("id") String bookId,
            @Validated @RequestBody CommentDTO commentDTO
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        commentDTO.setUser(authentication.getName());
        
        bookService.saveComment(bookId, toEntity(commentDTO));

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequestUri()
                .build().toUri();

        return ResponseEntity
            .created(uri)
            .build();
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentDTO>> listComments(@PathVariable("id") String bookId){
        List<CommentDTO> comments =
            bookService.listComments(bookId).parallelStream()
                .map(this::toDTO)
                .collect(toList());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(comments);
    }

    @GetMapping("/{name}/like")
    public ResponseEntity<List<BookDTO>> findBookByLikeName(@PathVariable String name){

        List<BookDTO> books =
            bookService.findByNameLike(name).parallelStream()
                .map(this::toDTO)
                .collect(toList());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(books);
    }

    @GetMapping("/{text}/full-text-search")
    public ResponseEntity<List<BookDTO>> findBookByFullText(@PathVariable String text){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("score"));

        List<BookDTO> books =
            bookService.findByFullTextSearch(text, pageable).stream()
                .map(this::toDTO)
                .collect(toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(books);
    }

    private BookDTO toDTO(final Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }

    private Book toEntity(final BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        return book;
    }

    private CommentDTO toDTO(final Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }

    private Comment toEntity(final CommentDTO commentDTO) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        return comment;
    }
}
