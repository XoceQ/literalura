package com.alura.literalura.console;

import com.alura.literalura.dto.BookDTO;
import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.entity.Book;
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

    public ConsoleApp(GutendexService gutendexService, BookService bookService) {
        this.gutendexService = gutendexService;
        this.bookService = bookService;
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
                case 4 -> exit();
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
        System.out.println("4. Salir");
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

    // Método auxiliar para mostrar libros
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
                    .map(GutendexResponse.Author::getName)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin autor")
                    : "Sin autor";

            System.out.printf("%d. %s por %s%n", i + 1, book.getTitle(), authors);
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

    // Salir de la aplicación
    private void exit() {
        System.out.println("Saliendo...");
        System.exit(0);
    }
}