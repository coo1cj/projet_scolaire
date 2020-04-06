<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addUser.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <title>Ajouter un utilisateur</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/Manager" method="post">
    <fieldset>
        <legend> Création d'un nouvel utilisateur</legend>

        <div class="textin">
            <input type="text" class="react" placeholder="Prénom" id="firstname" name="firstname"/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="text" class="react" placeholder="Nom" id="lastname" name="lastname"/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="email" class="react" pattern=".+@.+\..+" placeholder="Email@provider.xyz" id="email"
                   name="email" required/>
            <span class="underline"></span>
        </div>

        <div class="textin">
            <input type="password" class="react" placeholder="Mot de passe" id="password" name="password" required/>
            <span class="underline"></span>
        </div>

        <div>
            <div class="genre">
                <label>Homme</label>
                <input type="radio" id="male" name="gender" value="Homme" checked/>
            </div>

            <div class="genre">
                <label>Femme</label>
                <input type="radio" id="female" name="gender" value="Femme"/>
            </div>

            <div class="genre">
                <label>Admin</label>
                <input type="checkbox" id="admin" name="admin"/>
            </div>
        </div>

        <br><br>

        <div>
            <input type="submit" value="Valider" formmethod="post">
        </div>

    </fieldset>


</form>

<div class="home">
    <a href="${pageContext.request.contextPath}/Connexion">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>

</body>
</html>