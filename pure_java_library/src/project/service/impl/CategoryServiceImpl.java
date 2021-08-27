package project.service.impl;

import project.repository.CategoryRepository;
import project.service.CategoryService;

import java.util.List;

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
