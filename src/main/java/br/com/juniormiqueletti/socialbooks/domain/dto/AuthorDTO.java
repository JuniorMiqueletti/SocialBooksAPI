package br.com.juniormiqueletti.socialbooks.domain.dto;

import br.com.juniormiqueletti.socialbooks.domain.document.Book;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

public class AuthorDTO extends DTO {

    @NotEmpty(message = "The field name cannot be empty!")
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date bornDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nationality;

    @JsonIgnore
    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
