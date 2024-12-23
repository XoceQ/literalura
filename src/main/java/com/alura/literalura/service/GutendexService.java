package com.alura.literalura.service;


import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
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

    public GutendexService(RestTemplate restTemplate, ObjectMapper objectMapper, BookRepository bookRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
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

        // Asignar la lista de autores
        if (bookResponse.getAuthors() != null && !bookResponse.getAuthors().isEmpty()) {
            // Crea una lista de objetos Author a partir de los nombres de los autores
            List<Author> authors = bookResponse.getAuthors().stream()
                    .map(author -> new Author(author.getName()))  // Crear objetos Author con el nombre
                    .collect(Collectors.toList());

            book.setAuthors(authors); // Asignar la lista de objetos Author
        } else {
            book.setAuthors(new ArrayList<>()); // Si no hay autores, asignar una lista vacía
        }


        // Extraer el enlace de descarga
        if (bookResponse.getFormats() != null && !bookResponse.getFormats().isEmpty()) {
            book.setDownloadLink(bookResponse.getFormats().values().iterator().next());
        } else {
            book.setDownloadLink(""); // Si no hay enlace, asignar cadena vacía
        }

        // Extraer el idioma
        if (bookResponse.getLanguages() != null && !bookResponse.getLanguages().isEmpty()) {
            book.setLanguage(bookResponse.getLanguages().get(0));
        } else {
            book.setLanguage(""); // Si no hay idioma, asignar cadena vacía
        }

        // Establecer el valor de descargas
        book.setDownloadCount(1000);  // O cualquier valor que necesites

        // Guardar el libro en la base de datos
        bookRepository.save(book);
        System.out.println("Libro guardado: " + book.getTitle());
    }


}