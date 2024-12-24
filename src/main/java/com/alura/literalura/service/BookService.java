package com.alura.literalura.service;

import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // Método para almacenar un libro en la base de datos
    @Transactional
    public void storeBook(GutendexResponse.Book bookResponse) {
        Book book = new Book();
        book.setTitle(bookResponse.getTitle());

        // Asignar la lista de autores
        if (bookResponse.getAuthors() != null && !bookResponse.getAuthors().isEmpty()) {
            List<Author> authors = bookResponse.getAuthors().stream()
                    .map(author -> new Author(author.getName(), null, null, book))  // Crear objetos Author con el nombre y asociarlos al libro
                    .collect(Collectors.toList());
            book.setAuthors(authors);  // Asignar la lista de objetos Author
        } else {
            book.setAuthors(new ArrayList<>()); // Si no hay autores, asignar una lista vacía
        }

        // Extraer el enlace de descarga
        if (bookResponse.getFormats() != null && !bookResponse.getFormats().isEmpty()) {
            book.setDownloadLink(bookResponse.getFormats().values().iterator().next());
        }

        // Extraer el idioma
        if (bookResponse.getLanguages() != null && !bookResponse.getLanguages().isEmpty()) {
            book.setLanguage(bookResponse.getLanguages().get(0));
        }

        book.setDownloadCount(1000);

        // Guardar el libro en la base de datos
        bookRepository.save(book);
        System.out.println("Libro guardado: " + book.getTitle());
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
    public long countBooksByLanguage(String language) {
        return bookRepository.countByLanguage(language); // Consulta al repositorio
    }

}