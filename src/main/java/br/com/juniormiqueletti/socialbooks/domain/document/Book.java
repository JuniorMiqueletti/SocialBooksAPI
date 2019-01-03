package br.com.juniormiqueletti.socialbooks.domain.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Document
public class Book {

    @JsonInclude(Include.NON_NULL)
    @Id
    private String id;

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

    @JsonInclude(Include.NON_NULL)
    private Author author;

    @DBRef
    @JsonInclude(Include.NON_EMPTY)
    private List<Comment> comments;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Book(String name) {
        this.name = name;
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
