package servlets.user;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Task;
import models.User;
import service.TaskService;
import service.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();

        // Инициализация необходимых сервисов или репозиториев, если требуется
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
        // Аналогично проинициализируйте taskService, если это необходимо
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получите список пользователей
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);

        // Перейдите на страницу администратора
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "delete":
                    handleDeleteUser(request, response);
                    break;
                case "changeRole":
                    handleChangeUserRole(request, response);
                    break;
                default:
                    // Обработка других действий, если необходимо
                    break;
            }
        }
        // Дополнительная обработка POST-запросов, если необходимо
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("userId"));
        // Реализуйте логику удаления пользователя по userId
        userService.deleteUser(userId);

        // После удаления пользователя, обновите список пользователей и перенаправьте на страницу админа
        refreshUserListAndForward(request, response);
    }

    private void handleChangeUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.parseLong(request.getParameter("userId"));
        String newRole = request.getParameter("newRole");

        userService.changeUserRole(userId, newRole);

        // После изменения роли обновляем только конкретного пользователя в списке
        // и перенаправляем на страницу админа
        List<User> users = userService.getAllUsers();
        User updatedUser = users.stream().filter(user -> user.getUser_id().equals(userId)).findFirst().orElse(null);

        if (updatedUser != null) {
            updatedUser.setRole(newRole);
        }

        request.setAttribute("users", users);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }



    private void refreshUserListAndForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);

        // Перейдите на страницу администратора
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }
}







