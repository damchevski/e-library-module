package homework.elibrary.elibrarymodule.repository;

import homework.elibrary.elibrarymodule.model.enums.BookCategory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryRepository {
    public List<String> getAllCategories() {
        return Arrays.stream(BookCategory.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    public BookCategory getCategory(String category) {
        switch (category) {
            case "NOVEL":
                return BookCategory.NOVEL;
            case "THRILLER":
                return BookCategory.THRILLER;
            case "HISTORY":
                return BookCategory.HISTORY;
            case "FANTASY":
                return BookCategory.FANTASY;
            case "BIOGRAPHY":
                return BookCategory.BIOGRAPHY;
            case "CLASSICS":
                return BookCategory.CLASSICS;
            case "DRAMA":
                return BookCategory.DRAMA;
            default:
                return null;
        }
    }
}
