package com.alura.literalura.dto;

import com.alura.literalura.entity.Book;
import com.alura.literalura.entity.Author;


import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {
    private Long id;
    private String title;
    private String language;
    private String downloadLink;
    private Integer downloadCount;
    private List<String> authors;  // Añadir lista de nombres de autores


    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.language = book.getLanguage();
        this.downloadLink = book.getDownloadLink();
        this.downloadCount = book.getDownloadCount();
        // Convertir la lista de autores a una lista de nombres de autores
        this.authors = book.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }


    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                ", downloadCount=" + downloadCount +
                ", authors=" + authors +
                '}';
    }
}
