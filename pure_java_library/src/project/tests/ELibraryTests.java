package project.tests;

import org.junit.jupiter.api.*;
import project.model.Author;
import project.model.Book;
import project.model.Country;
import project.model.dto.BookDto;
import project.model.enums.BookCategory;
import project.repository.AuthorRepository;
import project.repository.BookRepository;
import project.repository.CategoryRepository;
import project.repository.CountryRepository;
import project.service.AuthorService;
import project.service.BookService;
import project.service.CategoryService;
import project.service.impl.AuthorServiceImpl;
import project.service.impl.BookServiceImpl;
import project.service.impl.CategoryServiceImpl;

import java.util.*;

//import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ELibraryTests {
    @BeforeAll
    void init() {
        this.authorRepository = new AuthorRepository();
        this.countryRepository = new CountryRepository();
        this.bookRepository = new BookRepository();
        this.categoryRepository = new CategoryRepository();
        this.authorService = new AuthorServiceImpl(authorRepository);
        this.bookService = new BookServiceImpl(bookRepository, authorRepository, categoryRepository);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
    }

    private AuthorService authorService;

    private BookService bookService;

    private CategoryService categoryService;

    private AuthorRepository authorRepository;

    private CountryRepository countryRepository;

    private BookRepository bookRepository;

    private CategoryRepository categoryRepository;

    private List<Author> authors;
    private List<Book> books;
    private List<String> categories;

    private Author author;
    private BookCategory category;
    private Book book;
    private BookDto bookDto;
    private Country country;

    @BeforeEach
    public void setup() {
        this.bookRepository.clear();
        books = this.bookService.getAll();

        this.authorRepository.clear();
        authors = this.authorService.getAll();

        categories = this.categoryService.getAll();

        country = new Country("Macedonia", "Europe");
        this.countryRepository.save(country);

        author = new Author("nameTest", "surnameTest", country);
        category = BookCategory.CLASSICS;
        book = new Book("Book1Test", category, author, 55);
        bookDto = new BookDto("Book1Test", category.name(), author.getId(), 55);
    }

    //Tests designed with Input Space Method

    @Test
    public void getAllAuthorsFT() {
        this.authorRepository.save(author);
        assertTrue(this.authorService.getAll().size() > 0);
    }

    @Test
    public void getAllAuthorsTF() {
        assertEquals(this.authorService.getAll().size(), 0);
    }

    @Test
    public void getAllBooksFT() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);
        assertTrue(this.bookService.getAll().size() > 0);
    }

    @Test
    public void getAllBooksTF() {
        assertEquals(0, this.bookService.getAll().size());
    }

    @Test
    public void getBookFF() {
        this.authorRepository.save(author);
        book = this.bookRepository.save(book);

        assertTrue(this.bookService.getBook(book.getId()).isPresent());
    }

    @Test
    public void getBookFT() {
        assertThrows(NullPointerException.class,
                () -> this.bookService.getBook(null));
    }

    @Test
    public void addBookFTTF() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        this.bookService.addBook(bookDto);
        assertTrue(this.bookService.getAll().size() > 0);
    }

    @Test
    public void addBookTTTF() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        this.bookDto.name = null;

        assertTrue(this.bookService.addBook(bookDto).isPresent());
    }

    @Test
    public void addBookFFTF() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        this.bookDto.category = null;
//        this.bookService.addBook(bookDto);
        assertThrows(NullPointerException.class,
                () -> this.bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTFF() {
        this.authorRepository.save(author);
        this.bookDto.author = null;
        this.bookService.addBook(bookDto);
//        assertThrows(InvalidDataAccessApiUsageException.class,
//                () -> this.bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTTT() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        this.bookDto.availableCopies = null;

        assertTrue(this.bookService.addBook(bookDto).isPresent());
    }

    @Test
    public void editBookTF() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = this.bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest1";

        assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
    }

    @Test
    public void editBookFT() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = this.bookService.addBook(bookDto).get();

        bookDto.author = null;

//        this.bookService.editBook(book.getId(), bookDto);
        assertThrows(NoSuchElementException.class, () -> this.bookService.editBook(book.getId(), bookDto));
    }

    @Test
    public void deleteBookFT() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);

        assertEquals(Optional.empty(),this.bookService.deleteBook((long) -5));
    }

    @Test
    public void deleteBookFF() {
        this.authorRepository.save(author);
        book = this.bookRepository.save(book);

        assertEquals(book.getName(), this.bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void takeBookFT() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);

        assertEquals(false,this.bookService.takeBook((long) -5));
    }

    @Test
    public void takeBookFF() {
        this.authorRepository.save(author);
        book = this.bookRepository.save(book);

        assertTrue(this.bookService.takeBook(book.getId()));
    }

    @Test
    public void getAllCategoriesFT() {
        assertTrue(this.categoryService.getAll().size() > 0);
    }

    @Test
    public void getAllCategoriesTF() {
        //categories is static enum always > 0
    }


    // Tests designed with Graph Method

    @Test
    public void editBookSuccess() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = this.bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest";
        assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
    }

    @Test
    public void editBookBookDoesNotExist() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = this.bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest";
        assertEquals(Optional.empty(), bookService.editBook(book.getId()+1, bookDto));
    }

    @Test
    public void deleteBookSuccess() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);

        assertEquals(book.getName(), this.bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void deleteBookBookDoesNotExist() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);

        assertEquals(Optional.empty(), this.bookService.deleteBook(-1L));
    }

    @Test
    public void takeBookSuccess() {
        this.authorRepository.save(author);
        this.bookRepository.save(book);

        assertTrue(this.bookService.takeBook(book.getId()));
    }

    @Test
    public void takeBookDoesNotExist() {
        this.authorRepository.save(author);

        assertFalse(this.bookService.takeBook(-1L));
    }

    @Test
    public void takeBookNoCopiesLeft() {
        this.authorRepository.save(author);
        book.setAvailableCopies(0);
        this.bookRepository.save(book);

        assertFalse(this.bookService.takeBook(book.getId()));
    }

    // Mutation Testing
//
//    @Test
//    public void getAllAuthorsMutation(){
//        this.authorRepository.save(author);
//
//        Assertions.assertNotEquals(Collections.emptyList(), this.authorService.getAll());
//    }
//
//    @Test
//    public void getAllAuthorsEmptyMutation(){
//        Assertions.assertEquals(Collections.emptyList(), this.authorService.getAll());
//    }
}