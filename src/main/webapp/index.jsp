<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>UTask - Ваш помощник в задачах!</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.style.css">

    <style>
        body {
            text-align: center;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }

        #main-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 60vh;
            text-align: center;
            width: 60%;
        }

        #logo {
            font-size: 3em;
            color: black; /* Черный цвет для UTask */
            margin-bottom: 20px;
        }

        #slogan {
            font-size: 1.5em;
            margin: 10px 0;
        }

        #description {
            font-size: 1em;
            margin-bottom: 40px;
        }

        .action-button {
            font-size: 1.2em;
            padding: 10px 20px;
            text-decoration: none;
            color: white;
            background-color: #a2a2a2;
            border: none;
            border-radius: 5px;
            margin: 10px;
        }
    </style>
</head>
<body>

<div id="main-content">
    <div id="logo">
        UTask
    </div>
    <div id="slogan">Ваш помощник в задачах!</div>
    <div id="description">Создавайте задачи, добавляйте категории и описания к задачам</div>

    <div id="buttons">
        <a class="action-button" href="/register">Регистрация</a>
        <a class="action-button" href="/signIn">Войти</a>
    </div>
</div>

</body>
</html>









