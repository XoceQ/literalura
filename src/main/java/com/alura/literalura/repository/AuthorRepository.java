package com.alura.literalura.repository;

import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBook(Book book);
}