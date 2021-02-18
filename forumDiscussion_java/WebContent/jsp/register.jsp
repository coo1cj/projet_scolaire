<%--suppress ELValidationInJSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/connexion.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<div class="error">
    <p ${(param.containsKey("failed")) ? "":"hidden"}>Erreur lors de l'inscription</p>
</div>

<fieldset>
    <legend>Inscrivez vous</legend>


    <form id="register" action="${pageContext.request.contextPath}/register">

        <div class="textin">
            <input type="email" class="react" pattern=".+@.+\..+" placeholder="Email" name="login" required/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="text" class="react" placeholder="Prénom" name="firstname" required/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="text" class="react" placeholder="Nom" name="lastname" required/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="password" class="react" placeholder="Password" name="password" required/>
            <span class="underline"></span>
        </div>

        <div>
            <div class="genre">
                <label>Homme</label>
                <input type="radio" id="male" name="gender" value="Male" checked/>
            </div>

            <div class="genre">
                <label>Femme</label>
                <input type="radio" id="female" name="gender" value="Female"/>
            </div>
        </div>


        <input class="sub" type="submit" value="Valider" formmethod="post">

    </form>

</fieldset>

<div class="home">
    <a href="${pageContext.request.contextPath}/auth/index">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>


</body>
</html>
