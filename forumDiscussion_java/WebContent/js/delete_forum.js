$(document).ready(function () {
    let fdel_running = false;
    let fdel = $(".fdelete");
    let admin = $(".admin");
    admin.on("click", function() {
        if(fdel_running === false) {
            fdel_running = true;
            fdel.animate({opacity:-1}, 1000, function() {
                fdel.html("! Confirmer !");
                fdel.animate({opacity:1}, 500, function() {
                    fdel.prop("id", "confirmed");
                    let fdUrl = appContext+"/admin/delete?id="+$(".titre").attr("id");
                    fdel.prop("href", fdUrl);
                    fdel_running = false;
                });
            })
        }
    });

    admin.on("click", "#confirmed", function() {

    })
})