<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/connexion.css">
</head>
<body>

<fieldset>
    <legend>Connectez vous</legend>


    <form id="connect" action="${pageContext.request.contextPath}/connexion"></form>
    <form id="register" action="${pageContext.request.contextPath}/register"></form>

    <div class="textin">
        <input type="email" class="react" pattern=".+@.+\..+" form="connect" placeholder="Login" name="login"
               required/>
        <span class="underline"></span>
    </div>

    <div class="textin">
        <input type="password" class="react" minlength="6" form="connect" placeholder="Password" name="password" required/>
        <span class="underline"></span>
    </div>

    <input class="sub" type="submit" value="Connexion" form="connect" formmethod="post">
    <input class="sub" type="submit" value="Inscription" form="register" formmethod="get">


</fieldset>

<div>
    <div class="error">
        <p ${(empty requestScope.failure)?"hidden":""}>Combinaison login/password inconnue</p>
    </div>
    <div class="success">
        <p ${(param.containsKey("registered"))?"":"hidden"}>Compte enregistré</p>
    </div>
</div>


</body>
</html>
