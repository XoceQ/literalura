package com.alura.literalura.entity;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Column(name = "birth_year")
    private Integer birth_year; // Año de nacimiento


    @Column(name = "death_year")
    private Integer death_year; // Año de muerte (puede ser null si el autor está vivo)


    // Relación ManyToOne con Book (un autor pertenece a un libro)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id") // Clave foránea en la tabla Author
    private Book book;

    // Constructor sin argumentos
    public Author() {

    }

    // Constructor con parámetros

    public Author(String name, Integer birth_year, Integer death_year, Book book) {
        this.name = name;
        this.birth_year = birth_year;
        this.death_year = death_year;
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
                ", birth_year=" + birth_year +
                ", name='" + name + '\'' +
                ", death_year=" + death_year +
                '}';
    }
}
