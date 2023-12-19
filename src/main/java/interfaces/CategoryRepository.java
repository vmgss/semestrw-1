package interfaces;

import models.Category;

import java.util.List;

public interface CategoryRepository {
    Category getCategoryById(Long categoryId);

    void createCategory(Category category);
    List<Category> getAllCategories();

    void deleteCategory(Long categoryId);

}
