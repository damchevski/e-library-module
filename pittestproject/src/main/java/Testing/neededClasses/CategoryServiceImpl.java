package Testing.neededClasses;

import java.util.List;

import Testing.*;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getAll() {
        return this.categoryRepository.getAllCategories();
    }
}
