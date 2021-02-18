$(document).ready(function () {
    $(".unsub").on("click", function () {
        var clicked = $(this);
        var id = clicked.attr("id").substring(2);
        var url = appContext+"/auth/unsubscribe?id=";
        url = url.concat(id);
        $.get(url, function () {
            clicked.parent().remove();
        });
    });
});
