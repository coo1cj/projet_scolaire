<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/forum.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <c:set var="context" value="${pageContext.request.contextPath}"/>
    <title>${requestScope.forum.title}</title>

    <script>const appContext = "${context}";</script>
    <script src="${context}/js/edit_message.js"></script>
    <script src="${context}/js/delete_forum.js"></script>

</head>
<body>
<fieldset class="surrond">

    <div class="description">
    <p><c:out value="${requestScope.forum.description}" /></p>
    </div>

    <legend id="${requestScope.forum.id}" class="titre">
        <c:out value="${requestScope.forum.title}"/>
    </legend>

    <form method="post" action="${context}/auth/sendmessage" id="postmsg"></form>
    <input type="hidden" name="forum" value="${requestScope.forum.id}" form="postmsg">

    <table>
        <tr>
            <th>Auteur</th>
            <th>Message</th>
        </tr>

        <c:forEach items="${requestScope.messages}" var="cmessage">
            <c:set var="self" value="${(cmessage.user == sessionScope.user.login)}"/>
            <tbody class="${(self)?"tupleself":"tuple"}">
            <tr>
                <td class="user">
                    <c:if test="${cmessage.user == null}">
                        Compte supprimé
                    </c:if>
                    <c:if test="${cmessage.user != null}">
                        <c:out value="${cmessage.user}"/>
                    </c:if>

                </td>
                <td id="msg${cmessage.id}" class="${(self)?"message":"messagein"}" rowspan="2"><textarea readonly><c:out value="${cmessage.content}"/></textarea></td>
            </tr>
            <tr>
                <td class="time"><c:out value="${cmessage.time}"/></td>
            </tr>
            </tbody>
        </c:forEach>

        <tbody class="tuple">

        <tr>
            <td>Nouveau message</td>
            <td rowspan="2" class="messagein"><textarea name="content" form="postmsg"></textarea></td>
        </tr>
        <tr>
            <td><input type="submit" form="postmsg"></td>
        </tr>
        </tbody>
    </table>

</fieldset>

<c:if test="${sessionScope.user.admin()}">
    <div class="admin">
        <a class="fdelete">Supprimer le forum</a>
    </div>
</c:if>


<div class="home">
    <a href="${pageContext.request.contextPath}/auth/index">
        <i class="material-icons" style="font-size: 48px; color:white">home</i>
    </a>
</div>


</body>
</html>
