package com.alura.literalura.console;

import com.alura.literalura.service.BookService;
import com.alura.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
            System.out.println("Catálogo de Libros - Opciones:");
            System.out.println("1. Buscar libros");
            System.out.println("2. Guardar libro en base de datos");
            System.out.println("3. Mostrar libros guardados");
            System.out.println("4. Eliminar libro por ID");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (choice) {
                case 1 -> {
                    System.out.print("Ingrese término de búsqueda: ");
                    String query = scanner.nextLine();
                    String response = gutendexService.searchBooks(query);
                    System.out.println("Resultado de la búsqueda:");
                    System.out.println(response);
                }
                case 2 -> {
                    System.out.print("Ingrese título: ");
                    String title = scanner.nextLine();
                    System.out.print("Ingrese autor: ");
                    String author = scanner.nextLine();
                    System.out.print("Ingrese enlace de descarga: ");
                    String downloadLink = scanner.nextLine();
                    bookService.saveBook(title, author, downloadLink);
                    System.out.println("Libro guardado.");
                }
                case 3 -> {
                    System.out.println("Mostrando libros guardados:");
                    bookService.showBooks();
                }
                case 4 -> {
                    System.out.print("Ingrese ID del libro a eliminar: ");
                    Long id = scanner.nextLong();
                    bookService.deleteBookById(id);
                    System.out.println("Libro eliminado.");
                }
                case 5 -> {
                    System.out.println("Saliendo...");
                    System.exit(0);
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }
}
