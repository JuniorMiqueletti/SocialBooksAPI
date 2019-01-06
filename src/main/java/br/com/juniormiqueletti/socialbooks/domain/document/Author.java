package br.com.juniormiqueletti.socialbooks.domain.document;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

public class Author extends GenericDocument {

    @NotEmpty(message = "The field name cannot be empty!")
    private String name;

    private Date bornDate;

    private String nationality;

    @DBRef
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
