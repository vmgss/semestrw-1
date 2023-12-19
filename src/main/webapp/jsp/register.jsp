<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/your_custom_style.css">

</head>

<body>
<div id="registration-form" class="vh-100 d-flex justify-content-center align-items-center">
    <div class="container">
        <div class="row d-flex justify-content-center">
            <div class="col-12 col-md-8 col-lg-6">
                <div class="card bg-white">
                    <div class="card-body p-5">
                        <form class="mb-3 mt-md-4 form-group" method="post" action="register">
                            <h2 class="form-title fw-bold mb-2 text-uppercase ">UTask</h2>
                            <div class="mb-3 form-group">
                                <label for="username" class="form-label">Имя пользователя</label>
                                <input type="text" class="form-control" id="username" name="username"
                                       placeholder="Имя пользователя" required>
                            </div>
                            <div class="mb-3 form-group">
                                <label for="email" class="form-label">Почта</label>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="example@example.com" required>
                            </div>
                            <div class="mb-3 form-group">
                                <label for="password" class="form-label">Пароль</label>
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="*******" required>
                            </div>
                            <div class="d-grid">
                                <input class="btn btn-outline-dark btn-submit" type="submit"
                                       value="Зарегистрироваться">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>


