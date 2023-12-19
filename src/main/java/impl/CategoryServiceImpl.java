package impl;

import interfaces.CategoryRepository;
import models.Category;
import service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    //реализация сервиса категорий
    private final CategoryRepository categoryRepository;
    //объявление репозитория для работы с данными категорий

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void createCategory(Category category) {
        categoryRepository.createCategory(category);
    }
    //вызов метода createCategory объекта categoryRepository (функциональность сохранения новой категории)

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }
    //вызов метода getAllCategories у categoryRepository, возвращает все категории

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }



}

