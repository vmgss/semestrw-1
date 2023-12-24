<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.User" %>
<%@ page import="service.UserService" %>

<%
    ServletContext servletContext = request.getServletContext();
    UserService userService = (UserService) servletContext.getAttribute("userService");
    List<User> users = userService.getAllUsers();
%>

<html>
<head>
    <title>Admin Page</title>
</head>
<body>
<h1>Admin Page</h1>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Email</th>
        <th>Role</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% for (User user : users) { %>
    <tr>
        <td><%= user.getUser_id() %></td>
        <td><%= user.getUsername() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getRole() %></td>
        <td>
            <form action="/admin" method="post">
                <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                <select name="newRole">
                    <option value="USER" <%= user.getRole().equals("USER") ? "selected" : "" %>>User</option>
                    <option value="ADMIN" <%= user.getRole().equals("ADMIN") ? "selected" : "" %>>Admin</option>
                </select>
                <button type="submit">Change Role</button>
            </form>
            <form action="/admin" method="post">
                <input type="hidden" name="userId" value="<%= user.getUser_id() %>">
                <button type="submit" name="action" value="delete">Delete User</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
</body>
</html>







