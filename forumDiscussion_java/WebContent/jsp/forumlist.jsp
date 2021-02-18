<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Forum</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forumlist.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <c:set var="context" value="${pageContext.request.contextPath}"/>

    <script>const appContext = "${context}";</script>
    <script src="${context}/js/subscribe.js"></script>

</head>
<body>

<fieldset class="surrond">

    <legend class="titre">Liste des forums</legend>

    <table class="forumtable">
        <c:forEach items="${requestScope.forums}" var="forum">
            <tr>
                <td>${forum.id}</td>
                <td title="${forum.description}"><a href="${context}/auth/forum?id=${forum.id}">${forum.title}</a></td>
                <c:set var="subscribed" scope="request" value="${requestScope.subscriptions.contains(forum)}"/>
                <td id="id${forum.id}" class="${(subscribed)?"unsub":"sub"}"><a>${(subscribed)?"X":"Subscribe"}</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${sessionScope.user.admin()}">
            <tr>
                <td>+</td>
                <td colspan="2"><a href="${context}/admin/addforum">Ajouter un forum</a></td>
            </tr>
        </c:if>
    </table>

</fieldset>

<div class="home">
    <a href="${pageContext.request.contextPath}/auth/index">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>

</body>
</html>
