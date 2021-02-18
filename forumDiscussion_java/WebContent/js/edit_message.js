$(document).ready(function () {
    let edit_running = false;
    message = $(".message");
    message.on("click", function(){
        if(edit_running === true) {
            return;
        }
        let msg = $(this);
        let edit = $(".edit");
        if (msg.find(".edit").length === 0) {
            edit_running = true;
            let button = getEdit();
            let delbutton = getDelete(getId(msg));
            clearEdit(edit);
            $(".delete").remove();
            msg.append(button);
            msg.append(delbutton);
            msg.animate({height: "+=54px"}, 600, function () {
                button.fadeIn(300);
                delbutton.fadeIn(300);
                edit_running = false;
            });
        }
    })

    message.on("click", ".edit", function() {
        let edit = $(".edit");
        let area = edit.prev();
        area.prop("readonly", false);
        area.css("cursor", "text");
        edit.prop("id", "vedit");
        edit.html("Valider");
        area.focus();
        area.selectionStart = area.selectionEnd = area.val();
    })

    message.on("click", "#vedit", function() {
        let edit = $(".edit");
        let area = edit.prev();
        let id = getId(edit.parent());
        let fid = $(".titre").attr("id");
        let eUrl = appContext.concat("/auth/edit");

        $.ajax({
            type: "POST",
            url: eUrl,
            data: {
                "id": id,
                "content": area.val(),
                "forum": fid
            },
            success: function(msg) {
                location.reload();
            },
            failure: function(msg) {
                area.val(area.html());
            }
        })
    })

    message.on("click", ".delete", function() {
        let dUrl = appContext.concat("/auth/delete");
        let id = getId($(".edit").parent());
        let fid = $(".titre").attr("id");
        $.ajax({
            type: "POST",
            url: dUrl,
            data: {
                "id": id,
                "forum": fid
            },
            complete: function(msg) {
                location.reload();
            },
        })
    })
});

function getId(message) {
    return message.attr("id").substring(3);
}

function getEdit() {
    let but = "<a onclick='editMsg()' class='edit'".concat(">Modifier</a>");
    return $(but).hide();
}

function getDelete(id) {
    const url = appContext.concat("/auth/delete?id=").concat(id);
    let del = "<a class='delete'>Supprimer</a>";
    return $(del).hide();
}

function clearEdit(edit) {
    edit.parent().height("-=54px");
    edit.prev().prop("readonly", true);
    edit.prev().css("cursor", "pointer");
    edit.remove();
}