package servlets.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/signOut")
public class SignOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        HttpSession session = request.getSession(false);
        //получает текущую сессию
        if (session != null) {
            session.invalidate();
        }
        //если сессия есть, она инвалидируется
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        //перенаправление на страницу входа
    }
}

