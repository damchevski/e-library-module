package project.repository;

import project.model.enums.BookCategory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
