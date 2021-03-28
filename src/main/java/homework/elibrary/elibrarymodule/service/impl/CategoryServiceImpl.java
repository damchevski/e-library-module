package homework.elibrary.elibrarymodule.service.impl;

import homework.elibrary.elibrarymodule.repository.CategoryRepository;
import homework.elibrary.elibrarymodule.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
