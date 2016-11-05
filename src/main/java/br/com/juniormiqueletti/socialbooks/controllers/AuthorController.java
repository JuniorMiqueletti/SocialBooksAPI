package br.com.juniormiqueletti.socialbooks.controllers;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Author>> list(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.list());
    }
}
