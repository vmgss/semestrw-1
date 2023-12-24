package servlets.auth;

import impl.SignInServiceImpl;
import interfaces.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import listener.AppContextListener;
import lombok.extern.slf4j.Slf4j;
import models.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import service.SignInService;
import service.UserService;
import java.io.IOException;
import java.util.Optional;
@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private SignInService signInService;

    @Override
    public void init() throws ServletException {
        super.init();

        // Получаем зависимости из контекста Spring
        UsersRepository usersRepository = (UsersRepository) getServletContext().getAttribute("usersRepository");
        PasswordEncoder passwordEncoder = (PasswordEncoder) getServletContext().getAttribute("passwordEncoder");

        // Инициализация SignInService
        this.signInService = new SignInServiceImpl(usersRepository, passwordEncoder);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Проверка аутентификации с использованием SignInService
        if (signInService.signIn(username, password)) {
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("authenticated", true);
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/signIn");
        }
    }
}








