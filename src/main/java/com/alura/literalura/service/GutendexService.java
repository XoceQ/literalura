package com.alura.literalura.service;


import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;  // Repositorio de autores


    public GutendexService(RestTemplate restTemplate, ObjectMapper objectMapper, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    // Método para buscar libros por título
    public List<GutendexResponse.Book> searchBooksByTitle(String title) {
        String apiUrl = "https://gutendex.com/books?search=" + title;
        try {
            // Realizamos la solicitud GET y obtenemos la respuesta JSON
            String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

            // Convertimos la respuesta JSON en un objeto GutendexResponse
            GutendexResponse response = objectMapper.readValue(jsonResponse, GutendexResponse.class);

            // Retornamos la lista de resultados
            return response.getResults();
        } catch (Exception e) {
            System.out.println("Error al realizar la consulta o procesar la respuesta: " + e.getMessage());
            return null;
        }
    }

    // Método para almacenar un libro en la base de datos
    public void storeBook(GutendexResponse.Book bookResponse) {
        Book book = new Book();
        book.setTitle(bookResponse.getTitle());

        // Verificación de autores nulos o vacíos
        if (bookResponse.getAuthors() != null && !bookResponse.getAuthors().isEmpty()) {
            List<Author> authors = bookResponse.getAuthors().stream()
                    .filter(author -> author != null && author.getName() != null) // Filtrar autores nulos o sin nombre
                    .map(author -> new Author(author.getName(), book)) // Crear objeto Author con el nombre
                    .collect(Collectors.toList());
            book.setAuthors(authors);
        } else {
            book.setAuthors(new ArrayList<>()); // Si no hay autores, asignar una lista vacía
        }

        // Manejo del enlace de descarga
        if (bookResponse.getFormats() != null && !bookResponse.getFormats().isEmpty()) {
            book.setDownloadLink(bookResponse.getFormats().values().iterator().next());
        }

        // Manejo del idioma
        if (bookResponse.getLanguages() != null && !bookResponse.getLanguages().isEmpty()) {
            book.setLanguage(bookResponse.getLanguages().get(0));
        }

        book.setDownloadCount(1000); // Número de descargas predeterminado

        // Guardar el libro en la base de datos
        bookRepository.save(book);
        System.out.println("Libro guardado: " + book.getTitle());
    }

}