package com.alura.literalura.dto;

import com.alura.literalura.entity.Author;

public class AuthorDTO {
    private Long id;
    private String name;
    private Long bookId;

    // Constructor que recibe un objeto Author
    public AuthorDTO(Author author) {
        this.id = author.getId();  // Obtener el ID del autor
        this.name = author.getName();  // Obtener el nombre del autor
        this.bookId = author.getBook() != null ? author.getBook().getId() : null;  // Obtener el ID del libro asociado
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
