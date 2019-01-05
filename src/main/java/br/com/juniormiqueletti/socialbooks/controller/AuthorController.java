package br.com.juniormiqueletti.socialbooks.controller;

import br.com.juniormiqueletti.socialbooks.domain.document.Author;
import br.com.juniormiqueletti.socialbooks.domain.dto.AuthorDTO;
import br.com.juniormiqueletti.socialbooks.service.AuthorService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/api/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> list(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.list().parallelStream()
                        .map(this::toDTO)
                        .collect(toList())
                );
    }

    @PostMapping
    public ResponseEntity<Void> save(@Validated @RequestBody AuthorDTO author){
        Author authorSaved = authorService.save(toEntity(author));

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(authorSaved.getId())
            .toUri();

        return ResponseEntity
            .created(uri)
            .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> find(@PathVariable("id") String id){

        Author author = authorService.find(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(toDTO(author));
    }

    private AuthorDTO toDTO(final Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        BeanUtils.copyProperties(author, authorDTO);
        return authorDTO;
    }

    private Author toEntity(final AuthorDTO authorDTO) {
        Author author = new Author();
        BeanUtils.copyProperties(authorDTO, author);
        return author;
    }
}
