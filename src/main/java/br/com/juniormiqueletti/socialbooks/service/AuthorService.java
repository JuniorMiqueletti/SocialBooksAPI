package br.com.juniormiqueletti.socialbooks.service;

import br.com.juniormiqueletti.socialbooks.domain.document.Author;
import br.com.juniormiqueletti.socialbooks.repository.AuthorRepository;
import br.com.juniormiqueletti.socialbooks.service.exception.AuthorFoundException;
import br.com.juniormiqueletti.socialbooks.service.exception.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        if(StringUtils.hasText(author.getId()) && authorRepository.existsById(author.getId()))
            throw new AuthorFoundException("The author has been exists!");

         return authorRepository.save(author);
    }

    public Author find(final String id){
        Optional<Author> author = authorRepository.findById(id);

        if (!author.isPresent())
            throw new AuthorNotFoundException("The author has not been found!");

        return author.get();
    }
}
