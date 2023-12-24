package impl;

import interfaces.CommentRepository;
import models.Category;
import models.Comment;
import models.Task;
import models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentRepositoryJdbcImpl implements CommentRepository {
    private DataSource dataSource;// соединение с бд
    private static final String SQL_SELECT_COMMENTS_BY_TASK_ID = "SELECT * FROM comments WHERE task_id = ?";
    //запрос выбора комментариев по id задачи
    private static final String SQL_INSERT_INTO_COMMENTS = "INSERT INTO comments (task_id, text, created_at) VALUES (?, ?, ?)";
    //запрос создания нового комментария
    private static final String SQL_DELETE_COMMENT = "DELETE FROM comments WHERE comment_id = ?";
    //запрос удаления комментария

    public CommentRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Comment> getCommentsByTaskId(Long taskId) {
        //получение списка комментариев по task id
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); //попытка подсоединения к бд
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COMMENTS_BY_TASK_ID))
        //используем preparedStatement для запроса по task id
        {
            preparedStatement.setLong(1, taskId);
            // значение устанавливаем в 1 параметр

            try (ResultSet resultSet = preparedStatement.executeQuery())
            // executeQuery для возвращения resultSet с полученными данными
            // ResultSet позволяет получить построчный доступ к запросам
            {
                while (resultSet.next()) {
                    Comment comment = mapResultSetToComment(resultSet);
                    //mapResultSetToComment принимает resultSet и преобразует
                    //данные в объект comment
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка получения комментариев по task id", e);
        }
        return comments;
    }

    @Override
    public void createComment(Comment comment) {//создание комментария в бд
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_COMMENTS)) {
            preparedStatement.setLong(1, comment.getTask().getTaskId());
            preparedStatement.setString(2, comment.getText());
            //устанавливает параметр 2 и возвращает текст комментария
            preparedStatement.setTimestamp(3, Timestamp.from(comment.getCreatedAt().toInstant()));
            //установка временной метки, toInstant преобразует date в момент времени Instant

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка создания комментария", e);
        }
    }

    @Override
    public void deleteComment(Long commentId) {//удаление комментария
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COMMENT)) {
            preparedStatement.setLong(1, commentId);
            preparedStatement.executeUpdate();//
        } catch (SQLException e) {
            throw new IllegalArgumentException("Ошибка удаления комментария", e);
        }
    }

    private Comment mapResultSetToComment(ResultSet resultSet) throws SQLException {
        User user = getUserFromResultSet(resultSet);

        Task task = new Task(
                resultSet.getLong("task_id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created_at"),
                resultSet.getDate("due_date"),
                getCategoryFromResultSet(resultSet)
        );
        // маппинг для соотношения данных из бд с объектами программы

        return Comment.builder()
                .commentId(resultSet.getLong("comment_id"))
                .task(task)
                .user(user)
                .text(resultSet.getString("text"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .build();
        //используем билдер для создания объекта comment
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .user_id(resultSet.getLong("user_id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .build();
    //выполняет маппинг из resultSet в объект User
    }

    private Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        return Category.builder()
                .categoryId(resultSet.getLong("category_id"))
                .name(resultSet.getString("category_name"))
                .description(resultSet.getString("category_description"))
                .build();
        //выполняет маппинг из resultSet в объект Category

    }
}






