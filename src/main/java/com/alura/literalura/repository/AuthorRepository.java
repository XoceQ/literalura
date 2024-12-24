package com.alura.literalura.repository;

import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByBook(Book book);
   /* La consulta devuelve una lista de objetos Author cuya condición de vida en el año proporcionado se cumple, es decir:
    El autor nació en o antes de ese año.
    El autor no ha muerto o murió después de ese año.*/
    @Query("SELECT a FROM Author a WHERE (a.birth_year IS NOT NULL AND a.birth_year <= :year) " +
            "AND (a.death_year IS NULL OR a.death_year > :year)")
    List<Author> findAuthorsAliveInYear(@Param("year") int year);

}