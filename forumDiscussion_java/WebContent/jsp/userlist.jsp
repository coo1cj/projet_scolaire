<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des utilisateurs</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forumlist.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>const appContext = "${pageContext.request.contextPath}"</script>
    <script src="${pageContext.request.contextPath}/js/userlist.js"></script>
</head>
<body>

<fieldset class="surrond">

    <legend class="titre">Utilisateurs</legend>

    <table class="forumtable">

        <tr>
            <th>Email</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Genre</th>
            <th>Permissions</th>
        </tr>

        <c:forEach items="${requestScope.userlist}" var="cuser">
            <tr class="tuple" id="${cuser.login}">
                <td><c:out value="${cuser.login}"/></td>
                <td><c:out value="${cuser.firstname}"/></td>
                <td><c:out value="${cuser.lastname}"/></td>
                <td><c:out value="${cuser.gender}"/></td>
                <td class="perm">
                    <c:choose>
                        <c:when test="${cuser.admin()}">
                            <i class="material-icons">block</i>
                            <i class="material-icons">remove_circle_outline</i>
                            <i class="material-icons pgreen">security</i>
                        </c:when>
                        <c:when test="${cuser.user()}">
                            <i class="material-icons">block</i>
                            <i class="material-icons pyellow">remove_circle_outline</i>
                            <i class="material-icons">security</i>
                        </c:when>
                        <c:otherwise>
                            <i class="material-icons pred">block</i>
                            <i class="material-icons">remove_circle_outline</i>
                            <i class="material-icons">security</i>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>

    </table>

    <div class="deletediv">
        <form method="post" action="${pageContext.request.contextPath}/admin/deleteuser">
        <select class="deletelist" name="user">
            <c:forEach items="${requestScope.userlist}" var="cuser">
                <option value="<c:out value="${cuser.login}"/>">${cuser.login}</option value="<c:out
                        value="${cuser.login}"/>">
            </c:forEach>
        </select>
        <input type="submit" class="delbutton" value="Supprimer">
        </form>
    </div>

</fieldset>

<div class="home">
    <a href="${pageContext.request.contextPath}/auth/index">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>

</body>
</html>
