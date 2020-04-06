<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/recap.css">
    <title>Ajouter un utilisateur</title>
</head>
<body>

<fieldset>
    <legend> Utilisateur déjà existant</legend>

    <div id="leftcmp">
        <form action="${pageContext.request.contextPath}/Manager" method="post">

            <div class="textin">
                <input type="text" class="react" value=${ufname} id="firstname" name="firstname"/>
                <span class="underline"></span>
            </div>

            <div class="textin">
                <input type="text" class="react" value=${ulname} id="lastname" name="lastname"/>
                <span class="underline"></span>
            </div>

            <div class="textin">
                <input type="email" class="react" pattern=".+@.+\..+" value=${uemail} id="email" name="email" required
                       readonly/>
                <span class="underline"></span>
            </div>

            <div class="textin">
                <input type="password" class="react" value="${upass}" id="password" name="password" required/>
                <span class="underline"></span>
            </div>

            <div class="genre">
                <label>Homme</label>
                <input type="radio" id="male" name="gender" value="Homme" ${ismale}/>
            </div>

            <div class="genre">
                <label>Femme</label>
                <input type="radio" id="female" name="gender" value="Femme" ${isfemale}/>
            </div>

            <input type="hidden" name="admin" value="true" ${uadmin}>
            <input type="hidden" name="enforce" value="true">
            <input type="submit" value="Valider" formmethod="post">
        </form>

    </div>


    <div id="rightcmp">
        <p>${fname}</p>
        <p>${lname}</p>
        <p>${email}</p>
        <p>${gender}</p>

        <form action="${pageContext.request.contextPath}/index.html">
            <input id="cancel" type="submit" value="Annuler">
        </form>

    </div>

</fieldset>


</body>
</html>
