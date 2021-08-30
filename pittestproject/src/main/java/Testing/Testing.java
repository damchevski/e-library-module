package Testing;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.Before;

public class Testing {

   static AuthorRepository authorRepository = new AuthorRepository();
   static CountryRepository countryRepository = new CountryRepository();
    static BookRepository bookRepository = new BookRepository();
    static CategoryRepository categoryRepository = new CategoryRepository();

    static AuthorService authorService;

    static BookService bookService;

    static CategoryService categoryService;


    static List<Author> authors;
    static List<Book> books;
    static List<String> categories;

    static Author author;
    static BookCategory category;
    static Book book;
    static BookDto bookDto;
    static Country country;

    @Before
    public void setup() {

        authorService = new AuthorServiceImpl(authorRepository);
        bookService = new BookServiceImpl(bookRepository, authorRepository, categoryRepository);
        categoryService = new CategoryServiceImpl(categoryRepository);

        bookRepository.clear();
        books = bookService.getAll();

        authorRepository.clear();
        authors = authorService.getAll();

        categories = categoryService.getAll();

        country = new Country("Macedonia", "Europe");
        countryRepository.save(country);

        author = new Author("nameTest", "surnameTest", country);
        category = BookCategory.CLASSICS;
        book = new Book("Book1Test", category, author, 55);
        bookDto = new BookDto("Book1Test", category.name(), author.getId(), 55);
    }

    //Tests designed with Input Space Method

    @Test
    public void getAllAuthorsFT() {
        authorRepository.save(author);
        assertTrue(authorService.getAll().size() > 0);
    }

    @Test
    public void getAllAuthorsTF() {
        assertEquals(authorService.getAll().size(), 0);
    }

    @Test
    public void getAllBooksFT() {
        authorRepository.save(author);
        bookRepository.save(book);
        assertTrue(bookService.getAll().size() > 0);
    }

    @Test
    public void getAllBooksTF() {
        assertEquals(0, bookService.getAll().size());
    }

    @Test
    public void getBookFF() {
        authorRepository.save(author);
        book = bookRepository.save(book);

        assertTrue(bookService.getBook(book.getId()).isPresent());
    }

    @Test
    public void getBookFT() {
        assertThrows(NullPointerException.class,
                () -> bookService.getBook(null));
    }

    @Test
    public void addBookFTTF() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        bookService.addBook(bookDto);
        assertTrue(bookService.getAll().size() > 0);
    }

    @Test
    public void addBookTTTF() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        bookDto.name = null;

        assertTrue(bookService.addBook(bookDto).isPresent());
    }

    @Test
    public void addBookFFTF() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        bookDto.category = null;
//        this.bookService.addBook(bookDto);
        assertThrows(NullPointerException.class,
                () -> bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTFF() {
        authorRepository.save(author);
        bookDto.author = null;
        bookService.addBook(bookDto);
//        assertThrows(InvalidDataAccessApiUsageException.class,
//                () -> this.bookService.addBook(bookDto));
    }

    @Test
    public void addBookFTTT() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        bookDto.availableCopies = null;

        assertTrue(bookService.addBook(bookDto).isPresent());
    }

    @Test
    public void editBookTF() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest1";

        assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
    }

    @Test
    public void editBookFT() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.author = null;

//        this.bookService.editBook(book.getId(), bookDto);
        assertThrows(NoSuchElementException.class, () -> bookService.editBook(book.getId(), bookDto));
    }

    @Test
    public void deleteBookFT() {
        authorRepository.save(author);
        bookRepository.save(book);

        assertEquals(Optional.empty(), bookService.deleteBook((long) -5));
    }

    @Test
    public void deleteBookFF() {
        authorRepository.save(author);
        book = bookRepository.save(book);

        assertEquals(book.getName(), bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void takeBookFT() {
        authorRepository.save(author);
        bookRepository.save(book);

        assertEquals(false, bookService.takeBook((long) -5));
    }

    @Test
    public void takeBookFF() {
        authorRepository.save(author);
        book = bookRepository.save(book);

        assertTrue(bookService.takeBook(book.getId()));
    }

    @Test
    public void getAllCategoriesFT() {
        assertTrue(categoryService.getAll().size() > 0);
    }

    @Test
    public void getAllCategoriesTF() {
        //categories is static enum always > 0
    }


    // Tests designed with Graph Method

    @Test
    public void editBookSuccess() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest";
        assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
    }

    @Test
    public void editBookBookDoesNotExist() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest";
        assertEquals(Optional.empty(), bookService.editBook(book.getId()+1, bookDto));
    }

    @Test
    public void deleteBookSuccess() {
        authorRepository.save(author);
        bookRepository.save(book);

        assertEquals(book.getName(), bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void deleteBookBookDoesNotExist() {
        authorRepository.save(author);
        bookRepository.save(book);

        assertEquals(Optional.empty(), bookService.deleteBook(-1L));
    }

    @Test
    public void takeBookSuccess() {
        authorRepository.save(author);
        bookRepository.save(book);

        assertTrue(bookService.takeBook(book.getId()));
    }

    @Test
    public void takeBookDoesNotExist() {
        authorRepository.save(author);
        assertFalse(bookService.takeBook(-1L));
    }

    @Test
    public void takeBookNoCopiesLeft() {
        authorRepository.save(author);
        book.setAvailableCopies(0);
        bookRepository.save(book);

        assertFalse(bookService.takeBook(book.getId()));
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