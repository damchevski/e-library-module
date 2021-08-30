package Testing.neededClasses;

import Testing.*;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.getAll();
    }

    @Override
    public Optional<Book> getBook(Long id){
        if (id == null)
            throw new NullPointerException();
        return this.bookRepository.findBook(id);
    }

    @Override
    public Optional<Book> addBook(BookDto bookDto) {
        Author author = this.authorRepository.findAuthor(bookDto.author).orElse(null);
        BookCategory bookCategory = this.categoryRepository.getCategory(bookDto.category);

        Book newBook = new Book(bookDto.name, bookCategory, author, bookDto.availableCopies);

        this.bookRepository.save(newBook);
        return Optional.of(newBook);
    }

    @Override
    public Optional<Book> editBook(Long id, BookDto bookDto) {
        Book book = this.bookRepository.findBook(id).orElse(null);

        if (book == null) {
            return Optional.empty();
        }

        Author author = this.authorRepository.findAuthor(bookDto.author).get();
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
        Optional<Book> book = this.bookRepository.findBook(id);

        if (book.isEmpty()) {
            return Optional.empty();
        }

        this.bookRepository.delete(book.get());

        return book;
    }

    @Override
    public Boolean takeBook(Long id) {
        Optional<Book> book = this.bookRepository.findBook(id);

        if (book.isEmpty())
            return false;

        if (book.get().getAvailableCopies() - 1 < 0)
            return false;

//        this.applicationEventPublisher.publishEvent(new BookTakeEvent(book.get()));
        minusBook(book.get());

        return true;
    }

    @Override
    public void minusBook(Book book) {
        book.takeBook();
        this.bookRepository.save(book);
    }


}
