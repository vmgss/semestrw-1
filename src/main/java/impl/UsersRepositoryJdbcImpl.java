package impl;

import interfaces.UsersRepository;
import models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource dataSource;

    private static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (username, passwordHash, email) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User entity) {//сохранение пользователя в бд
        try (Connection connection = dataSource.getConnection();//подключение к бд
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_USERS))
        //выполнение запроса к бд
        {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPasswordHash());
            preparedStatement.setString(3, entity.getEmail());

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


    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .passwordHash(resultSet.getString("passwordHash"))
                .email(resultSet.getString("email"))
                .build();
    }
}

