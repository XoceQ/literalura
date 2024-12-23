package com.alura.literalura.service;

import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository; // Repositorio Author

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Guardar un libro
    @Transactional
    public Book saveBook(String title, String authorName, String downloadLink) {
        // Crear el nuevo libro
        Book book = new Book();
        book.setTitle(title);
        book.setDownloadLink(downloadLink);

        // Crear o recuperar el autor de la base de datos
        Author author = new Author(authorName); // Asumimos que Author tiene un constructor que recibe el nombre
        authorRepository.save(author); // Guarda el autor si no existe

        // Asociar el autor al libro
        book.setAuthors(List.of(author)); // Establecer la lista de autores (ahora una lista de objetos Author)

        // Guardar el libro
        return bookRepository.save(book); // Hibernate asignará el ID automáticamente
    }

    // Mostrar todos los libros
    @Transactional
    public List<BookDTO> getAllBooksDTO() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookDTO::new) // Usamos el constructor que recibe un objeto Book
                .collect(Collectors.toList());
    }


    // Eliminar un libro por ID
    @Transactional
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null); // Cargar explícitamente el libro
        if (book != null) {
            System.out.println("El libro con ID " + id + " existe. Procediendo a eliminar.");
            bookRepository.delete(book); // Usar el objeto directamente
            bookRepository.flush(); // Forzar que los cambios sean persistidos
            System.out.println("Libro eliminado.");
        } else {
            System.out.println("El libro con ID " + id + " no existe.");
        }
    }
}
