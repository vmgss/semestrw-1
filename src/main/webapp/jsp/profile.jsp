User
<%@ page import="models.User" %>
<%@ page import="models.Task" %>
<%@ page import="models.Category" %>
<%@ page import="models.Comment" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Профиль</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/profile.css">
</head>

<body>
<div id="profile" class="vh-100 d-flex justify-content-center align-items-center">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <!-- Боковое меню -->
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Привет, ${user.username}!</h5>
                        <form action="${pageContext.request.contextPath}/signOut" method="post" id="signOutForm">
                            <input type="hidden" name="dummy" value="dummy">
                            <a href="#" class="btn btn-danger" onclick="document.getElementById('signOutForm').submit()">Выход</a>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <!-- Содержимое профиля -->
                <div class="card bg-white">
                    <div class="card-body p-5">
                        <h2 class="card-title mb-4">Ваши задачи</h2>
                        <!-- Отобразите задачи пользователя -->
                        <c:forEach var="task" items="${tasks}">
                            <div class="mb-3">
                                <h4>${task.title}</h4>
                                <p>${task.description}</p>
                                <!-- Добавьте другие поля задачи по вашему усмотрению -->
                            </div>
                        </c:forEach>
                        <!-- Отобразите категории пользователя -->
                        <h2 class="card-title mb-4">Ваши категории</h2>
                        <c:forEach var="category" items="${categories}">
                            <div class="mb-3">
                                <h4>${category.name}</h4>
                                <p>${category.description}</p>
                                <!-- Добавьте другие поля категории по вашему усмотрению -->
                            </div>
                        </c:forEach>
                        <!-- Отобразите комментарии пользователя -->
                        <h2 class="card-title mb-4">Ваши комментарии</h2>
                        <c:forEach var="comment" items="${comments}">
                            <div class="mb-3">
                                <p>${comment.text}</p>
                                <!-- Добавьте другие поля комментария по вашему усмотрению -->
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>











