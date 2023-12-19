package servlets.user;

import impl.*;
import interfaces.TaskRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Category;
import models.Comment;
import models.Task;
import models.User;
import org.postgresql.ds.PGSimpleDataSource;
import service.CategoryService;
import service.CommentService;
import service.TaskService;
import service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private UserService userService;
    private TaskService taskService;
    private CategoryService categoryService;
    private CommentService commentService;

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
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username != null) {
            Optional<User> userOptional = userService.getUserByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                List<Task> tasks = taskService.getAllTasks();
                List<Category> categories = categoryService.getAllCategories();
                List<Comment> comments = commentService.getCommentsByTaskId(user.getId());

                request.setAttribute("user", user);
                request.setAttribute("tasks", tasks);
                request.setAttribute("categories", categories);
                request.setAttribute("comments", comments);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/profile.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/signIn");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/signIn");
        }
    }
}








