package com.alura.literalura.repository;

import com.alura.literalura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Método para obtener el ID máximo
    @Query("SELECT MAX(b.id) FROM Book b")
    Long getMaxId();

    // Método para verificar si existe un libro con el ID proporcionado
    boolean existsById(Long id);
}