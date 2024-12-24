package com.alura.literalura.dto;

import com.alura.literalura.entity.Author;

public class AuthorDTO {
    private Long id;
    private String name;
    private Long bookId;
    private Integer birth_year;  // Año de nacimiento
    private Integer death_year;  // Año de muerte

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.bookId = author.getBook() != null ? author.getBook().getId() : null;
        this.birth_year = author.getBirth_year();  // Asignamos el año de nacimiento
        this.death_year = author.getDeath_year();  // Asignamos el año de muerte
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

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
