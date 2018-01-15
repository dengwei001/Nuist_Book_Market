$(function () {
    $('#mainPage').css("width",$(window).width());
    $('#mainPage').css("height",$(window).height()-46);

    $.ajax({
        type:'get',
        url:'/book_market/getUserName',
        success:function (data) {
            $('#username').text(data);
        }
    });

})


