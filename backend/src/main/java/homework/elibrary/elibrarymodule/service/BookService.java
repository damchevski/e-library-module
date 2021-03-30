package homework.elibrary.elibrarymodule.service;

import homework.elibrary.elibrarymodule.model.Book;
import homework.elibrary.elibrarymodule.model.dto.BookDto;

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
