package Testing.neededClasses;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import Testing.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Assert;
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
        Assert.assertEquals(authorService.getAll().size(), 0);
    }

    @Test
    public void getAllBooksFT() {
        authorRepository.save(author);
        bookRepository.save(book);
        assertTrue(bookService.getAll().size() > 0);
    }

    @Test
    public void getAllBooksTF() {
        Assert.assertEquals(0, bookService.getAll().size());
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

        Assert.assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
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

        Assert.assertEquals(Optional.empty(), bookService.deleteBook((long) -5));
    }

    @Test
    public void deleteBookFF() {
        authorRepository.save(author);
        book = bookRepository.save(book);

        Assert.assertEquals(book.getName(), bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void takeBookFT() {
        authorRepository.save(author);
        bookRepository.save(book);

        Assert.assertEquals(false, bookService.takeBook((long) -5));
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
        Assert.assertEquals(bookDto.name, bookService.editBook(book.getId(), bookDto).get().getName());
    }

    @Test
    public void editBookBookDoesNotExist() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.name = "EditBookTest";
        Assert.assertEquals(Optional.empty(), bookService.editBook(book.getId() + 1, bookDto));
    }

    @Test
    public void deleteBookSuccess() {
        authorRepository.save(author);
        bookRepository.save(book);

        Assert.assertEquals(book.getName(), bookService.deleteBook(book.getId()).get().getName());
    }

    @Test
    public void deleteBookBookDoesNotExist() {
        authorRepository.save(author);
        bookRepository.save(book);

        Assert.assertEquals(Optional.empty(), bookService.deleteBook(-1L));
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

    @Test
    public void setAuthorMutation() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        Author newAuthor = authorRepository.save(new Author("NewName", "NewSurname", new Country("Germany", "Europe")));

        bookDto.author = newAuthor.getId();

        Assert.assertEquals(bookDto.author, bookService.editBook(book.getId(), bookDto).get().getAuthor().getId());
    }

    @Test
    public void setCopiesMutation() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.availableCopies = 69;

        Assert.assertEquals(Integer.valueOf(69), bookService.editBook(book.getId(), bookDto).get().getAvailableCopies());
    }

    @Test
    public void setCategoryMutation() {
        author = authorRepository.save(author);
        bookDto.author = author.getId();

        Book book = bookService.addBook(bookDto).get();

        bookDto.category = "FANTASY";

        Assert.assertEquals(BookCategory.FANTASY, bookService.editBook(book.getId(), bookDto).get().getCategory());
    }

    @Test
    public void takeBookAvailableCopiesMutation(){
        authorRepository.save(author);
        bookDto.author = author.getId();

        bookDto.availableCopies = 1;
        Book book = bookService.addBook(bookDto).get();

        assertTrue(bookService.takeBook(book.getId()));
    }

    @Test
    public void takeBookMinusBookMutation(){
        authorRepository.save(author);
        bookDto.author = author.getId();

        bookDto.availableCopies = 2;
        Book book = bookService.addBook(bookDto).get();

        bookService.takeBook(book.getId());
        assertEquals(Integer.valueOf(1), bookService.getBook(book.getId()).get().getAvailableCopies());
    }
}