<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ajouter un forum</title>
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" type="text/css" href="${context}/css/addforum.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<fieldset class="surrond">

    <legend class="titre">Ajouter un forum</legend>
    <form id="add" action="${context}/admin/addforum" method="post">
        <input type="text" class="react" placeholder="Nom du forum" name="forum">
        <textarea class="react" maxlength="1000" placeholder="Description" name="description"></textarea>
        <input class="validate" type="submit" value="Valider">
    </form>

</fieldset>

<div class="home">
    <a href="${pageContext.request.contextPath}/auth/index">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>

</body>
</html>
