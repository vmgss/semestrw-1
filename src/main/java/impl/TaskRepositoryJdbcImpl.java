package impl;

import interfaces.TaskRepository;
import models.Category;
import models.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryJdbcImpl implements TaskRepository {
    private DataSource dataSource;
    private static final String SQL_INSERT_INTO_TASKS =
            "INSERT INTO tasks (title, description, due_date, created_at, category_id) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL_TASKS = "SELECT * FROM tasks";
    private static final String SQL_SELECT_TASKS_BY_CATEGORY = "SELECT * FROM tasks WHERE category_id = ?";
    private static final String SQL_SELECT_TASK_BY_ID = "SELECT * FROM tasks WHERE task_id = ?";
    private static final String SQL_DELETE_TASK = "DELETE FROM tasks WHERE task_id = ?";

    public TaskRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTask(Task task) {//создание задачи

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_TASKS)) {
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(5, task.getCategory().getCategoryId());

            preparedStatement.executeUpdate();//вносим изменения
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания задачи", e);
        }
    }

    @Override
    public List<Task> getAllTasks() {//получение всех задач
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TASKS)) {

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при получении всех задач", e);
        }
        return tasks;
    }

    @Override
    public List<Task> getTasksByCategory(Category category) {//получение задач по категории
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TASKS_BY_CATEGORY)) {

            preparedStatement.setLong(1, category.getCategoryId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = mapResultSetToTask(resultSet);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при получении задач по категории", e);
        }
        return tasks;
    }

    @Override
    public Task getTaskById(Long taskId) {// получение задач по id
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_TASK_BY_ID)) {

            preparedStatement.setLong(1, taskId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToTask(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при получении задачи по ID", e);
        }
        return null;
    }

    @Override
    public void deleteTask(Long taskId) {//удаление задач
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_TASK)) {

            preparedStatement.setLong(1, taskId);
            preparedStatement.executeUpdate();//внесение изменений
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при удалении задачи", e);
        }
    }


    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        return Task.builder()
                .taskId(resultSet.getLong("task_id"))
                .title(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .dueDate(resultSet.getDate("due_date"))
                .build();
    }
}


