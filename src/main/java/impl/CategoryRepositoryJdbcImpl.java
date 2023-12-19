package impl;

import interfaces.CategoryRepository;
import models.Category;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryJdbcImpl implements CategoryRepository {
    //реализация интерфейса CategoryRepository
    //берем категории из бд
    private DataSource dataSource; //соединение с бд
    private static final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String SQL_SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SQL_INSERT_INTO_CATEGORIES = "INSERT INTO categories (name, description) VALUES (?, ?)";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";

    public CategoryRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();//создает statement для sql-запросов
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CATEGORIES))
        //извлекаем все категории
        {

            while (resultSet.next()) {
                Category category = mapResultSetToCategory(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка получения категорий", e);
        }
        return categories;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_CATEGORY_BY_ID)) {
            preparedStatement.setLong(1, categoryId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка получения категории по ID", e);
        }

        return null; // Возвращаем null, если категория с заданным ID не найдена
    }


    @Override
    public void createCategory(Category category) { //создает новую категорию в бд
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_CATEGORIES)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());

            preparedStatement.executeUpdate();//изменяет данные в бд
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка создания категории", e);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) {// удаляет категорию из бд
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CATEGORY)) {
            preparedStatement.setLong(1, categoryId);//устанавливает значение типа long
            preparedStatement.executeUpdate();//изменяет данные в бд
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка удаления категории", e);
        }
    }

    private Category mapResultSetToCategory(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .categoryId(resultSet.getLong("category_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
        //метод для преобразования результата запроса в объект Category
    }
}




