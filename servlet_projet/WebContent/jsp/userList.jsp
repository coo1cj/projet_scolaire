<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 26/03/2020
  Time: 19:42
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Liste des utilisateurs</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userList.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<fieldset>
    <legend>Liste des utilisateurs</legend>
    <table>
        <tr>
            <th>Email</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Genre</th>
            <th>Privilèges</th>
        </tr>
        <c:forEach items="${sessionScope.userList}" var="cuser">
            <tr>
                <td>${cuser.value.getemail()}</td>
                <td>${cuser.value.getfname()}</td>
                <td>${cuser.value.getlname()}</td>
                <td>${cuser.value.getgender()}</td>
                <td>${(cuser.value.admin)?"Admin":"User"}</td>
            </tr>
        </c:forEach>
    </table>
</fieldset>

<div class="home">
    <a href="${pageContext.request.contextPath}/Connexion">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>


</body>
</html>
