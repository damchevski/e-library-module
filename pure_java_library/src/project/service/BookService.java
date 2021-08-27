package project.service;

import project.model.Book;
import project.model.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();
    Optional<Book> getBook(Long id);
    Optional<Book> addBook(BookDto bookDto);
    Optional<Book> editBook(Long id, BookDto bookDto);
    Optional<Book> deleteBook(Long id);
    Boolean takeBook(Long id);
    void minusBook(Book book);
}
