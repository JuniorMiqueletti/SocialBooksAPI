package br.com.juniormiqueletti.socialbooks.services;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> list(){
        return authorRepository.findAll();
    }
}
