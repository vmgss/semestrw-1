package filter;

import dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RolesFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserDto currentUser = (UserDto) servletRequest.getAttribute("user");

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if (request.getMethod().equals("POST") && request.getServletPath().equals("/cars")) {
            if (currentUser != null && currentUser.isAdmin()) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(403);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}