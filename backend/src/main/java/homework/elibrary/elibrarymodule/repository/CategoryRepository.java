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
        return BookCategory.valueOf(category);
    }
}
