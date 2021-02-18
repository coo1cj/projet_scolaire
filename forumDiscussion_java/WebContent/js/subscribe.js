$(document).ready(function () {
    $(".sub, .unsub").on("click", function () {
        var clicked = $(this);
        var id = clicked.attr("id").substring(2);
        var url = appContext.concat("/auth/");
        url = url.concat(clicked.attr("class") === "sub" ? "subscribe?id=" : "unsubscribe?id=");
        url = url.concat(id);
        $.get(url, function (data, stat) {
            clicked.toggleClass("sub unsub");
            clicked.html((clicked.attr("class") === "sub") ? "Subscribe" : "X");
        });
    });
});
