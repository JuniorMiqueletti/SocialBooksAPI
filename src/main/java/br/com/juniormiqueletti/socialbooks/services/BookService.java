package br.com.juniormiqueletti.socialbooks.services;

import br.com.juniormiqueletti.socialbooks.domain.Book;
import br.com.juniormiqueletti.socialbooks.repository.BookRepository;
import br.com.juniormiqueletti.socialbooks.services.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> list(){
        return bookRepository.findAll();
    }

    public Book find(Long id){
        Book book = bookRepository.findOne(id);

        if(book == null){
            throw new BookNotFoundException("Book not found!");
        }
        return book;
    }

    public Book save(Book book){
        book.setId(null);
        return bookRepository.save(book);
    }

    public void delete(Long id){
        try
        {
            bookRepository.delete(id);
        }catch (EmptyResultDataAccessException e){
            throw new BookNotFoundException("Book not found!");
        }
    }

    public void update(Book book){
        isValidBook(book);
        bookRepository.save(book);
    }

    private void isValidBook(Book book){
        this.find(book.getId());
    }
}
