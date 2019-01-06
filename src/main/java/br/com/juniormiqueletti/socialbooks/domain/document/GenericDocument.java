package br.com.juniormiqueletti.socialbooks.domain.document;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
public class GenericDocument {

    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
