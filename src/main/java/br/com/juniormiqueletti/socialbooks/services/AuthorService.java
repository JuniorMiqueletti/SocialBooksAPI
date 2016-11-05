package br.com.juniormiqueletti.socialbooks.services;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.repository.AuthorRepository;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorFoundException;
import br.com.juniormiqueletti.socialbooks.services.exceptions.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> list(){
        return authorRepository.findAll();
    }

    public Author save(Author author){
        if(author.getId() != null){
            Author a = authorRepository.findOne(author.getId());

            if(a != null){
                throw new AuthorFoundException("The author has been exists!");
            }
        }
        return authorRepository.save(author);
    }

    public Author find(Long id){
        Author author = authorRepository.findOne(id);

        if(author == null){
            throw new AuthorNotFoundException("The author has not been found!");
        }
        return author;
    }
}
