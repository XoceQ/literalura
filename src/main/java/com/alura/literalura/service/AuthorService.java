package com.alura.literalura.service;

import com.alura.literalura.dto.AuthorDTO;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // Crear y guardar un autor
    @Transactional
    public Author createAuthor(String name, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book ID no encontrado: " + bookId));
        Author author = new Author(name, book);
        return authorRepository.save(author);
    }

    // Obtener todos los autores
    @Transactional
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(AuthorDTO::new) // Convertir Author a AuthorDTO
                .collect(Collectors.toList());
    }

    // Obtener autores por libro
    @Transactional
    public List<AuthorDTO> getAuthorsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book ID no encontrado: " + bookId));
        List<Author> authors = authorRepository.findByBook(book);
        return authors.stream()
                .map(AuthorDTO::new) // Convertir Author a AuthorDTO
                .collect(Collectors.toList());
    }

    // Eliminar un autor por ID
    @Transactional
    public void deleteAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Author ID no encontrado: " + id));
        authorRepository.delete(author);
    }
}
