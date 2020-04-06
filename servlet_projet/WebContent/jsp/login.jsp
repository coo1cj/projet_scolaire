<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>

<fieldset>
    <legend>Connectez vous</legend>


    <form id="connect" action="${pageContext.request.contextPath}/Connexion"></form>
    <form id="register" action="${pageContext.request.contextPath}/Manager"></form>

    <div class="textin">
        <input type="email" class="react" pattern=".+@.+\..+" form="connect" placeholder="Email" name="email"
               required/>
        <span class="underline"></span>
    </div>

    <div class="textin">
        <input type="password" class="react" form="connect" placeholder="Password" name="password" required/>
        <span class="underline"></span>
    </div>

    <input class="sub" type="submit" value="Connexion" form="connect" formmethod="post">
    <input class="sub" type="submit" value="Inscription" form="register" formmethod="get">


</fieldset>

<div>
    <div class="error">
        <p ${(empty requestScope.bademail)?"hidden":""}>Utilisateur inconnu</p>
        <p ${(empty requestScope.badpass)?"hidden":""}>Mot de passe erroné</p>
    </div>
</div>


</body>
</html>
