<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Attente de validation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/connexion.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<div class="error">
    <p>Compte en attente de validation</p>
</div>

<div class="home">
    <form class="leftform" id="quit" action="${pageContext.request.contextPath}/disconnect">
        <input id="disconnect" class="button" type="submit" value="Déconnexion" form="quit" formmethod="get">
    </form>
</div>

</body>
</html>
