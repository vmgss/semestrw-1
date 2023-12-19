package servlets.auth;

import impl.RegisterServiceImpl;
import impl.SignInServiceImpl;
import impl.UsersRepositoryJdbcImpl;
import interfaces.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import secure.AuthManager;
import service.SignInService;
import service.UserService;
import java.io.IOException;
import java.util.Optional;
@WebServlet("/signIn")
@Slf4j
public class SignInServlet extends HttpServlet {
    private AuthManager authManager;
    private UserService userService;
    private UsersRepository usersRepository;
    private SignInService signInService;

    @Override
    public void init() throws ServletException {
        this.authManager = (AuthManager) getServletContext().getAttribute("authManager");
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.usersRepository = (UsersRepository) getServletContext().getAttribute("usersRepository");

        //экземпляр BCryptPasswordEncoder
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.signInService = new SignInServiceImpl(usersRepository, passwordEncoder);

        if (authManager == null || userService == null || usersRepository == null || signInService == null) {
            throw new ServletException("Ошибка инициализации.");
        }
        // Проверка на инициализацию
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // проверка существующей сессии
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("authenticated") != null) {
            // если пользователь  аутентифицирован, перенаправляем на профиль
            response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
        } else {
            // если нет, возвращаем на страницу входа
            request.getRequestDispatcher("/jsp/signIn.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Попытка аутентификации пользователя
        if (signInService.signIn(username, password)) {
            Optional<User> user = userService.getUserByUsername(username);
            if (user.isPresent()) {
                // Установка в сессию
                HttpSession session = req.getSession(true);
                session.setAttribute("authenticated", true);
                //устанавливает атрибут в контекст сервлета
                session.setAttribute("user", user.get());
                resp.sendRedirect(req.getContextPath() + "/jsp/profile");
                //собирает данные и отправляет на фронт
                return;
            }
        }

        resp.sendRedirect(req.getContextPath() + "/signIn?error=true");
        //если не прошла аутентификация , то обратно отправляет на страницу входа с параметром ошибки
    }
}





