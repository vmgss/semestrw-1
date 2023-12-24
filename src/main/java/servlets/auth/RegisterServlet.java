package servlets.auth;

import impl.RegisterServiceImpl;
import impl.UserServiceImpl;
import impl.UsersRepositoryJdbcImpl;
import interfaces.UsersRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import service.RegisterService;
import service.UserService;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "55555";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    private UsersRepository usersRepository;
    private RegisterService registerService;

    @Override
    public void init() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        DataSource dataSource = new DriverManagerDataSource(DB_URL, DB_USER, DB_PASSWORD);

        usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        registerService = new RegisterServiceImpl(usersRepository);
        UserService userService = new UserServiceImpl(usersRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        String passwordHash = registerService.hashPassword(password);

        User newUser = new User(null, username, passwordHash, email,role);
        usersRepository.save(newUser);

        HttpSession session = request.getSession();
        session.setAttribute("username", newUser.getUsername());

        response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
    }
}



