package listener;

import impl.*;
import interfaces.UsersRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import secure.AuthManager;
import service.RegisterService;

import javax.sql.DataSource;


@WebListener
public class AppContextListener implements ServletContextListener {
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "55555";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_DRIVER = "org.postgresql.Driver";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        DataSource dataSource = new DriverManagerDataSource(DB_URL, DB_USER, DB_PASSWORD);
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        servletContext.setAttribute("authManager", new AuthManagerImpl(usersRepository));
        servletContext.setAttribute("userService", new UserServiceImpl(usersRepository));
        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("signInService", new SignInServiceImpl(usersRepository, passwordEncoder));
    }
}



