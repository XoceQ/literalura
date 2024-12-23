package com.alura.literalura.repository;

import com.alura.literalura.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Puedes agregar métodos personalizados si es necesario
}