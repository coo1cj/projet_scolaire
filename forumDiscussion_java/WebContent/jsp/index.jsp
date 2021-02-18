<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Home</title>
    <meta charset="UTF-8">
    <c:set value="${pageContext.request.contextPath}" var="context" scope="request"/>
    <link rel="stylesheet" type="text/css" href="${context}/css/index.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>const appContext = "${context}";</script>
    <script src="${context}/js/unsubscribe_index.js"></script>
</head>

<body>
<fieldset>
    <legend>Bienvenue ${sessionScope.user.firstname}</legend>

    <section class="flex">

        <div>
            <form class="leftform" id="quit" action="${context}/auth/disconnect">
                <input id="disconnect" class="button" type="submit" value="Déconnexion" form="quit" formmethod="get">
            </form>

            <form class="leftform" id="flink" action="${context}/auth/forum">
                <input id="forums" class="button" type="submit" value="Forums" form="flink" formmethod="get">
            </form>

            <form class="leftform" id="list" action="${context}/admin/userlist">
                <input id="admin" class="button" type="submit" value="Utilisateurs" form="list"
                       formmethod="get" ${(sessionScope.user.admin())?"":"hidden"}>
            </form>
        </div>

        <div class="vl"></div>

        <table>
            <tr>
                <th colspan="2">Vos abonnements</th>
            </tr>
                <c:forEach items="${requestScope.subs}" var="cforum">
                <tr>
                    <td><a href="${context}/auth/forum?id=${cforum.id}">${cforum.title}</a></td>
                    <td class="unsub" id="id${cforum.id}"><a>X</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>

</fieldset>


</body>
</html>

