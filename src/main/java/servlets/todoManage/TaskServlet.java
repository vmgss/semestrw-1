package servlets.todoManage;

import dto.TaskDto;
import impl.*;
import interfaces.TaskRepository;
import interfaces.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Task;
import org.postgresql.ds.PGSimpleDataSource;
import service.CategoryService;
import service.CommentService;
import service.TaskService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        // Инициализация сервисов
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("55555");

        TaskRepository taskRepository = new TaskRepositoryJdbcImpl(dataSource);
        this.taskService = new TaskServiceImpl(taskRepository);
        this.categoryService = new CategoryServiceImpl(new CategoryRepositoryJdbcImpl(dataSource));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        //получаем список всех задач
        request.setAttribute("tasks", tasks);
        request.getRequestDispatcher("/jsp/tasks.jsp").forward(request, response);
        //перенаправляет на страницу задач
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String taskIdStr = request.getParameter("task_id");
        Long taskId = Long.parseLong(taskIdStr);
        String newTitle = request.getParameter("new_title");
        String newDescription = request.getParameter("new_description");

        // Получите существующую задачу
        Task existingTask = taskService.getTaskById(taskId);

        // Обновите задачу новыми значениями
        existingTask.setTitle(newTitle);
        existingTask.setDescription(newDescription);

        // Сохраните обновленную задачу
        taskService.updateTask(existingTask);

        // Перенаправьте на страницу задач
        response.sendRedirect(request.getContextPath() + "/tasks");
    }
}







