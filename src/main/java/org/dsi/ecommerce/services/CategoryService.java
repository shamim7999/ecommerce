package org.dsi.ecommerce.services;

import org.dsi.ecommerce.models.Category;
import org.dsi.ecommerce.repositories.CategoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    public Category findByCategoryId(int categoryId) throws Exception {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new Exception("Resource Not Found"));
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(Sort.by("title"));
    }

    public long rowCount() {
        return categoryRepository.count();
    }
}
