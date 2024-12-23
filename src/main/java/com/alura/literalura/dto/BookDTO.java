package com.alura.literalura.dto;

import com.alura.literalura.entity.Book;

public class BookDTO {
    private Long id;
    private String title;
    private String language;
    private String downloadLink;
    private Integer downloadCount;

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.language = book.getLanguage();
        this.downloadLink = book.getDownloadLink();
        this.downloadCount = book.getDownloadCount();
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
                '}';
    }
}
