package service;

import models.Category;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);
    List<Category> getAllCategories();
    Category getCategoryById(Long categoryId);

}

