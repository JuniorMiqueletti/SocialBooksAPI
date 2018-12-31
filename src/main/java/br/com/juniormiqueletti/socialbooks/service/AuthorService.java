package br.com.juniormiqueletti.socialbooks.service;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.repository.AuthorRepository;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorFoundException;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> list(){
        return authorRepository.findAll();
    }

    public Author save(final Author author){
        if(author.getId() != null){
            Optional<Author> authoSaved = authorRepository.findById(author.getId());

            if (authoSaved.isPresent())
                throw new AuthorFoundException("The author has been exists!");

        }
        return authorRepository.save(author);
    }

    public Author find(final String id){
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent())
            return author.get();

        throw new AuthorNotFoundException("The author has not been found!");
    }
}
