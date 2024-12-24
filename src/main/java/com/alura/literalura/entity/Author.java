package com.alura.literalura.entity;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Relación ManyToOne con Book (un autor pertenece a un libro)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id") // Clave foránea en la tabla Author
    private Book book;

    // Constructor sin argumentos
    public Author() {

    }

    // Constructor con parámetros
    public Author(String name, Book book) {
        this.name = name;
        this.book = book;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}