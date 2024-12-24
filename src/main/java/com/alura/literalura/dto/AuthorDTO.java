package com.alura.literalura.dto;

import com.alura.literalura.entity.Author;

public class AuthorDTO {
    private Long id;
    private String name;
    private Long bookId;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.bookId = author.getBook() != null ? author.getBook().getId() : null;
    }

    // Constructor que recibe solo el nombre (si solo te interesa mostrar el nombre)
    public AuthorDTO(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
