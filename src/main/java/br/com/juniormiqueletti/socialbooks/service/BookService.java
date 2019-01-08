package br.com.juniormiqueletti.socialbooks.service;

import br.com.juniormiqueletti.socialbooks.domain.document.Book;
import br.com.juniormiqueletti.socialbooks.domain.document.Comment;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
import br.com.juniormiqueletti.socialbooks.repository.CommentRepository;
import br.com.juniormiqueletti.socialbooks.service.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private CommentRepository commentRepository;

    @Autowired
    public BookService(
        final BookRepository bookRepository,
        final CommentRepository commentRepository
    ) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    public List<Book> list(){
        return bookRepository.findAll();
    }

    public Book find(final String id){
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent())
            return book.get();

        throw new BookNotFoundException("Book not found!");
    }

    public List<Book> findByNameLike(final String name) {
        return bookRepository.findByNameLikeIgnoreCase(name);
    }

    public Book save(final Book book){
        book.setId(null);
        return bookRepository.save(book);
    }

    public void delete(final String id){
        if (!bookRepository.existsById(id))
            throw new BookNotFoundException("Book not found!");

        bookRepository.deleteById(id);
    }

    public void update(final Book book){
        isValidBook(book);
        bookRepository.save(book);
    }

    public Comment saveComment(final String bookId, Comment comment){
        Book book = find(bookId);

        comment.setBook(book);
        comment.setDate(new Date());

        return commentRepository.save(comment);
    }

    public List<Comment> listComments(final String bookId) {
        Book book = find(bookId);

        return book.getComments();
    }

    private void isValidBook(final Book book){
        this.find(book.getId());
    }
}
