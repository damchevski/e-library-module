package homework.elibrary.elibrarymodule.model.dto;

public class BookDto {
    public String name;
    public String category;
    public Long author;
    public Integer availableCopies;

    public BookDto(String name, String category, Long author, Integer availableCopies){
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}
