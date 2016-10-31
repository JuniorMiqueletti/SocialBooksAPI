package br.com.juniormiqueletti.socialbooks.domain;

import java.util.Date;

/**
 * Created by junio on 30/10/2016.
 */
public class Comment {

    private Long id;
    private String text;
    private String user;
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
