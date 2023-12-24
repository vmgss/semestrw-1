package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/auth/*")
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // преобразуем запросы к нужному виду
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Boolean isAuthenticated = false;
        Boolean sessionExists = session != null;
        Boolean isLoginPage = request.getRequestURI().equals("/signIn");

        if (sessionExists) {
            isAuthenticated = (Boolean) session.getAttribute("authenticated");
            if (isAuthenticated == null) {
                isAuthenticated = false;
            }
        }
        // если авторизован и запрашивает не логин или если не авторизован и запрашивает логин
        if(isAuthenticated && !isLoginPage || !isAuthenticated && isLoginPage) {
            // отдаем то что хочет
            filterChain.doFilter(request, response);
        } else if (isAuthenticated && isLoginPage) {
            // пользователь авторизован и просит страницу авторизации
            response.sendRedirect("/profile");
        } else {
            // не авторизован и просит другие страницы
            response.sendRedirect("/register");
        }

    }
}
