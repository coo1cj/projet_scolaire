$(document).ready(function () {
    let permission = $(".perm");
    let running_perms = false;
    permission.on("click", "i", function (e) {
        let current = $(e.target);
        let level = current.parent().children();
        if (isSelected(current)) {
            return;
        }
        if (running_perms === true) {
            return;
        }
        running_perms = true;
        removeColors(level);

        const pUrl = appContext.concat("/admin/userlist");
        const user = current.parent().parent().prop("id");
        const privs = current.index();
        $.ajax({
            type: "POST",
            url: pUrl,
            dataType: "text",
            data: {
                "user": user,
                "level": privs,
            },
            success: function (data, status, jqXHR) {
                running_perms = false;
                $(level[privs]).addClass(assocClass(privs));
            }
        })
    });
})

function removeColors(elem) {
    elem.removeClass("pgreen pyellow pred");
}

function isSelected(elem) {
    return elem.hasClass("pred") || elem.hasClass("pyellow") || elem.hasClass("pgreen");
}

function getDelete() {
    return "<td class='unsub'><a href='" + appContext + "/admin/deleteuser?user='>Supprimer</a></td>";
}

function assocClass(index) {
    switch (index) {
        case 0:
            return "pred";
        case 1:
            return "pyellow";
        case 2:
            return "pgreen";
        default:
            return "";
    }
}