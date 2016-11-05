package br.com.juniormiqueletti.socialbooks.controllers;

import br.com.juniormiqueletti.socialbooks.domain.Author;
import br.com.juniormiqueletti.socialbooks.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody Author author){
        author = authorService.save(author);

        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(author.getId())
                        .toUri();

        return ResponseEntity
                .created(uri)
                .build();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity find(@PathVariable("id")Long id){

        Author author = authorService.find(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(author);
    }
}
