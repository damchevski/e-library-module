package homework.elibrary.elibrarymodule;

import homework.elibrary.elibrarymodule.model.Author;
import homework.elibrary.elibrarymodule.model.Book;
import homework.elibrary.elibrarymodule.model.Country;
import homework.elibrary.elibrarymodule.model.dto.BookDto;
import homework.elibrary.elibrarymodule.model.enums.BookCategory;
import homework.elibrary.elibrarymodule.repository.AuthorRepository;
import homework.elibrary.elibrarymodule.repository.BookRepository;
import homework.elibrary.elibrarymodule.repository.CategoryRepository;
import homework.elibrary.elibrarymodule.repository.CountryRepository;
import homework.elibrary.elibrarymodule.service.AuthorService;
import homework.elibrary.elibrarymodule.service.BookService;
import homework.elibrary.elibrarymodule.service.CategoryService;
import org.junit.Before;
import org.junit.experimental.categories.Categories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
 class ELibraryModuleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
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

        for (Book a :
                this.bookService.getAll()) {
            this.bookRepository.delete(a);
        }
        books = this.bookService.getAll();

        for (Author a :
                this.authorService.getAll()) {
            this.authorRepository.delete(a);
        }
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
        assertThrows(InvalidDataAccessApiUsageException.class,
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

        assertThrows(NullPointerException.class,
                () -> this.bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTFF() {
        this.authorRepository.save(author);
        this.bookDto.author = null;

        assertThrows(InvalidDataAccessApiUsageException.class,
                () -> this.bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTTT() {
        author = this.authorRepository.save(author);
        bookDto.author = author.getId();

        this.bookDto.availableCopies = null;

        assertTrue(
                this.bookService.addBook(bookDto).isPresent());
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

        assertThrows(InvalidDataAccessApiUsageException.class, () -> this.bookService.editBook(book.getId(), bookDto));
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
        book.setId(book.getId()+1);

        bookDto.name = "EditBookTest";
        assertEquals(Optional.empty(), bookService.editBook(book.getId(), bookDto));
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




}
