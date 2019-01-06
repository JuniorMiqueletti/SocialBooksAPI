package br.com.juniormiqueletti.socialbooks.domain.document;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

public class Comment extends GenericDocument {

    @NotEmpty(message = "The field comment cannot be empty!")
    @Size(message = "The comment cannot greater than 1500 characters", max = 1500)
    private String text;

    private String user;

    private Date date;

    @DBRef
    private Book book;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
