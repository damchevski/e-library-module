package Testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    private List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    public List<Book> getAll() {
        return books;
    }

    public Optional<Book> findBook(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book save(Book book) {
        books.removeIf(b -> b.getId().equals(book.getId()));
        books.add(book);
        return book;
    }

    public boolean delete(Book book) {
        return books.removeIf(b -> b.getId().equals(book.getId()));
    }

    public void clear(){
        this.books = new ArrayList<>();
    }
}
