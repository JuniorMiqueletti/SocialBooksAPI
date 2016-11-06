package br.com.juniormiqueletti.socialbooks.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Book {

    @JsonInclude(Include.NON_NULL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotEmpty(message = "The field name cannot be empty!")
    private String name;

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "The field publication cannot be empty!")
    private Date publication;

    @JsonInclude(Include.NON_NULL)
    @NotEmpty(message = "The field publishingCompany cannot be empty!")
    private String publishingCompany;

    @JsonInclude(Include.NON_NULL)
    @NotEmpty(message = "The field summary cannot be empty!")
    @Size(message = "The summary cannot greater than 1500 characters", max = 1500)
    private String summary;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonInclude(Include.NON_NULL)
    private Author author;

    @JsonInclude(Include.NON_EMPTY)
    @OneToMany(mappedBy = "book")
    private List<Comment> comments;

    public Book(){}

    public Book(String name) {
        this.name = name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublication() {
        return publication;
    }

    public void setPublication(Date publication) {
        this.publication = publication;
    }

    public String getPublishingCompany() {
        return publishingCompany;
    }

    public void setPublishingCompany(String publishingCompany) {
        this.publishingCompany = publishingCompany;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
