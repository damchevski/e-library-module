package homework.elibrary.elibrarymodule.service.impl;

import homework.elibrary.elibrarymodule.model.Author;
import homework.elibrary.elibrarymodule.model.Book;
import homework.elibrary.elibrarymodule.model.dto.BookDto;
import homework.elibrary.elibrarymodule.model.enums.BookCategory;
import homework.elibrary.elibrarymodule.model.events.BookTakeEvent;
import homework.elibrary.elibrarymodule.repository.AuthorRepository;
import homework.elibrary.elibrarymodule.repository.BookRepository;
import homework.elibrary.elibrarymodule.repository.CategoryRepository;
import homework.elibrary.elibrarymodule.service.BookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBook(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> addBook(BookDto bookDto) {
        Author author = this.authorRepository.getOne(bookDto.author);
        BookCategory bookCategory = this.categoryRepository.getCategory(bookDto.category);

        Book newBook = new Book(bookDto.name, bookCategory, author, bookDto.availableCopies);

        this.bookRepository.save(newBook);
        return Optional.of(newBook);
    }

    @Override
    public Optional<Book> editBook(Long id, BookDto bookDto) {
        Book book = this.bookRepository.findById(id).get();

        if (book == null) {
            return Optional.empty();
        }

        Author author = this.authorRepository.findById(bookDto.author).get();
        BookCategory bookCategory = this.categoryRepository.getCategory(bookDto.category);

        book.setAuthor(author);
        book.setCategory(bookCategory);
        book.setAvailableCopies(bookDto.availableCopies);
        book.setName(bookDto.name);

        this.bookRepository.save(book);

        return Optional.of(book);
    }

    @Override
    public Optional<Book> deleteBook(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);

        if (book.isEmpty()) {
            return Optional.empty();
        }

        this.bookRepository.delete(book.get());

        return book;
    }

    @Override
    public Boolean takeBook(Long id) {
        Optional<Book> book = this.bookRepository.findById(id);

        if (book.isEmpty())
            return false;

        if (book.get().getAvailableCopies() - 1 < 0)
            return false;

        this.applicationEventPublisher.publishEvent(new BookTakeEvent(book.get()));

        return true;
    }

    @Override
    public void minusBook(Book book) {
        book.takeBook();
        this.bookRepository.save(book);
    }


}
