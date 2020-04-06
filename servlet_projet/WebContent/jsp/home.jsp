<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26/03/2020
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>

<body>
<fieldset>
    <legend>Bienvenue ${sessionScope.user.getfname()}</legend>

    <form id="quit" action="${pageContext.request.contextPath}/Disconnect">
        <input id="disconnect" class="button" type="submit" value="Déconnexion" form="quit" formmethod="post">
    </form>
    <form id="list" action="${pageContext.request.contextPath}/List">
        <input id="admin" class="button" type="submit" value="Utilisateurs" form="list"
               formmethod="post" ${(sessionScope.user.admin)?"":"hidden"}>
    </form>

</fieldset>

<div class="cookie" ${(sessionScope.user.admin)?"":"hidden"}>
    <p>${(empty sessionScope.last)?"Jamais":sessionScope.last}</p>
</div>

</body>
</html>
