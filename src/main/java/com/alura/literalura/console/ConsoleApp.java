package com.alura.literalura.console;

import com.alura.literalura.dto.AuthorDTO;
import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.entity.Author;
import com.alura.literalura.entity.Book;
import com.alura.literalura.service.AuthorService;
import com.alura.literalura.service.BookService;
import com.alura.literalura.service.GutendexService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    private final GutendexService gutendexService;
    private final BookService bookService;
    private final AuthorService authorService; // Inyección de AuthorService


    public ConsoleApp(GutendexService gutendexService, BookService bookService, AuthorService authorService) {
        this.gutendexService = gutendexService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1 -> searchBooks(scanner);
                case 2 -> showBooks();
                case 3 -> deleteBook(scanner);
                case 4 -> showAuthorsByBook(scanner);
                case 5 -> showAllAuthors();
                case 6 -> exit();
                case 7 -> showBooksByLanguage(scanner);
                case 8 -> showAuthorsAliveInYear(scanner);


                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // Mostrar el menú de opciones
    private void showMenu() {
        System.out.println("Catálogo de Libros - Opciones:");
        System.out.println("1. Buscar libros");
        System.out.println("2. Mostrar libros guardados");
        System.out.println("3. Eliminar libro por ID");
        System.out.println("4. Mostrar autores por libro");
        System.out.println("5. Mostrar todos los autores");
        System.out.println("6. Salir");
        System.out.println("7. Mostrar cantidad de libros por idioma");
        System.out.println("8. Listar autores vivos en un año determinado");
        System.out.print("Seleccione una opción: ");
    }

    // Obtener una opción válida del usuario
    private int getUserChoice(Scanner scanner) {
        int choice = -1;
        boolean validChoice = false;
        while (!validChoice) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume el salto de línea
                validChoice = true; // Si no se lanza una excepción, la opción es válida
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
        return choice;
    }

    // Método para buscar libros y mostrar resultados
    private void searchBooks(Scanner scanner) {
        System.out.print("Ingrese término de búsqueda: ");
        String query = scanner.nextLine();
        List<GutendexResponse.Book> books = gutendexService.searchBooksByTitle(query);

        // Usar el método displayBooks para mostrar los resultados
        displayBooks(books);

        if (books != null && !books.isEmpty()) {
            System.out.print("¿Desea guardar alguno de estos libros? (s/n): ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("s")) {
                System.out.print("Ingrese el número del libro que desea guardar: ");
                try {
                    int bookIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    if (bookIndex >= 0 && bookIndex < books.size()) {
                        gutendexService.storeBook(books.get(bookIndex));
                    } else {
                        System.out.println("Selección inválida. Asegúrese de ingresar un número válido.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número válido.");
                }
            }
        }
    }

    // Mostrar libros guardados
    @Transactional
    public void showBooks() {
        List<BookDTO> books = bookService.getAllBooksDTO();
        if (books.isEmpty()) {
            System.out.println("No hay libros guardados.");
        } else {
            books.forEach(book -> System.out.println(book));
        }
    }

    // Eliminar un libro
    private void deleteBook(Scanner scanner) {
        System.out.print("Ingrese ID del libro a eliminar: ");
        Long id = null;
        boolean validId = false;
        while (!validId) {
            try {
                id = scanner.nextLong();
                scanner.nextLine(); // Consume el salto de línea
                validId = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número válido para el ID.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }
        bookService.deleteBookById(id);
        System.out.println("Libro eliminado.");
    }

    // Mostrar autores por libro
    @Transactional
    public void showAuthorsByBook(Scanner scanner) {
        System.out.print("Ingrese ID del libro: ");
        Long bookId = getValidLong(scanner);
        List<AuthorDTO> authors = authorService.getAuthorsByBookId(bookId);
        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores para el libro con ID " + bookId);
        } else {
            System.out.println("Autores para el libro con ID " + bookId + ":");
            authors.forEach(author -> System.out.println(author));
        }
    }

    // Mostrar todos los autores
    @Transactional
    public void showAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            authors.forEach(author -> System.out.println(author));
        }
    }

    // Salir de la aplicación
    private void exit() {
        System.out.println("Saliendo...");
        System.exit(0);
    }





    private void displayBooks(List<GutendexResponse.Book> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No se encontraron libros para el término de búsqueda.");
            return;
        }

        System.out.println("Libros encontrados:");
        for (int i = 0; i < books.size(); i++) {
            GutendexResponse.Book book = books.get(i);

            String authors = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? book.getAuthors().stream()
                    .map(author -> author.getName() + " (" + author.getBirth_year() + " - " + author.getDeath_year() + ")")
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin autor")
                    : "Sin autor";

            System.out.printf("%d. %s por %s%n", i + 1, book.getTitle(), authors);

            // Mostrar detalles de los autores utilizando AuthorDTO
            // Mostrar detalles de los autores utilizando AuthorDTO
            for (GutendexResponse.Author author : book.getAuthors()) {
                // Convertir GutendexResponse.Author a Author (com.alura.literalura.entity.Author)
                Author entityAuthor = new Author(
                        author.getName(),    // Nombre del autor
                        author.getBirth_year(),  // Año de nacimiento
                        author.getDeath_year(),  // Año de muerte
                        null                 // Libro asociado (no se especifica aquí)
                );

                AuthorDTO authorDTO = new AuthorDTO(entityAuthor);  // Crear AuthorDTO con la entidad Author
                System.out.println(authorDTO); // Imprime el AuthorDTO con los detalles del autor
            }

        }
    }

    // Método auxiliar para obtener un número válido
    private Long getValidLong(Scanner scanner) {
        Long value = null;
        boolean valid = false;
        while (!valid) {
            try {
                value = scanner.nextLong();
                scanner.nextLine(); // Consumir el salto de línea
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
        return value;
    }



    // Mostrar cantidad de libros por idioma
    private void showBooksByLanguage(Scanner scanner) {
        System.out.print("Ingrese los idiomas separados por coma (por ejemplo, 'en,es,fr'): ");
        String input = scanner.nextLine();
        String[] languages = input.split(",");

        if (languages.length < 2) {
            System.out.println("Debe ingresar al menos dos idiomas.");
            return;
        }

        for (String language : languages) {
            String trimmedLanguage = language.trim();
            long count = bookService.countBooksByLanguage(trimmedLanguage);
            System.out.printf("Cantidad de libros en '%s': %d%n", trimmedLanguage, count);
        }
    }

    // Mostrar autores vivos en un año específico
    private void showAuthorsAliveInYear(Scanner scanner) {
        System.out.print("Ingrese el año para buscar autores vivos: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un año válido.");
            return;
        }

        List<AuthorDTO> authors = authorService.getAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.printf("No se encontraron autores vivos en el año %d.%n", year);
        } else {
            System.out.printf("Autores vivos en el año %d:%n", year);
            authors.forEach(author -> System.out.println(author));
        }
    }

}