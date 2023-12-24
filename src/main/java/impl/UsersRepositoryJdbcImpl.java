package impl;

import interfaces.UsersRepository;
import models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource dataSource;

    private static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (username, passwordHash, email, role) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_UPDATE_USER_ROLE = "UPDATE users SET role = ? WHERE user_id = ?";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User entity) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERS)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPasswordHash());
            preparedStatement.setString(3, entity.getEmail());

            // Убедитесь, что entity.getRole() не равен null перед установкой значения
            preparedStatement.setString(4, entity.getRole() != null ? entity.getRole() : "DEFAULT_ROLE");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка сохранения пользователя", e);
        }
    }


    @Override
    public Optional<User> findByUsername(String username)
    //поиск по имени пользователя
    {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            //результат обрабатывается в resultSet
            {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                    //если пользователь найден, возвращается Optional
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка поиска по имени пользователя", e);
        }
        return Optional.empty();
    }

    @Override
    public String getUserHashedPassword(String username) {
        //возвращает хэшированный пароль по имени пользователя
        String hashedPassword = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_BY_USERNAME))
        //выполняем запрос для получения
        {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password_hash");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hashedPassword;
    }
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при получении всех пользователей", e);
        }

        return users;
    }

    @Override
    public void deleteUser(Long userId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {

            preparedStatement.setLong(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при удалении пользователя", e);
        }
    }

    @Override
    public void changeUserRole(Long userId, String newRole) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_ROLE)) {

            preparedStatement.setString(1, newRole);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка при изменении роли пользователя", e);
        }
    }


    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .user_id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .passwordHash(resultSet.getString("passwordHash"))
                .email(resultSet.getString("email"))
                .role(resultSet.getString("role"))
                .build();
    }
}

