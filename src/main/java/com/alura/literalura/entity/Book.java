package com.alura.literalura.entity;

import jakarta.persistence.*;

import java.util.List;



@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String language;
    private String downloadLink;
    private Integer downloadCount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors;

    public Book() {
        this.downloadCount = 0; // Inicializar en 0
    }

    public Book(String title, String language, String downloadLink) {
        this.title = title;
        this.language = language;
        this.downloadLink = downloadLink;
        this.downloadCount = 0; // Inicializar en 0
    }

    // Getter para el ID del libro
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
    // Otros getters y setters...

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", downloadCount=" + downloadCount +
                ", authors=" + authors +
                '}';
    }
}
