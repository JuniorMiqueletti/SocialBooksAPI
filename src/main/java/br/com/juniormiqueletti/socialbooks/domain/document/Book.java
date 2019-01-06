package br.com.juniormiqueletti.socialbooks.domain.document;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class Book extends GenericDocument {

    @NotEmpty(message = "The field name cannot be empty!")
    private String name;

    @NotNull(message = "The field publication cannot be empty!")
    private Date publication;

    @NotEmpty(message = "The field publishingCompany cannot be empty!")
    private String publishingCompany;

    @NotEmpty(message = "The field summary cannot be empty!")
    @Size(message = "The summary cannot greater than 1500 characters", max = 1500)
    private String summary;

    private Author author;

    @DBRef
    private List<Comment> comments;

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
