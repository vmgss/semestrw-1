<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">

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

        #login-form {
            width: 60%;
            max-width: 400px;
        }

        .form-title {
            font-size: 2em;
            color: black;
            margin-bottom: 20px;
        }

        .form-label {
            font-size: 1.2em;
        }

        .form-control {
            font-size: 1em;
            margin-bottom: 10px;
        }

        .btn-submit {
            font-size: 1.2em;
            padding: 10px 20px;
            color: white;
            background-color: #343a40;
            border: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div id="login-form" class="vh-100">
    <div class="container">
        <div class="card bg-white p-5">
            <form class="mb-3 mt-md-4" method="post" action="/signIn">
                <h2 class="form-title fw-bold mb-2 text-uppercase">UTask</h2>
                <div class="form-group">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <input class="btn btn-outline-dark btn-submit" type="submit" value="Sign In">
            </form>
        </div>
    </div>
</div>

</body>
</html>





