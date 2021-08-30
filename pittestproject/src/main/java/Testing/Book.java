package Testing;

import java.util.Random;

public class Book {

    private Long id;

    private String name;

    private BookCategory category;

    private Author author;

    private Integer availableCopies;

    public BookCategory getCategory() {
        return category;
    }

    public Author getAuthor() {
        return author;
    }

    public Book() {
    }

    public Book(String name, BookCategory bookCategory, Author author, Integer availableCopies) {
        this.id = new Random().nextLong();
        this.name = name;
        this.category = bookCategory;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void takeBook() {
        this.availableCopies -= 1;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
