package com.alura.literalura.service;

import com.alura.literalura.entity.Book;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(String title, String author, String downloadLink) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setDownloadLink(downloadLink);
        return bookRepository.save(book);
    }

    public void showBooks() {
        bookRepository.findAll().forEach(System.out::println);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}