package homework.elibrary.elibrarymodule.model;

import javax.persistence.Entity;
import javax.persistence.*;

import homework.elibrary.elibrarymodule.model.enums.BookCategory;
import lombok.Data;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @ManyToOne
    private Author author;

    private Integer availableCopies;

    public Book() {
    }

    public Book(String name, BookCategory bookCategory, Author author, Integer availableCopies) {
        this.name = name;
        this.category = bookCategory;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public void setName(String name) {
        this.name = name;
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
}
