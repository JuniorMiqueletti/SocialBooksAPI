package br.com.juniormiqueletti.socialbooks.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class BookDTO extends DTO {

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
    private AuthorDTO author;

    @JsonInclude(Include.NON_EMPTY)
    private List<CommentDTO> comments;

    private Float score;

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

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
